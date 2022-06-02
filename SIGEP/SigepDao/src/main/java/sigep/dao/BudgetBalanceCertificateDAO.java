package sigep.dao;

import common.dao.generic.GenericDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import sigep.model.BudgetBalanceCertificate;
import sigep.model.BudgetBalanceCertificateRequest;

public class BudgetBalanceCertificateDAO extends GenericDAO {

    private static BudgetBalanceCertificateDAO uniqueInstance;

    public static BudgetBalanceCertificateDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new BudgetBalanceCertificateDAO();
        }
        return uniqueInstance;
    }

    public List<BudgetBalanceCertificate> listAll() {
        String cmd = "SELECT b FROM BudgetBalanceCertificate b";

        EntityManager manager = emf.createEntityManager();

        Query query = manager.createQuery(cmd);
        List<BudgetBalanceCertificate> bc = query.getResultList();

        manager.close();

        return bc;
    }

    public List<BudgetBalanceCertificate> listAllByOfficial(int idOfficial) {
        String cmd = "SELECT b FROM BudgetBalanceCertificate b WHERE b.beneficiary.idOfficial = :pkOfficial";

        EntityManager manager = emf.createEntityManager();

        Query query = manager.createQuery(cmd);
        query.setParameter("pkOfficial", idOfficial);
        List<BudgetBalanceCertificate> bc = query.getResultList();

        manager.close();

        return bc;
    }

    public BudgetBalanceCertificate searchById(BudgetBalanceCertificate budgetBC) {
        EntityManager manager = emf.createEntityManager();
        BudgetBalanceCertificate budgetSearched = (BudgetBalanceCertificate) manager.find(BudgetBalanceCertificate.class, budgetBC.getId());
        manager.close();
        return budgetSearched;
    }

    public void insert(BudgetBalanceCertificate request) {
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
    
    public void update(BudgetBalanceCertificate csp) {
        try {
            EntityManager manager = emf.createEntityManager();
            
            manager.getTransaction().begin();
            manager.merge(csp);
            manager.getTransaction().commit();
            
            manager.close();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        } 
    }

}
