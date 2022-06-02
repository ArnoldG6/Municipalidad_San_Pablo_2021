package sigep.dao;

import common.dao.generic.GenericDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import sigep.model.BudgetCodeCertificate;

public class BudgetCodeCertificateDAO extends GenericDAO {

    private static BudgetCodeCertificateDAO uniqueInstance;

    public static BudgetCodeCertificateDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new BudgetCodeCertificateDAO();
        }
        return uniqueInstance;
    }

    public List<BudgetCodeCertificate> listAll() {
        String cmd = "SELECT b FROM BudgetCodeCertificate b";

        EntityManager manager = emf.createEntityManager();

        Query query = manager.createQuery(cmd);
        List<BudgetCodeCertificate> bc = query.getResultList();

        manager.close();

        return bc;
    }

    public List<BudgetCodeCertificate> listAllByOfficial(int idOfficial) {
        String cmd = "SELECT b FROM BudgetCodeCertificate b WHERE b.beneficiary.idOfficial = :pkOfficial";

        EntityManager manager = emf.createEntityManager();

        Query query = manager.createQuery(cmd);
        query.setParameter("pkOfficial", idOfficial);
        List<BudgetCodeCertificate> bc = query.getResultList();

        manager.close();

        return bc;
    }

    public BudgetCodeCertificate searchById(BudgetCodeCertificate budgetBC) {
        EntityManager manager = emf.createEntityManager();
        BudgetCodeCertificate budgetSearched = (BudgetCodeCertificate) manager.find(BudgetCodeCertificate.class, budgetBC.getId());
        manager.close();
        return budgetSearched;
    }

    public void insert(BudgetCodeCertificate request) {
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

}
