package sigep.dao;

import common.dao.generic.GenericDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import sigep.model.BudgetLog;

public class BudgetLogDAO extends GenericDAO {

    private static BudgetLogDAO uniqueInstance;

    public static BudgetLogDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new BudgetLogDAO();
        }
        return uniqueInstance;
    }
    
    public List<BudgetLog> listAll() {
        String cmd = "SELECT b FROM BudgetLog b ORDER BY b.id.date";

        EntityManager manager = emf.createEntityManager();

        Query query = manager.createQuery(cmd);
        List<BudgetLog> bl = query.getResultList();

        manager.close();

        return bl;
    }

}
