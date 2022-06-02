/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sigep.dao;

import common.dao.generic.GenericDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import sigep.model.BudgetBalanceCertificateRequest;
import sigep.model.BudgetCodeCertificateRequests;

/**
 *
 * @author Diego Quiros
 */
public class BudgetCodeCertificateRequestsDAO extends GenericDAO {

    private static BudgetCodeCertificateRequestsDAO uniqueInstance;

    public static BudgetCodeCertificateRequestsDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new BudgetCodeCertificateRequestsDAO();
        }
        return uniqueInstance;
    }

    public void insert(BudgetCodeCertificateRequests request) {
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

    public List<BudgetCodeCertificateRequests> listAll() {
        String cmd = "SELECT b FROM BudgetCodeCertificateRequests b";

        EntityManager manager = emf.createEntityManager();

        Query query = manager.createQuery(cmd);
        List<BudgetCodeCertificateRequests> bcr = query.getResultList();

        manager.close();

        return bcr;
    }

    public List<BudgetCodeCertificateRequests> listAllByOfficial(int idOfficial) {
        String cmd = "SELECT b FROM BudgetCodeCertificateRequests b WHERE b.applicant.idOfficial = :pkOfficial";

        EntityManager manager = emf.createEntityManager();

        Query query = manager.createQuery(cmd);
        query.setParameter("pkOfficial", idOfficial);
        List<BudgetCodeCertificateRequests> ba = query.getResultList();

        manager.close();

        return ba;
    }
    
    public BudgetCodeCertificateRequests searchById(BudgetCodeCertificateRequests budgetBCR) {
        EntityManager manager = emf.createEntityManager();
        BudgetCodeCertificateRequests budgetSearched = (BudgetCodeCertificateRequests) manager.find(BudgetCodeCertificateRequests.class, budgetBCR.getId());
        manager.close();
        return budgetSearched;
    }
    
    public void delete(BudgetCodeCertificateRequests request) {
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
    
    public void update(BudgetCodeCertificateRequests request) {
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
