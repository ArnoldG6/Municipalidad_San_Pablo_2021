package sigep.dao;

import common.dao.generic.GenericDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import sigep.model.BiddingAct;

public class BiddingActDAO extends GenericDAO {

    private static BiddingActDAO uniqueInstance;

    public static BiddingActDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new BiddingActDAO();
        }
        return uniqueInstance;
    }
    
     public void insert(BiddingAct request) {
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

    public List<BiddingAct> listAllByOfficial(int idOfficial) {
        String cmd = "SELECT b FROM BiddingAct b WHERE b.beneficiary.idOfficial = :pkOfficial";

        EntityManager manager = emf.createEntityManager();

        Query query = manager.createQuery(cmd);
        query.setParameter("pkOfficial", idOfficial);
        List<BiddingAct> ba = query.getResultList();

        manager.close();

        return ba;
    }

    public List<BiddingAct> listAll() {
        String cmd = "SELECT b FROM BiddingAct b";

        EntityManager manager = emf.createEntityManager();

        Query query = manager.createQuery(cmd);
        List<BiddingAct> bcr = query.getResultList();

        manager.close();

        return bcr;
    }

    public BiddingAct searchById(BiddingAct ba) {
        EntityManager manager = emf.createEntityManager();
        BiddingAct baSearched = (BiddingAct) manager.find(BiddingAct.class, ba.getId());
        manager.close();
        return baSearched;
    }
    
      public void update(BiddingAct request) {
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
