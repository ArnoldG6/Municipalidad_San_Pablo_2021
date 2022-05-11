/**
 * @author ArnoldG6
 * GenericDAO class is the lowest level class responsible of establishing connection with Hibernate framework
 * in order to cast Java objects from HQL queries.
 */
package sfr.dao;
import common.model.User;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
public class GenericDAO {
    @PersistenceContext
    protected static EntityManager em;
    private static EntityManagerFactory emf;
    private static final String PU = "SFRPU";
    public GenericDAO() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(PU);
            java.util.logging.Logger.getLogger("org.hibernate").setLevel((Level.OFF));
            
        }
    }
    /**
    getEntityManager() method 
    * @return a EntityManager object in order to send HQL queries.
    */
    protected EntityManager getEntityManager() {
        if (em == null) 
            em = emf.createEntityManager();
        return em;
    }
    /**
    closeEntityManager() method is in charge of closing connection with Hibernate framework.
     */
    protected void closeEntityManager() {
        if (em != null) {
            em.close();
            em = null;
        }
    }
    public void recordTransaction(User user, Transaction transaction,Boolean success, String description) throws  Exception {
        try {
            if (user == null ||transaction == null|| success == null || description == null)
                throw new NullPointerException();
            StoredProcedureQuery proc = getEntityManager().createStoredProcedureQuery("insertResetCode");
            proc.registerStoredProcedureParameter("P_USERNAME", Integer.class, ParameterMode.IN);
            proc.registerStoredProcedureParameter("P_EMAIL", String.class, ParameterMode.IN);
            proc.registerStoredProcedureParameter("P_TRANSACTION_NAME", String.class, ParameterMode.IN);
            proc.registerStoredProcedureParameter("P_SUCCESS", Boolean.class, ParameterMode.IN);
            proc.registerStoredProcedureParameter("P_DESCRIPTION", String.class, ParameterMode.IN);
            proc.setParameter("P_USERNAME", user.getIdUser());
            proc.setParameter("P_EMAIL", user.getEmail());
            proc.setParameter("P_TRANSACTION_NAME", transaction.name());
            proc.setParameter("P_SUCCESS", success);
            proc.setParameter("P_DESCRIPTION", description);
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