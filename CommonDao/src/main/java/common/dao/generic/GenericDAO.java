package common.dao.generic;

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