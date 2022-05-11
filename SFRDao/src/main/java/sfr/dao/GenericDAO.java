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
        public void handlePasswordReset(User user, String transactionName,Boolean success) throws  Exception {
        try {
            if (user == null ||transactionName == null|| success == null )
                throw new NullPointerException();
            StoredProcedureQuery proc = getEntityManager().createStoredProcedureQuery("insertResetCode");
            proc.registerStoredProcedureParameter("P_IN_FK_USER", String.class, ParameterMode.IN);
            proc.registerStoredProcedureParameter("P_IN_RESET_CODE", String.class, ParameterMode.IN);
            proc.registerStoredProcedureParameter("P_IN_FK_USER", String.class, ParameterMode.IN);
            proc.registerStoredProcedureParameter("P_IN_RESET_CODE", String.class, ParameterMode.IN);
            proc.setParameter("P_IN_FK_USER", user.getIdUser().toString());
            proc.setParameter("P_IN_RESET_CODE", code.toString());
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