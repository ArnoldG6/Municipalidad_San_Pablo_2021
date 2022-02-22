/**
 * @author ArnoldG6
 * GenericDAO class is the lowest level class responsible of establishing connection with Hibernate framework
 * in order to cast Java objects from HQL queries.
 */
package sfr.dao;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
public class GenericDAO {
    @PersistenceContext
    protected static EntityManager em;
    private static EntityManagerFactory emf;
    private static final String PU = "SFRPU";
    public GenericDAO() {
        if (emf == null) 
            emf = Persistence.createEntityManagerFactory(PU);
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

}