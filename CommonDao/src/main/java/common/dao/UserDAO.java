package common.dao;
import common.dao.generic.GenericDAO;
import common.model.User;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;

public class UserDAO extends GenericDAO {
    private static UserDAO uniqueInstance;
    public static UserDAO getInstance(){
        if (uniqueInstance == null) uniqueInstance = new UserDAO();
        return uniqueInstance;
    }
    public User emailValidation(String emailPrt, String passwordPrt) {
        String cmd = "CALL authenticateViaEmail(:emailPrt, :passwordPrt)";

        em = getEntityManager();
        Session session = em.unwrap(Session.class);
        
        Query query = session.createSQLQuery(cmd).addEntity(User.class);
        query.setParameter("emailPrt", emailPrt);
        query.setParameter("passwordPrt", passwordPrt);
        
        User u = (User) query.getSingleResult();
        
        closeEntityManager();
        return u;
    }
        public User idValidation(Integer username, String passwordPrt) {
        String cmd = "CALL authenticateViaUsername(:userPrt, :passwordPrt)";
        em = getEntityManager();
        Session session = em.unwrap(Session.class);
        Query query = session.createSQLQuery(cmd).addEntity(User.class);
        query.setParameter("userPrt", username);
        query.setParameter("passwordPrt", passwordPrt);
        User u = (User) query.getSingleResult();
        closeEntityManager();
        return u;
    }
        public List<User> listAll() {
        try{
            System.out.println(1);
            String cmd = "SELECT u.idUser, u.official,u.email, u.password from User u";
            em = getEntityManager();
            Query query = em.createQuery(cmd);
            List<User> l = (List<User>) query.getResultList();
            return l;
        }catch(Exception e){
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }
    public HashMap<Integer, User> listAllHM(){ //list all users in HashMap type
        HashMap<Integer,User> users = new HashMap<>();
        List<User> usersList = this.listAll();
        for (final User u : usersList) users.put(u.getIdUser(), u);
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
        } finally {
            closeEntityManager();
        }
    }
   
    public User searchById(User user) {
        em = getEntityManager();
        return (User) em.find(User.class, user.getIdUser());
    }
    public User searchByEmail(User user) {
        em = getEntityManager();
        return (User) em.find(User.class, user.getEmail());
    }
}
