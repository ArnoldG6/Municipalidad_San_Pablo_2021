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
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
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

    public void add(User user, String password) {
        try {
            StoredProcedureQuery proc = getEntityManager().createStoredProcedureQuery("insertUser");
            proc.registerStoredProcedureParameter("P_IN_PK_USER", Integer.class, ParameterMode.IN);
            proc.registerStoredProcedureParameter("P_IN_FK_OFFICIAL", Integer.class, ParameterMode.IN);
            proc.registerStoredProcedureParameter("P_IN_FK_EMAIL", String.class, ParameterMode.IN);
            proc.registerStoredProcedureParameter("P_IN_PASSWORD", String.class, ParameterMode.IN);
            proc.setParameter("P_IN_PK_USER", user.getIdUser());
            proc.setParameter("P_IN_FK_OFFICIAL", user.getOfficial().getIdOfficial());
            proc.setParameter("P_IN_FK_EMAIL", user.getEmail());
            proc.setParameter("P_IN_PASSWORD", password);
            proc.execute();
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

    public void handlePasswordReset(User user, Integer code) throws MessagingException, Exception {
        try {
            StoredProcedureQuery proc = getEntityManager().createStoredProcedureQuery("insertResetCode");
            proc.registerStoredProcedureParameter("P_IN_FK_USER", String.class, ParameterMode.IN);
            proc.registerStoredProcedureParameter("P_IN_RESET_CODE", String.class, ParameterMode.IN);
            proc.setParameter("P_IN_FK_USER", user.getIdUser().toString());
            proc.setParameter("P_IN_RESET_CODE", code.toString());
            proc.execute();
            EmailFactory.getInstance().sendResetPassword(user, code.toString());
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        } finally {
            closeEntityManager();
        }
    }
    
    public String getPasswordCode(User user) throws MessagingException, Exception {
        try {
            StoredProcedureQuery proc = getEntityManager().createStoredProcedureQuery("checkPwdResetCodeValidity");
            proc.registerStoredProcedureParameter("P_IN_FK_USER", String.class, ParameterMode.IN);
            proc.setParameter("P_IN_FK_USER", user.getIdUser().toString());
            proc.execute();
            return (String) proc.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        } finally {
            closeEntityManager();
        }

    }

       public void changePassword(User user,String pwdSha256Hash) throws MessagingException, Exception {
        try {
            StoredProcedureQuery proc = getEntityManager().createStoredProcedureQuery("setUserPassword");
            proc.registerStoredProcedureParameter("P_IN_FK_USER", String.class, ParameterMode.IN);
            proc.registerStoredProcedureParameter("P_IN_PWD_HASH", String.class, ParameterMode.IN);
            proc.setParameter("P_IN_FK_USER", user.getIdUser().toString());
            proc.setParameter("P_IN_PWD_HASH", pwdSha256Hash);
            proc.execute();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        } finally {
            closeEntityManager();
        }

    }
}
