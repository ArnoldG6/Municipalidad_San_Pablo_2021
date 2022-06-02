package sigep.dao;

import common.dao.generic.GenericDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.hibernate.Session;
import sigep.model.Budget;
import sigep.model.PurchaseOrder;

public class PurchaseOrderDAO extends GenericDAO {

    private static PurchaseOrderDAO uniqueInstance;

    public static PurchaseOrderDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new PurchaseOrderDAO();
        }
        return uniqueInstance;
    }

    public void insert(PurchaseOrder order) {
        try {
            EntityManager manager = emf.createEntityManager();

            manager.getTransaction().begin();
            manager.persist(order);
            manager.getTransaction().commit();

            manager.close();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        }
    }

    public void addWithId(PurchaseOrder order) {
        try {
            EntityManager manager = emf.createEntityManager();
            Session session = manager.unwrap(Session.class);

            session.beginTransaction();
            session.save(order);
            manager.getTransaction().commit();

            manager.close();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        }
    }

    public void update(PurchaseOrder order) {
        try {
            EntityManager manager = emf.createEntityManager();

            manager.getTransaction().begin();
            manager.merge(order);
            manager.getTransaction().commit();

            manager.close();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
    public List<PurchaseOrder> listAll() {
        String cmd = "SELECT b FROM PurchaseOrder b";

        EntityManager manager = emf.createEntityManager();

        Query query = manager.createQuery(cmd);
        List<PurchaseOrder> ibar = query.getResultList();

        manager.close();

        return ibar;
    }
    
    public PurchaseOrder findById(PurchaseOrder po) {
        EntityManager manager = emf.createEntityManager();
        PurchaseOrder poSelected = (PurchaseOrder) manager.find(PurchaseOrder.class, po.getId());
        manager.close();
        return poSelected;
    }

    public List<PurchaseOrder> listAllByOfficial(int idOfficial) {
        String cmd = "SELECT b FROM PurchaseOrder b WHERE b.transmitter.idOfficial = :pkOfficial";

        EntityManager manager = emf.createEntityManager();

        Query query = manager.createQuery(cmd);
        query.setParameter("pkOfficial", idOfficial);
        List<PurchaseOrder> bc = query.getResultList();

        manager.close();

        return bc;
    }

}
