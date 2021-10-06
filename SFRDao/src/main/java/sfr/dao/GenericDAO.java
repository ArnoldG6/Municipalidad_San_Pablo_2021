package sfr.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GenericDAO {

    protected static EntityManager em;
    private static EntityManagerFactory emf;
    private static final String PU = "SFRPU";

    public GenericDAO() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(PU);
        }
    }

    protected EntityManager getEntityManager() {
        if (em == null) {
            em = emf.createEntityManager();
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