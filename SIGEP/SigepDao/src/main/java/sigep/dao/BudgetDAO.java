package sigep.dao;

import common.dao.generic.GenericDAO;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.hibernate.Session;
import sigep.model.Budget;

public class BudgetDAO extends GenericDAO {
    
    private static BudgetDAO uniqueInstance;
    private static HashMap<String, Budget> items = new HashMap<>();
    
    public static BudgetDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new BudgetDAO();
        }
        return uniqueInstance;
    }
    
    public HashMap<String, Budget> getItems() {
        return items;
    }
    
    public void addBudgetData() {
        List<Budget> budget = getInstance().listAll();
        if (budget.size() > 0) {
            budget.forEach(b -> {
                getInstance().getItems().put(b.getIdItem(), b);
            });
        }
    }
    
    public List<Budget> listAll() {
        String cmd = "SELECT b FROM Budget b ORDER BY b.idItem ASC";
        
        EntityManager manager = emf.createEntityManager();
        
        Query query = manager.createQuery(cmd);
        List<Budget> bl = query.getResultList();
        
        manager.close();
        
        return bl;
    }
    
    public List<Budget> listByNameOrCode(String parameter) {
        String cmd = "CALL searchBudgetByNameOrCode(:parameter)";
        
        EntityManager manager = emf.createEntityManager();
        Session session = manager.unwrap(Session.class);
        
        Query query = session.createSQLQuery(cmd).addEntity(Budget.class);
        query.setParameter("parameter", parameter);
        
        List<Budget> bl = query.getResultList();
        
        manager.close();
        
        return bl;
    }
    
    public void update(Budget budget) {
        try {
            EntityManager manager = emf.createEntityManager();
            
            manager.getTransaction().begin();
            manager.merge(budget);
            manager.getTransaction().commit();
            
            manager.close();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        }
    }
    
    public Budget searchById(Budget budget) {
        EntityManager manager = emf.createEntityManager();
        Budget budgetSearched = (Budget) manager.find(Budget.class, budget.getIdItem());
        manager.close();
        return budgetSearched;
    }
    
    public void updateOrdinaryAmount(String idItem, double newAmount) {
        Budget budget = searchById(new Budget(idItem));
        double diference = newAmount - budget.getOrdinaryAmount();
        budget.setOrdinaryAmount(budget.getOrdinaryAmount() + diference);
        budget.setAvailableAmount(budget.getAvailableAmount() + diference);
        update(budget);
    }
    
    public void addBlockedAmount(String idItem, double blockedAmount) {
        Budget budget = searchById(new Budget(idItem));
        do {
            budget.setAvailableAmount(budget.getAvailableAmount() - blockedAmount);
            budget.setBlockedAmount(budget.getBlockedAmount() + blockedAmount);
            BudgetDAO.getInstance().getItems().get(idItem).setAvailableAmount(budget.getAvailableAmount() - blockedAmount);
            BudgetDAO.getInstance().getItems().get(idItem).setBlockedAmount(budget.getBlockedAmount() + blockedAmount);
            update(budget);
            budget = budget.getFather();
        } while (!Objects.isNull(budget));
    }
    
    public void removeBlockedAmount(String idItem, double blockedAmount) {
        Budget budget = searchById(new Budget(idItem));
        do {
            budget.setAvailableAmount(budget.getAvailableAmount() + blockedAmount);
            budget.setBlockedAmount(budget.getBlockedAmount() - blockedAmount);
            BudgetDAO.getInstance().getItems().get(idItem).setAvailableAmount(budget.getAvailableAmount() - blockedAmount);
            BudgetDAO.getInstance().getItems().get(idItem).setBlockedAmount(budget.getBlockedAmount() + blockedAmount);
            update(budget);
            budget = budget.getFather();
        } while (!Objects.isNull(budget));
    }
    
    public void insert(Budget budget) {
        try {
            EntityManager manager = emf.createEntityManager();
            
            manager.getTransaction().begin();
            manager.persist(budget);
            manager.getTransaction().commit();
            
            manager.close();
            addBudgetAmount(budget);
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        }
    }
    
    public void addBudgetAmount(Budget budget) {
        Budget father = budget.getFather();
        if (father != null) {
            do {
                
                father = BudgetDAO.getInstance().searchById(father);
                father.setOrdinaryAmount(budget.getOrdinaryAmount());
                father.setAvailableAmount(budget.getOrdinaryAmount());
                BudgetDAO.getInstance().update(father);
                
                father = father.getFather();
                
            } while (father != null);
        }
    }
}
