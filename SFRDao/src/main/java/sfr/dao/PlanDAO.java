package sfr.dao;
import common.dao.generic.GenericDAO;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Query;
import sfr.model.Plan;
/**
 *
 * @author arnold
 */
public class PlanDAO extends GenericDAO{
        private static PlanDAO uniqueInstance;
        public static PlanDAO getInstance(){
            if (uniqueInstance == null) uniqueInstance = new PlanDAO();
            return uniqueInstance;
        }
        public List<Plan> listAll() {
        try{
            System.out.println(1);
            String cmd = "SELECT p.PK_ID, p.Name, p.Description, "
                    + "p.EntryDate, p.Status, p.AuthorName FROM T_PLAN p";
            em = getEntityManager();
            Query query = em.createQuery(cmd);
            List<Plan> l = (List<Plan>) query.getResultList();
            return l;
        }catch(Exception e){
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }
    public HashMap<Integer, Plan> listAllHM(){
        HashMap<Integer,Plan> plans = new HashMap<>();
        List<Plan> plansList = this.listAll();
        for (final Plan p : plansList) plans.put(p.getId(), p);
        return plans;
    }
    public void add(Plan plan) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(plan);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        } finally {
            closeEntityManager();
        }
    }
    public void update(Plan plan) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(plan);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        } finally {
            closeEntityManager();
        }
    }

    public void delete(Plan plan) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.remove(em.merge(plan));
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        } finally {
            closeEntityManager();
        }
    }
   
    public Plan searchById(Plan plan) {
        em = getEntityManager();
        return (Plan) em.find(Plan.class, plan.getId());
    }
}
