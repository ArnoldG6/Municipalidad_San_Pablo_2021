/**
 *
 * @author ArnoldG6
 * RiskDAO class is responsible of establishing connection with Hibernate framework
 * in order to cast Java objects from HQL queries.
 */
package sfr.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import javax.persistence.Query;
import static sfr.dao.GenericDAO.em;
import sfr.model.Plan;
import sfr.model.Risk;

public class RiskDAO extends GenericDAO {

    private static RiskDAO uniqueInstance;
       /**
     @return the Singleton Pattern Object of RiskDAO class.
      */
    public static RiskDAO getInstance() {
        if (uniqueInstance == null)
            uniqueInstance = new RiskDAO();
        return uniqueInstance;
    }
    /**
    *  @return a translated column name into an attribute's name (of Risk class) 
    *  in order to use Hibernate's queries defined in this file.
    *  @param column specifies the desired column name to check if it can be translated.
    *  @param order specifies the 'ascendent' or 'descendent' order keyword to be translated. 
     * @throws java.io.IOException 
    */
    public String translateColumnName(String column, String order) throws IOException {
        order = order.toUpperCase();
        if (!(order.equals("ASC")|| order.equals("DESC"))) {
            throw new IOException("Invalid order parameter");
        }
        switch (column.toUpperCase()) {
            case "PK_ID":
                return "id";
            case "NAME":
                return "name";
            case "FACTORS":
                return "factors";
            case "GENERALTYPE":
                return "generalType";
            case "AREATYPE":
                return "areaType";
            case "SPECTYPE":
                return "specType";
            case "PROBABILITY":
                return "probability";
            case "IMPACT":
                return "impact";
            case "MAGNITUDE":
                return "magnitude";
            case "MITIGATIONMEASURES":
                return "mitigationMeasures";
            default:
                throw new IOException("Invalid column");
        }
    }
    /**
    *  @return a sorted list of Risk objects depending on the next parameters: 
    *  @param column specifies the sorting criteria from the selected column. Default value for this project is: "PK_ID".
    *  @param order specifies the 'ascendent' or 'descendent' sorting criteria. Default value for this project is: "DESC".
     * @throws java.lang.Exception
    */
    public List<Risk> listByColumn(String column, String order) throws Exception {
        try {
            order = order.toUpperCase();
            column = this.translateColumnName(column, order);
            String cmd = new StringBuilder().append("SELECT r from Risk r order by r.")
            .append(column).append(" ").append(order).toString();
            em = getEntityManager();
            Query query = em.createQuery(cmd);
            return (List<Risk>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }
    /**
    *  @return a casted HashMap of Risk objects from an HQL query sorted by default param values (defined in this project).
     * @throws java.lang.Exception
    */
    public HashMap<Integer, Risk> listAllHM() throws Exception {
        HashMap<Integer, Risk> Risks = new HashMap<>();
        List<Risk> RisksList = this.listByColumn("PK_ID","DESC");
        RisksList.forEach(p -> {
            Risks.put(p.getId(), p);
        });
        return Risks;
    }
    /**
    * This method adds a Risk object into the DB record, using HQL.
    * @param risk is the Risk Object to be added.
    */
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
       /**
     * This method updates a Risk object from the DB record, using HQL.
     * @param risk is the Risk Object to be updated.
     */
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
     /**
     * This method deletes a Risk object from the DB record, using HQL.
     * @param risk is the Risk Object to be deleted.
     */
    public void delete(Risk risk) throws Exception {
        try {
            List<Plan> pl = PlanDAO.getInstance().listByColumn("ENTRYDATE","DESC");
            List<Risk> rl;
            for (int i = 0; i < pl.size(); i++) {
                try {
                    rl = pl.get(i).getRiskList();
                    if(rl != null){
                        if(rl.contains(risk)){
                            rl.remove(risk);
                            pl.get(i).setRiskList(rl);
                            PlanDAO.getInstance().update(pl.get(i));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                    System.err.println(e.getMessage());
                }
            }
            em = getEntityManager();
            em.getTransaction().begin();
            em.remove(em.merge(risk));
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }
     /**
     * 
     * @return a Risk object that matches with
     *@param id
     */

    public Risk searchById(int id) {
        try {
            em = getEntityManager();
            return (Risk) em.find(Risk.class, id);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }
     /**
     * 
     * @return a List<Risk> object that matches with 
     * @param value, also considering 
     * @param toSearch, which is the attribute's name.
     */
    public List<Risk> listSearchBy(String toSearch, String value) {
        try {
            String cmd = "SELECT r FROM Risk r WHERE "
                    + "r." + toSearch + " LIKE '" + value + "%'";
            em = getEntityManager();
            Query query = em.createQuery(cmd);
            return (List<Risk>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }
    /**
     * 
     *
     * @return a List<Risk> object which contains matched attribute toString() values with
     * @param value 
     */
    public List<Risk> searchInAllColumns(String value) throws Exception {
        try {
            HashMap<Integer, Risk> resultHM = this.listAllHM();
            Pattern p = Pattern.compile(value, Pattern.CASE_INSENSITIVE);
            ArrayList<Risk> result = new ArrayList<>();
            for (HashMap.Entry<Integer, Risk> risk : resultHM.entrySet()) {
                Risk r = risk.getValue();
                if (p.matcher(String.valueOf(r.getId())).find() || p.matcher(r.getName()).find()
                        || p.matcher(r.getFactors()).find() || p.matcher(r.getGeneralType()).find()
                        || p.matcher(r.getAreaType()).find() || p.matcher(r.getSpecType()).find()
                        || p.matcher(String.valueOf(r.getProbability())).find() || p.matcher(String.valueOf(r.getImpact())).find()
                        || // p.matcher(String.valueOf(r.getMagnitude())).find()||p.matcher(r.getMitigationMeasures()).find()
                        p.matcher(String.valueOf(r.getMagnitude())).find()) {
                    result.add(r);
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
     * @return a List<Risk> object that is paged according to 
     * @param page 
     */
    public List<Risk> listTenRisks(int page) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }
     /**
     * @return the entry count of Plan objects.
     */
    public long riskCount() {
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
