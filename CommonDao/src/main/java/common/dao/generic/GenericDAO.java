package common.dao.generic;

import common.model.User;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;

public class GenericDAO {

    protected static EntityManager em;
    private static EntityManagerFactory emf;
    private static final String PU = "HibernateJpaPU";

    public GenericDAO() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(PU);
            java.util.logging.Logger.getLogger("org.hibernate").setLevel((Level.OFF));
        }
    }

    protected EntityManager getEntityManager() {
        if (em == null) {

            em = emf.createEntityManager();
            em.setFlushMode(FlushModeType.COMMIT);

        }
        return em;
    }

    protected void closeEntityManager() {
        if (em != null) {
            em.close();
            em = null;
        }
    }

    public void recordTransaction(String usernameOrEmail, Transaction transaction, Boolean success, String description) throws Exception {
        try {
            if (usernameOrEmail == null || transaction == null || success == null) {
                throw new NullPointerException();
            }
            StoredProcedureQuery proc = getEntityManager().createStoredProcedureQuery("recordUserTransaction");
            proc.registerStoredProcedureParameter("P_EMAIL_OR_USERNAME", String.class, ParameterMode.IN);
            proc.registerStoredProcedureParameter("P_TRANSACTION_NAME", String.class, ParameterMode.IN);
            proc.registerStoredProcedureParameter("P_SUCCESS", Boolean.class, ParameterMode.IN);
            proc.registerStoredProcedureParameter("P_DESCRIPTION", String.class, ParameterMode.IN);
            proc.setParameter("P_EMAIL_OR_USERNAME", usernameOrEmail);
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
