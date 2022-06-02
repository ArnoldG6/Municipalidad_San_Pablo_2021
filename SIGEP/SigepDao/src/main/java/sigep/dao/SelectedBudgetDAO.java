package sigep.dao;
import common.dao.generic.GenericDAO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import sigep.model.BudgetModificationRequestId;
import sigep.model.SelectedBudget;

/**
 *
 * @author Diego Quiros
 */
public class SelectedBudgetDAO extends GenericDAO {
    
    private static SelectedBudgetDAO uniqueInstance;

    public static SelectedBudgetDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SelectedBudgetDAO();
        }
        return uniqueInstance;
    }

    public void insert(SelectedBudget request) {
        try {
            EntityManager manager = emf.createEntityManager();

            manager.getTransaction().begin();
            manager.persist(request);
            manager.getTransaction().commit();

            manager.close();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        }
    }
    
    public SelectedBudget searchById(SelectedBudget sb) {
        EntityManager manager = emf.createEntityManager();
        SelectedBudget sbSearched = (SelectedBudget) manager.find(SelectedBudget.class, sb.getId());
        manager.close();
        return sbSearched;
    }
    
    public SelectedBudget getLastData(){
        String cmd = "SELECT b FROM SelectedBudget b";

        EntityManager manager = emf.createEntityManager();

        Query query = manager.createQuery(cmd);
        List<SelectedBudget> sbs = query.getResultList();
        
        manager.close();
        SelectedBudget sbDB =  sbs.get(sbs.size()-1);;
        return sbDB;
    }
    
    public List<SelectedBudget> listAll() {
        String cmd = "SELECT b FROM SelectedBudget b";

        EntityManager manager = emf.createEntityManager();

        Query query = manager.createQuery(cmd);
        List<SelectedBudget> selecteds = query.getResultList();
        
        manager.close();

        return selecteds;
    }
    
    public List<SelectedBudget> listAllByRequest(BudgetModificationRequestId id) {
        String cmd = "SELECT b FROM SelectedBudget b WHERE b.budgetModificationRequest.id = :cid";
        
        EntityManager manager = emf.createEntityManager();

        Query query = manager.createQuery(cmd);
        query.setParameter("cid", id);
        List<SelectedBudget> sbs = query.getResultList();
        manager.close();

        return sbs;
    }
}
