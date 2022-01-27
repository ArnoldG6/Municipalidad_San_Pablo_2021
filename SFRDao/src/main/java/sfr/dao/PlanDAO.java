package sfr.dao;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import javax.persistence.Query;
import sfr.model.Plan;
import org.hibernate.Session;
import sfr.model.Risk;

/**
 *
 * @author ArnoldG6
 * PlanDAO class is it's responsible of establishing connection with Hibernate framework
 * in order to cast Java objects from HQL queries.
 */
public class PlanDAO extends GenericDAO {
    private static PlanDAO uniqueInstance; //Singleton Pattern Object
    //Returns Singleton Pattern Object of PlanDAO class.
    public static PlanDAO getInstance() {
        if (uniqueInstance == null) 
            uniqueInstance = new PlanDAO();
        return uniqueInstance;

    }
    /**
    *  @return a translated column name into an attribute's name (of Plan class) 
    *  in order to use Hibernate's queries defined in this file.
    *  @param column specifies the desired column name to check if it can be translated.
    *  @param order specifies the ascendent or descendent order keyword to be translated. 
     * @throws java.io.IOException 
    */
    public String translateColumnName(String column, String order) throws IOException {
        order = order.toUpperCase();
        if (!(equals("ASC")|| order.equals("DESC"))) {
            throw new IOException("Invalid order parameter");
        }
        switch (column.toUpperCase()) {
            case "PK_ID": return "id";
            case "NAME":  return "name";
            case "DESCRIPTION": return "description";
            case "AUTHORNAME": return "authorName";
            case "ENTRYDATE": return "entryDate";
            case "STATUS": return "status";
            case "TYPE": return "type";
            default:
                throw new IOException("Invalid column");
        }
    }
    /**
    *  @return a sorted list of Plan objects depending on the next parameters: 
    *  @param column specifies the sorting criteria from the selected column.
    *  @param order specifies the ascendent or descendent order criteria.
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
    *  @return a casted HashMap of Plan objects from an HQL query sorted in descending order by entryDate.
     * @throws java.lang.Exception
    */
    public HashMap<String, Plan> listAllHM() throws Exception {
        HashMap<String, Plan> plans = new HashMap<>();
        List<Plan> plansList = this.listByColumn("ENTRYDATE","DESC");
        plansList.forEach(p -> {
            plans.put(p.getId(), p);
        });
        return plans;
    }
    /**
    * This method adds a Plan object into the DB record, using HQL.
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
     * @param planID the Plan object that will be associated to the Risk objects
     * @param riskIDs List of riskIDs to associate with planID parameter.
     * @throws java.lang.Exception
     */
    public void associateRisksToPlan(String planID, List<Integer> riskIDs) throws Exception {
        try {
            if (planID == null) {
                throw new IOException("Invalid planID field");
            }
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
            if(riskIDs.isEmpty())
                throw new IOException("Empty riskIDs field exception");
            Risk r;
            for (int i = 0; i < riskIDs.size(); i++) {
                r = RiskDAO.getInstance().searchByIdSmall(riskIDs.get(i));
                if (!riskList.contains(r)) 
                    riskList.add(r);
                else
                    throw new IOException("This plan already contains this risk");
            }
            p.setRiskList(riskList);
            PlanDAO.getInstance().update(p);

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
     * @return a list of risks, including all risks, except for the ones in the
     * Plan identified by 
     * @param planID
     * @throws java.lang.Exception
     */
    public List<Risk> getRiskListByPlanNoRep(String planID) throws Exception {
        try {
            if (planID == null) {
                throw new IOException("Invalid planID field");
            }
            Plan p = PlanDAO.getInstance().searchById(planID);
            List<Risk> pRiskList = p.getRiskList(); //risks of an specific Plan.
            List<Risk> riskList = RiskDAO.getInstance().listByColumn("ENTRYDATE","DESC");
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
     * @param id
     * @return a Plan object that matches with
     */
    public Plan searchById(String id) {
        em = getEntityManager();
        return (Plan) em.find(Plan.class,id);
    }
     /**
     * 
     *
     * @return a Plan object list which contains matched attribute toString() values with
     * @param value 
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
                        || p.matcher(String.format("%tB",pl.getEntryDate())).find() 
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

     /**
     * 
     *
     * @return a Plan object list that is paged according to 
     * @param page 
     */
    public List<Plan> listTenPlans(int page) {
        try {
            String cmd = "CALL selectTenPlans(" + page + "0)";

            em = getEntityManager();
            Session session = em.unwrap(Session.class
            );

            Query query = session.createSQLQuery(cmd);
            List<Plan> objList = (List<Plan>) query.getResultList();

            List<Plan> l = new ArrayList<>();
            Iterator itr = objList.iterator();

            //Hibernate consigue lista de Object
            //Realizar conversion al objeto correcto
            //Posiciones estan basadas en las posiciones de la tabla en la base de datos
            while (itr.hasNext()) {
                Object[] obj = (Object[]) itr.next();

                Plan p = new Plan(String.valueOf(obj[0]));
                p.setName(String.valueOf(obj[1]));
                p.setDesc(String.valueOf(obj[2]));
                p.setEntryDate((Date) (obj[3]));
                p.setStatus(String.valueOf(obj[4]));
                p.setAuthorName(String.valueOf(obj[5]));
                p.setType(String.valueOf(obj[6]));

                l.add(p);
            }

            return l;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }
    /**
     * @return a Plan object list that is paged according to 
     * @param page 
     */
    public long countPlans() {
        try {
            String cmd = "SELECT count(*) FROM Plan";
            em = getEntityManager();
            Query query = em.createQuery(cmd);
            long cantPlans = (long) query.getSingleResult();
            return cantPlans;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }

}
