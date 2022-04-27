/**
 * @author ArnoldG6
 */
package common.dao;

import common.dao.generic.GenericDAO;
import common.model.User;
import jakarta.mail.MessagingException;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import javax.persistence.Query;
import org.hibernate.Session;

public class UserDAO extends GenericDAO {

    private static UserDAO uniqueInstance;

    public static UserDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new UserDAO();
        }
        return uniqueInstance;
    }

    public User userAuth(String pEmail, String pPwd) {
        try {
            em = getEntityManager();
            Session session = em.unwrap(Session.class);
            Query query = session.createSQLQuery("CALL authUser(:pEmail, :pPwd)").addEntity(User.class);
            query.setParameter("pEmail", pEmail);
            query.setParameter("pPwd", pPwd);
            User u = (User) query.getSingleResult();
            closeEntityManager();
            return u;
        } catch (javax.persistence.NoResultException ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
            return null; //User not found
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        } finally {
            closeEntityManager();
        }
    }

    public List<User> listAll() {
        try {
            em = getEntityManager();
            Query query = em.createQuery("SELECT u from User u");
            return (List<User>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        } finally {
            closeEntityManager();
        }
    }

    public HashMap<Integer, User> listAllHM() { //list all users in HashMap type
        HashMap<Integer, User> users = new HashMap<>();
        List<User> usersList = this.listAll();
        for (final User u : usersList) {
            users.put(u.getIdUser(), u);
        }
        return users;
    }

    public void add(User user) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }

    public void update(User user) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }

    public void delete(User user) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.remove(em.merge(user));
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }

    public User searchById(int id) {
        em = getEntityManager();
        return (User) em.find(User.class, id);
    }

    public User searchByEmail(String email) throws NoSuchElementException {
        return this.listAll().stream().filter(u -> u.getEmail().equals(email)).findFirst().get();
    }

    public void handlePasswordReset(User user, Integer code) throws MessagingException {
        em = getEntityManager();
        Session session = em.unwrap(Session.class);
        Query query = session.createSQLQuery("CALL insertResetCode(:userID, :code)");
        query.setParameter("userID", user.getIdUser());
        query.setParameter("code", code);
        
        query.getSingleResult();
        closeEntityManager();
        
        EmailFactory.getInstance().sendResetPassword(user, code.toString());
        
    }

}
