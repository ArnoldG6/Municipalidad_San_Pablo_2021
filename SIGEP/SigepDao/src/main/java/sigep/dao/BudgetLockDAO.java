package sigep.dao;

import common.dao.generic.GenericDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sigep.model.Budget;
import sigep.model.BudgetLock;

public class BudgetLockDAO extends GenericDAO {

    private static BudgetLockDAO uniqueInstance;

    public static BudgetLockDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new BudgetLockDAO();
        }
        return uniqueInstance;
    }

    public List<BudgetLock> listAllCurrent() {
        String cmd = "CALL retrieveCurrentLocks()";

        EntityManager manager = emf.createEntityManager();
        Session session = manager.unwrap(Session.class);

        Query query = session.createSQLQuery(cmd).addEntity(BudgetLock.class);
        List<BudgetLock> bl = query.getResultList();

        manager.close();

        return bl;
    }

    public List<BudgetLock> listAllBudget(String idBudget) {
        String cmd = "SELECT l FROM BudgetLock l WHERE l.budgetItem.idItem = :idBudget";

        EntityManager manager = emf.createEntityManager();

        Query query = manager.createQuery(cmd);
        query.setParameter("idBudget", idBudget);
        List<BudgetLock> bl = query.getResultList();

        manager.close();

        return bl;
    }

    public void update(BudgetLock lock) {
        try {
            EntityManager manager = emf.createEntityManager();

            manager.getTransaction().begin();
            manager.merge(lock);
            manager.getTransaction().commit();

            manager.close();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        }
    }

    public BudgetLock searchById(BudgetLock budget) {
        EntityManager manager = emf.createEntityManager();
        BudgetLock budgetLockSearched = (BudgetLock) manager.find(BudgetLock.class, budget.getIdLock());
        manager.close();
        return budgetLockSearched;
    }

    public void addWithId(BudgetLock lock) {
        try {
            EntityManager manager = emf.createEntityManager();
            Session session = manager.unwrap(Session.class);

            session.beginTransaction();
            session.save(lock);
            manager.getTransaction().commit();

            manager.close();
            addLockEvent(lock);

            if (!Objects.isNull(lock.getBudgetItem())) {
                List<Budget> list = new ArrayList<>(BudgetDAO.getInstance().getItems().values());
                if (list.isEmpty()) {
                    BudgetDAO.getInstance().addBudgetData();
                }
                new BudgetDAO().addBlockedAmount(lock.getBudgetItem().getIdItem(), lock.getAmount());
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        }
    }

    public void addWithIdNoEvent(BudgetLock lock) {
        try {
            EntityManager manager = emf.createEntityManager();
            Session session = manager.unwrap(Session.class);

            session.beginTransaction();
            session.save(lock);
            manager.getTransaction().commit();

            manager.close();
            if (!Objects.isNull(lock.getBudgetItem())) {
                new BudgetDAO().addBlockedAmount(lock.getBudgetItem().getIdItem(), lock.getAmount());
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        }
    }

    public void removeLock(BudgetLock lock) {
        try {

            EntityManager manager = emf.createEntityManager();

            manager.getTransaction().begin();
            manager.remove(manager.contains(lock) ? lock : manager.merge(lock));
            manager.flush();
            manager.clear();
            manager.getTransaction().commit();
            manager.close();

            removeLockEvent(lock);
            if (!Objects.isNull(lock.getBudgetItem())) {
                new BudgetDAO().removeBlockedAmount(lock.getBudgetItem().getIdItem(), lock.getAmount());
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        }
    }

    public void removeLockEvent(BudgetLock lock) {

        String cmd = String.format("DROP EVENT IF EXISTS %s;", "removeLockEvent_" + lock.getIdLock());

        EntityManager manager = emf.createEntityManager();
        Session session = manager.unwrap(Session.class);
        Query query = session.createSQLQuery(cmd);
        session.beginTransaction();

        query.executeUpdate();

        manager.close();
    }

    public void addLockEvent(BudgetLock lock) {

        String cmd = String.format("CREATE EVENT %s ON SCHEDULE AT :date DO CALL removeLockPrc('%d');", "removeLockEvent_" + lock.getIdLock(), lock.getIdLock());

        EntityManager manager = emf.createEntityManager();
        Session session = manager.unwrap(Session.class);
        Query query = session.createSQLQuery(cmd);
        session.beginTransaction();

        java.sql.Timestamp date = new java.sql.Timestamp(lock.getEndDate().getTime());
        query.setParameter("date", date);
        query.executeUpdate();

        manager.close();
    }

}
