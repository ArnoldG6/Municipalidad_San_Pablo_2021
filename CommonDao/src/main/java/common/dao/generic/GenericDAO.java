package common.dao.generic;

import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

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

}