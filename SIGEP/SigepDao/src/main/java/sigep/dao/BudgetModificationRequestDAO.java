package sigep.dao;

import common.dao.generic.GenericDAO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import sigep.model.BudgetBalanceCertificateRequest;
import sigep.model.BudgetModificationRequest;

/**
 *
 * @author Diego Quiros
 */
public class BudgetModificationRequestDAO extends GenericDAO {
    
    private static BudgetModificationRequestDAO uniqueInstance;

    public static BudgetModificationRequestDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new BudgetModificationRequestDAO();
        }
        return uniqueInstance;
    }

    public void insert(BudgetModificationRequest request) {
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
    
    public BudgetModificationRequest searchById(BudgetModificationRequest msp) {
        EntityManager manager = emf.createEntityManager();
        BudgetModificationRequest mspSearched = (BudgetModificationRequest) manager.find(BudgetModificationRequest.class, msp.getId());
        manager.close();
        return mspSearched;
    }
    
    public BudgetModificationRequest getLastData(){
        String cmd = "SELECT b FROM BudgetModificationRequest b";

        EntityManager manager = emf.createEntityManager();

        Query query = manager.createQuery(cmd);
        List<BudgetModificationRequest> msps = query.getResultList();
        
        manager.close();
        BudgetModificationRequest msp =  msps.get(msps.size()-1);;
        return msp;
    }
    
    
    public List<BudgetModificationRequest> listAll() {
        String cmd = "SELECT b FROM BudgetModificationRequest b";

        EntityManager manager = emf.createEntityManager();

        Query query = manager.createQuery(cmd);
        List<BudgetModificationRequest> bcr = query.getResultList();

        manager.close();

        return bcr;
    }
    
    public List<BudgetModificationRequest> listAllByStatus() {
        String cmd = "SELECT b FROM BudgetModificationRequest b";

        EntityManager manager = emf.createEntityManager();

        Query query = manager.createQuery(cmd);
        List<BudgetModificationRequest> msps = query.getResultList();
        
         List<BudgetModificationRequest> filter = new ArrayList<>();
        for (BudgetModificationRequest b : msps) {
            if (b.getStatus().getIdRequestStatus() == 1) {
                filter.add(b);
            }
        }
        manager.close();

        return filter;
    }
    
      public List<BudgetModificationRequest> listAllByOfficial(int idOfficial) {
        String cmd = "SELECT b FROM BudgetModificationRequest b WHERE b.applicant.idOfficial = :pkOfficial";

        EntityManager manager = emf.createEntityManager();

        Query query = manager.createQuery(cmd);
        query.setParameter("pkOfficial", idOfficial);
        List<BudgetModificationRequest> ba = query.getResultList();

        manager.close();

        return ba;
    }
      
      
      public void update(BudgetModificationRequest request) {
        try {
            EntityManager manager = emf.createEntityManager();
            
            manager.getTransaction().begin();
            manager.merge(request);
            manager.getTransaction().commit();
            
            manager.close();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        } 
    }
}
