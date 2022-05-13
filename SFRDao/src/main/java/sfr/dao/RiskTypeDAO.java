package sfr.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import static sfr.dao.GenericDAO.em;
import sfr.model.RiskType;

/**
 *
 * @author Luis D
 */
public class RiskTypeDAO extends GenericDAO {

    private static RiskTypeDAO uniqueInstance; //Singleton Pattern Object

    /**
     * @return the Singleton Pattern Object of RiskTypeDAO class.
     */
    public static RiskTypeDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new RiskTypeDAO();
        }
        return uniqueInstance;
    }

    /**
     * @return a sorted list of RiskType objects depending on the next
     * parameters:
     * @throws java.lang.Exception
     */
    public List<RiskType> listAll() throws Exception {
        try {
            em = getEntityManager();
            Query query = em.createQuery("SELECT p from sfr.model.RiskType p");
            return (List<RiskType>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }

    /**
     * @return a casted HashMap of RiskType objects from an HQL query
     * @throws java.lang.Exception
     */
    public Map<String, List<RiskType>> listAllPlanTypeHM() throws Exception {
        HashMap<String, List<RiskType>> data = new HashMap<>();
        List<RiskType> dataList = this.listAll();
        List<RiskType> parents = new ArrayList<>();
        //Parents
        dataList.forEach(d -> {
            if (d.getParent() == null) {
                parents.add(d);
            }
        });

        data.put("parents", parents);

        //Children
        parents.forEach(p -> {
            List<RiskType> children = new ArrayList<>();
            dataList.forEach(d -> {
                if (d.getParent() != null) {
                    if (d.getParent().getId().equals(p.getId())) {
                        children.add(d);
                        List<RiskType> grandchildren = new ArrayList<>();
                        dataList.forEach(g -> {
                            if (g.getParent() != null) {
                                if (g.getParent().getId().equals(d.getId())) {
                                    grandchildren.add(g);
                                }
                            }
                        });
                        data.put(d.getName(), grandchildren);
                    }
                }
            });
            data.put(p.getName(), children);

        });
        return data;
    }

    /**
     * This method is used to obtain a number to be used when creating an ID for
     * a Risk. It searches a field on the RiskType table for said number, adds
     * one to it and proceeds to update it for future use.
     *
     * @param id String containing the ID Name of a RiskType
     * @return a number to be used for a Risk ID upon creation
     */
    public int handleIDAmount(String id) {
        // Get Amount
        try {
            String cmd = "SELECT r FROM RiskType r WHERE r.idName = '" + id + "'";
            em = getEntityManager();
            Query query = em.createQuery(cmd);
            RiskType r = (RiskType) query.getSingleResult();
            int amount = r.getId_amount() + 1;
            //Update Amount
            r.setId_amount(amount);
            this.update(r);
            return amount;
        } catch (Exception ex) {
            Logger.getLogger(RiskTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }

    /**
     * This method updates a RiskType object from the DB record, using HQL.
     *
     * @param risk is the RiskType Object to be updated.
     */
    public void update(RiskType risk) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(risk);
            em.getTransaction().commit();

        } catch (Exception ex) {
            Logger.getLogger(RiskTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }

}
