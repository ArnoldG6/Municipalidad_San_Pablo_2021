package sigep.dao;

import common.dao.generic.GenericDAO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import sigep.model.BudgetBalanceCertificateRequest;

public class BudgetBalanceCertificateRequestDAO extends GenericDAO {

    private static BudgetBalanceCertificateRequestDAO uniqueInstance;

    public static BudgetBalanceCertificateRequestDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new BudgetBalanceCertificateRequestDAO();
        }
        return uniqueInstance;
    }

    public void insert(BudgetBalanceCertificateRequest request) {
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
    
    public List<BudgetBalanceCertificateRequest> listAll() {
        String cmd = "SELECT b FROM BudgetBalanceCertificateRequest b";

        EntityManager manager = emf.createEntityManager();

        Query query = manager.createQuery(cmd);
        List<BudgetBalanceCertificateRequest> bcr = query.getResultList();

        manager.close();

        return bcr;
    }
    
    public List<BudgetBalanceCertificateRequest> listAllByStatus() {
        String cmd = "SELECT b FROM BudgetBalanceCertificateRequest b";

        EntityManager manager = emf.createEntityManager();

        Query query = manager.createQuery(cmd);
        List<BudgetBalanceCertificateRequest> bcr = query.getResultList();
        
         List<BudgetBalanceCertificateRequest> filter = new ArrayList<>();
        for (BudgetBalanceCertificateRequest b : bcr) {
            if (b.getStatus().getIdRequestStatus() == 1) {
                filter.add(b);
            }
        }

        manager.close();

        return filter;
    }
    
     public List<BudgetBalanceCertificateRequest> listAllByOfficial(int idOfficial) {
        String cmd = "SELECT b FROM BudgetBalanceCertificateRequest b WHERE b.applicant.idOfficial = :pkOfficial";

        EntityManager manager = emf.createEntityManager();

        Query query = manager.createQuery(cmd);
        query.setParameter("pkOfficial", idOfficial);
        List<BudgetBalanceCertificateRequest> ba = query.getResultList();

        manager.close();

        return ba;
    }
    
    public BudgetBalanceCertificateRequest searchById(BudgetBalanceCertificateRequest budgetBCR) {
        EntityManager manager = emf.createEntityManager();
        BudgetBalanceCertificateRequest budgetSearched = (BudgetBalanceCertificateRequest) manager.find(BudgetBalanceCertificateRequest.class, budgetBCR.getId());
        manager.close();
        return budgetSearched;
    }
    
    public void delete(BudgetBalanceCertificateRequest request) {
        try {
            EntityManager manager = emf.createEntityManager();
            
            manager.getTransaction().begin();
            manager.remove(manager.merge(request));
            manager.getTransaction().commit();
            
            manager.close();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        } 
    }
    
    public void update(BudgetBalanceCertificateRequest request) {
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
