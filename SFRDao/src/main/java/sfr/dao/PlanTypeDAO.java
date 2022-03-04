package sfr.dao;

import java.util.ArrayList;
import java.util.HashMap;
import sfr.model.PlanType;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import static sfr.dao.GenericDAO.em;

/**
 *
 * @author Luis D
 */
public class PlanTypeDAO extends GenericDAO {

    private static PlanTypeDAO uniqueInstance; //Singleton Pattern Object

    /**
     * @return the Singleton Pattern Object of PlanTypeDAO class.
     */
    public static PlanTypeDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new PlanTypeDAO();
        }

        return uniqueInstance;
    }

    /**
     * @return a sorted list of PlanType objects depending on the next
     * parameters:
     * @throws java.lang.Exception
     */
    public List<PlanType> listAll() throws Exception {
        try {
            em = getEntityManager();
            Query query = em.createQuery("SELECT p from sfr.model.PlanType p");
            return (List<PlanType>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }

    /**
     * @return a casted HashMap of PlanType objects from an HQL query
     * @throws java.lang.Exception
     */
    public Map<String, List<PlanType>> listAllPlanTypeHM() throws Exception {
        HashMap<String, List<PlanType>> data = new HashMap<>();
        List<PlanType> dataList = this.listAll();
        List<PlanType> parents = new ArrayList<>();
        //Parents
        dataList.forEach(d -> {
            if (d.getParent() == null) {
                parents.add(d);
            }
        });

        data.put("parents", parents);

        //Children
        parents.forEach(p -> {
            List<PlanType> children = new ArrayList<>();
            dataList.forEach(d -> {
                if (d.getParent() != null) {
                    if (d.getParent().getId().equals(p.getId())) {
                        children.add(d);
                    }
                }
            });
            data.put(p.getName(), children);
        });
        return data;
    }

    /**
     * This method is used to obtain a number to be used when creating an ID for
     * a Plan. It searches a field on the PlanType table for said number, adds
     * one to it and proceeds to update it for future use.
     *
     * @param id String containing the ID Name of a PlanType
     * @return a number to be used for a Plan ID upon creation
     */
    public int handleIDAmount(String id) {
        // Get Amount
        try {
            String cmd = "SELECT p FROM PlanType p WHERE p.idName = '" + id + "'";
            em = getEntityManager();
            Query query = em.createQuery(cmd);
            PlanType p = (PlanType) query.getSingleResult();
            int amount = p.getId_amount() + 1;
            //Update Amount
            p.setId_amount(amount);
            this.update(p);
            return amount;
        } catch (Exception ex) {
            Logger.getLogger(PlanTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }

    /**
     * This method updates a PlanType object from the DB record, using HQL.
     *
     * @param risk is the PlanType Object to be updated.
     */
    public void update(PlanType risk) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(risk);
            em.getTransaction().commit();

        } catch (Exception ex) {
            Logger.getLogger(PlanTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }

}
