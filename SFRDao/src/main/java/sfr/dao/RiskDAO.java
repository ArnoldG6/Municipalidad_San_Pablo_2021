/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfr.dao;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Query;
import static sfr.dao.GenericDAO.em;
import sfr.model.Risk;

/**
 *
 * @author Arnold y Migue
 */
public class RiskDAO extends GenericDAO {

    private static RiskDAO uniqueInstance;

    public static RiskDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new RiskDAO();
        }

        return uniqueInstance;
    }

    public List<Risk> listAll() {
        try {
            String cmd = "SELECT r FROM Risk r";
            em = getEntityManager();
            Query query = em.createQuery(cmd);
            return (List<Risk>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }

    public List<Risk> listByColumn(String column, String order) throws Exception {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    public HashMap<String, Risk> listAllHM() {
        HashMap<String, Risk> Risks = new HashMap<>();
        List<Risk> RisksList = this.listAll();
        RisksList.forEach(p -> {
            Risks.put(p.getId(), p);
        });
        return Risks;
    }

    public void add(Risk risk) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(risk);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        } finally {
            closeEntityManager();
        }
    }

    public void update(Risk risk) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(risk);
            em.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        } finally {
            closeEntityManager();
        }
    }

    public void delete(Risk risk) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.remove(em.merge(risk));
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        } finally {
            closeEntityManager();
        }
    }

    public Risk searchByIdSmall(String id) {
        em = getEntityManager();
        return (Risk) em.find(Risk.class, id);
    }

    public Risk searchById(String id) {
        try {
            em = getEntityManager();
            return (Risk) em.find(Risk.class, id);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }

    public List<Risk> listSearchBy(String toSearch, String value) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    public List<Risk> listTenRisks(int page) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    public long countRisks() {
        try {
            String cmd = "SELECT count(*) FROM Risk";
            em = getEntityManager();
            Query query = em.createQuery(cmd);
            long cantRisks = (long) query.getSingleResult();
            return cantRisks;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }
}
