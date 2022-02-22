package sfr.dao;
import sfr.model.PlanType;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Luis D
 */
public class PlanTypeDAO extends GenericDAO {

    private static PlanTypeDAO uniqueInstance; //Singleton Pattern Object

    /**
     * @return the Singleton Pattern Object of DataDAO class.
     */
    public static PlanTypeDAO getInstance() {
        if (uniqueInstance == null) 
            uniqueInstance = new PlanTypeDAO();
        
        return uniqueInstance;
    }

    /**
     * This method adds a Data object into the DB record, using HQL.
     *
     * @param data is the Data Object to be added.
     */
    public void add(PlanType data) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(data);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        } finally {
            closeEntityManager();
        }
    }

    /**
     * This method updates a Data object into the DB record, using HQL.
     *
     * @param data is the Data Object to be updated.
     */
    public void update(PlanType data) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(data);
            em.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        } finally {
            closeEntityManager();
        }
    }

    /**
     * This method deletes a Data object into the DB record, using HQL.
     *
     * @param data is the Data Object to be deleted.
     */
    public void delete(PlanType data) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.remove(em.merge(data));
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        } finally {
            closeEntityManager();
        }
    }

    /**
     * @return a sorted list of Data objects depending on the next parameters:
     * @throws java.lang.Exception
     */
    public List<PlanType> listAll() throws Exception {
        try {
            em = getEntityManager();
            Query query = em.createQuery("SELECT p from sfr.model.PlanType p");
            return (List<PlanType>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }

    /**
     * @return a casted HashMap of Data objects from an HQL query sorted in
     * descending order by entryDate.
     * @throws java.lang.Exception
     */
/*
    public HashMap<String, List<PlanType>> listAllPlanTypeHM() throws Exception {
        HashMap<String, List<PlanType>> plans = new HashMap<>();
        List<PlanType> dataList = this.listAll();
        List<PlanType> parents = new ArrayList<PlanType>();
        //Parents
        dataList.forEach(d -> {
            if (d.getParent() == 0) {
                parents.add(d);
            }
        });
        
        return plans;
    }
*/
}
