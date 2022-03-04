/**
 *
 * @author ArnoldG6
 * PlanDAO class is responsible of establishing connection with Hibernate framework
 * in order to cast Java objects from HQL queries.
 */
package sfr.dao;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.persistence.Query;
import sfr.model.Plan;
import sfr.model.Risk;

public class PlanDAO extends GenericDAO {

    private static PlanDAO uniqueInstance; //Singleton Pattern Object

    /**
     * @return the Singleton Pattern Object of PlanDAO class.
     */
    public static PlanDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new PlanDAO();
        }
        return uniqueInstance;

    }

    /**
     * @return a translated column name into an attribute's name (of Plan class)
     * in order to use Hibernate's queries defined in this file.
     * @param column specifies the desired column name to check if it can be
     * translated.
     * @param order specifies the 'ascendent' or 'descendent' order keyword to
     * be translated.
     * @throws java.io.IOException
     */
    public String translateColumnName(String column, String order) throws IOException {
        order = order.toUpperCase();
        if (!(order.equals("ASC") || order.equals("DESC"))) {
            throw new IOException("Invalid order parameter");
        }
        switch (column.toUpperCase()) {
            case "PK_ID":
                return "pkID";
            case "ID":
                return "id";
            case "NAME":
                return "name";
            case "DESCRIPTION":
                return "description";
            case "AUTHORNAME":
                return "authorName";
            case "ENTRYDATE":
                return "entryDate";
            case "STATUS":
                return "status";
            case "TYPE":
                return "type";
            case "SUBTYPE":
                return "subtype";
            default:
                throw new IOException("Invalid column");
        }
    }

    /**
     * @return a sorted list of Plan objects depending on the next parameters:
     * @param column specifies the sorting criteria from the selected column.
     * Default value for this project is: "ENTRYDATE".
     * @param order specifies the 'ascendent' or 'descendent' sorting criteria.
     * Default value for this project is: "DESC".
     * @throws java.lang.Exception
     */
    public List<Plan> listByColumn(String column, String order) throws Exception {
        try {
            order = order.toUpperCase();
            column = this.translateColumnName(column, order);
            String cmd = new StringBuilder().append("SELECT p from Plan p order by p.")
                    .append(column).append(" ").append(order).toString();
            em = getEntityManager();
            Query query = em.createQuery(cmd);
            return (List<Plan>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }

    /**
     * @return a casted HashMap of Plan objects from an HQL query sorted in
     * descending order by entryDate.
     * @throws java.lang.Exception
     */
    public HashMap<String, Plan> listAllHM() throws Exception {
        HashMap<String, Plan> plans = new HashMap<>();
        List<Plan> plansList = this.listByColumn("ENTRYDATE", "DESC");
        plansList.forEach(p -> {
            plans.put(p.getId(), p);
        });
        return plans;
    }

    /**
     * This method adds a Plan object into the DB record, using HQL.
     *
     * @param plan is the Plan Object to be added.
     */
    public void add(Plan plan) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(plan);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        } finally {
            closeEntityManager();
        }
    }

    /**
     * This method updates a Plan object into the DB record, using HQL.
     *
     * @param plan is the Plan Object to be updated.
     */
    public void update(Plan plan) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(plan);
            em.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        } finally {
            closeEntityManager();
        }
    }

    /**
     * This method deletes a Plan object into the DB record, using HQL.
     *
     * @param plan is the Plan Object to be deleted.
     */
    public void delete(Plan plan) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.remove(em.merge(plan));
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        } finally {
            closeEntityManager();
        }
    }

    /**
     * This method associates a single Plan object to n Risk objects.
     *
     * @param planID the Plan object that will be associated to the Risk objects
     * @param riskIDs List of riskIDs to associate with planID parameter.
     * @throws java.lang.Exception
     */
    public void associateRisksToPlan(int planID, List<Integer> riskIDs) throws Exception {
        try {
            if (riskIDs == null) {
                throw new IOException("Invalid RiskIDs field");
            }
            Plan p = PlanDAO.getInstance().searchById(planID);
            if (p == null) {
                throw new IOException("Invalid planID field");
            }
            List<Risk> riskList = p.getRiskList();
            if (riskList == null) {
                throw new IOException("Empty riskList exception");
            }
            if (riskIDs.isEmpty()) {
                throw new IOException("Empty riskIDs field exception");
            }
            Risk r;
            for (int i = 0; i < riskIDs.size(); i++) {
                r = RiskDAO.getInstance().searchById(riskIDs.get(i));
                if (!riskList.contains(r)) {
                    riskList.add(r);
                } else {
                    throw new IOException("This plan already contains this risk");
                }
            }
            p.setRiskList(riskList);
            PlanDAO.getInstance().update(p);

        } catch (IOException ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }

    /**
     *
     * @return a list of risks, including all risks, except for the ones in the
     * Plan identified by
     * @param planID
     * @throws java.lang.Exception
     */
    public List<Risk> getRiskListByPlanNoRep(String planID) throws Exception {
        try {
            Plan p = PlanDAO.getInstance().searchByIdString(planID);
            List<Risk> pRiskList = p.getRiskList(); //risks of an specific Plan.
            List<Risk> riskList = RiskDAO.getInstance().listByColumn("PK_ID", "DESC");
            if (pRiskList == null || riskList == null) {
                throw new IOException("Empty riskList exception");
            }
            for (int i = 0; i < pRiskList.size(); i++) {
                if (riskList.contains(pRiskList.get(i))) {
                    riskList.remove(pRiskList.get(i));
                }
            }
            return riskList;
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }

    /**
     *
     * @return a Plan object that matches with
     * @param id
     */
    public Plan searchById(int id) {
        em = getEntityManager();
        return (Plan) em.find(Plan.class, id);
    }

    /**
     *
     * @return a Plan object that matches with
     * @param id
     */
    public Plan searchByIdString(String id) {
        try {
            HashMap<String, Plan> planes = this.listAllHM();
            return planes.get(id);
        } catch (Exception e) {

        }
        return null;
    }

    /**
     *
     *
     * @return a Plan object list which contains matched attribute toString()
     * values with
     * @param value
     * @throws java.lang.Exception
     */
    public List<Plan> searchInAllColumns(String value) throws Exception {
        try {
            HashMap<String, Plan> resultHM = this.listAllHM();
            SimpleDateFormat dateFor = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            Pattern p = Pattern.compile(new StringBuilder().append("(.*)").append(value).append("(.*)").toString(),
                    Pattern.CASE_INSENSITIVE);
            ArrayList<Plan> result = new ArrayList<>();
            for (HashMap.Entry<String, Plan> plan : resultHM.entrySet()) {
                Plan pl = plan.getValue();

                //
                if (p.matcher(pl.getId()).find() || p.matcher(pl.getAuthorName()).find()
                        || p.matcher(pl.getName()).find() || p.matcher(pl.getDesc()).find()
                        || p.matcher(dateFor.format(pl.getEntryDate())).find() || p.matcher(pl.getStatus()).find()
                        || p.matcher(String.format("%tB", pl.getEntryDate())).find()
                        || p.matcher(pl.getType()).find()) {
                    result.add(pl);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }

}
