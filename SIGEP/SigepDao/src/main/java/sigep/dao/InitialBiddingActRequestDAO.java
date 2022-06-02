package sigep.dao;

import common.dao.generic.GenericDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.hibernate.Session;
import sigep.model.InitialBiddingActRequest;
import sigep.model.InitialBiddingActRequestId;

public class InitialBiddingActRequestDAO extends GenericDAO {

    private static InitialBiddingActRequestDAO uniqueInstance;

    public static InitialBiddingActRequestDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new InitialBiddingActRequestDAO();
        }
        return uniqueInstance;
    }

    public void insert(InitialBiddingActRequest request) {
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

    public void addWithId(InitialBiddingActRequest request) {
        try {
            EntityManager manager = emf.createEntityManager();
            Session session = manager.unwrap(Session.class);
            

            session.beginTransaction();
            InitialBiddingActRequestId id;
            id = (InitialBiddingActRequestId) session.save(request);
            manager.getTransaction().commit();

            manager.close();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        }
    }

    public void update(InitialBiddingActRequest request) {
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

    public List<InitialBiddingActRequest> listAll() {
        String cmd = "SELECT b FROM InitialBiddingActRequest b";

        EntityManager manager = emf.createEntityManager();

        Query query = manager.createQuery(cmd);
        List<InitialBiddingActRequest> ibar = query.getResultList();

        manager.close();

        return ibar;
    }

    public List<InitialBiddingActRequest> listAllByOfficial(int idOfficial) {
        String cmd = "SELECT b FROM InitialBiddingActRequest b WHERE b.applicant.idOfficial = :pkOfficial";

        EntityManager manager = emf.createEntityManager();

        Query query = manager.createQuery(cmd);
        query.setParameter("pkOfficial", idOfficial);
        List<InitialBiddingActRequest> bc = query.getResultList();

        manager.close();

        return bc;
    }

    public InitialBiddingActRequest searchById(InitialBiddingActRequest iba) {
        EntityManager manager = emf.createEntityManager();
        InitialBiddingActRequest ibaSearched = (InitialBiddingActRequest) manager.find(InitialBiddingActRequest.class, iba.getId());
        manager.close();
        return ibaSearched;
    }

}
