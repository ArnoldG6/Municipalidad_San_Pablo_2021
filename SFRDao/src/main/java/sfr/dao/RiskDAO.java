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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.hibernate.Session;
import javax.persistence.Query;
import static sfr.dao.GenericDAO.em;
import sfr.model.Comment;
import sfr.model.Incidence;
import sfr.model.Plan;
import sfr.model.Risk;

public class RiskDAO extends GenericDAO {

    private static RiskDAO uniqueInstance;

    /**
     * @return the Singleton Pattern Object of RiskDAO class.
     */
    public static RiskDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new RiskDAO();
        }
        return uniqueInstance;
    }

    /**
     * @return a translated column name into an attribute's name (of Risk class)
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
     * @return a sorted list of Risk objects depending on the next parameters:
     * @param column specifies the sorting criteria from the selected column.
     * Default value for this project is: "ID".
     * @param order specifies the 'ascendent' or 'descendent' sorting criteria.
     * Default value for this project is: "DESC".
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
        } catch (Exception ex) {
            Logger.getLogger(RiskDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }

    /**
     * @param risk
     * @return an Integer according to the number of Plan objects that contains
     * the risk param.
     * @throws java.lang.Exception
     */

    public Integer countOfRiskAppearenceInPlans(Risk risk) throws Exception {
        try {
            if (risk == null) {
                throw new NullPointerException("Risk Null Pointer Exception");
            }
            Integer count = 0;
            for (Plan p : PlanDAO.getInstance().listByColumn("ENTRYDATE", "DESC")) {
                if (p.containsRisk(risk)) {
                    count += 1;
                }
            }
            return count;
        } catch (Exception ex) {
            Logger.getLogger(RiskDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }

    /**
     * @param r is the Risk object that should be searched in all Incidence
     * objects.
     * @return an Integer according to the number of Risk objects that appears
     * in all Incidence objects.
     * @throws java.lang.Exception
     */

    public Integer countOfRiskAppearenceInIncidences(Risk r) throws Exception {
        try {
            Integer count = 0;
            for (Incidence i : IncidenceDAO.getInstance().listByColumn("DATE", "DESC")) {
                if (i.containsRisk(r)) {
                    count += 1;
                }
            }
            return count;
        } catch (Exception ex) {
            Logger.getLogger(RiskDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }

    /**
     * @return a casted HashMap of Risk objects from an HQL query sorted by
     * default param values (defined in this project).
     * @throws java.lang.Exception
     */
    public HashMap<String, Risk> listAllHMByPK() throws Exception {
        HashMap<String, Risk> Risks = new HashMap<>();
        List<Risk> RisksList = this.listByColumn("ID", "DESC");
        RisksList.forEach(p -> {
            Risks.put(String.valueOf(p.getPkId()), p);
        });
        return Risks;
    }

    public HashMap<String, Risk> listAllHMBbyID() throws Exception {
        HashMap<String, Risk> Risks = new HashMap<>();
        List<Risk> RisksList = this.listByColumn("ID", "DESC");
        RisksList.forEach(p -> {
            Risks.put(p.getId(), p);
        });
        return Risks;
    }

    /**
     * This method adds a Risk object into the DB record, using HQL.
     *
     * @param risk is the Risk Object to be added.
     */
    public void add(Risk risk) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(risk);
            em.getTransaction().commit();
        } catch (Exception ex) {
            Logger.getLogger(RiskDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }

    /**
     * This method updates a Risk object from the DB record, using HQL.
     *
     * @param risk is the Risk Object to be updated.
     */
    public void update(Risk risk) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(risk);
            em.getTransaction().commit();

        } catch (Exception ex) {
            Logger.getLogger(RiskDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }

    /**
     * This method deletes a Risk object from the DB record, using HQL.
     *
     * @param risk is the Risk Object to be deleted.
     */
    public void delete(Risk risk) throws Exception {
        try {
            List<Plan> pl = PlanDAO.getInstance().listByColumn("ENTRYDATE", "DESC");
            List<Risk> rl;
            for (int i = 0; i < pl.size(); i++) {
                try {
                    rl = pl.get(i).getRiskList();
                    if (rl != null) {
                        if (rl.contains(risk)) {
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
            risk.setAuthor(null);
            this.update(risk);
            em = getEntityManager();
            em.getTransaction().begin();
            em.remove(em.merge(risk));
            em.getTransaction().commit();
        } catch (Exception ex) {
            Logger.getLogger(RiskDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }
    
    /**
     * This method associates a single Risk object to n Comment objects.
     *
     * @param riskID the Risk object that will be associated to the Comments objects
     * @param commentIDs List of commentIDs to associate with riskID parameter.
     * @throws java.lang.Exception
     */
    public void associateCommentsToRisk(int riskID, List<Integer> commentIDs) throws Exception {
        try {
            if (commentIDs == null) {
                throw new IOException("Invalid commentIDs field");
            }
            Risk r = RiskDAO.getInstance().searchById(riskID);
            if (r == null) {
                throw new IOException("Invalid riskID field");
            }
            List<Comment> commentList = r.getCommentList();
            if (commentList == null) {
                throw new IOException("Empty commentList exception");
            }
            if (commentIDs.isEmpty()) {
                throw new IOException("Empty commentIDs field exception");
            }
            Comment com;
            for (int i = 0; i < commentIDs.size(); i++) {
                com = CommentDAO.getInstance().searchById(commentIDs.get(i));
                if (!commentList.contains(com)) {
                    commentList.add(com);
                } else {
                    throw new IOException("This plan already contains this comment");
                }
            }
            r.setCommentList(commentList);
            RiskDAO.getInstance().update(r);
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
     * @return a Risk object that matches with
     * @param id
     */
    public Risk searchById(int id) {
        try {
            em = getEntityManager();
            return (Risk) em.find(Risk.class, id);
        } catch (Exception ex) {
            Logger.getLogger(RiskDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }

    public Risk searchByIdHM(String id) throws Exception {
        try {
            return listAllHMBbyID().get(id);
        } catch (Exception ex) {
            Logger.getLogger(RiskDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
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
        } catch (Exception ex) {
            Logger.getLogger(RiskDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }
    
    /**
     *
     * @return a list of comments, including all comments, except for the ones in the
     * Risk identified by
     * @param riskID
     * @throws java.lang.Exception
     */
    public List<Comment> getCommentListByPlanNoRep(String riskID) throws Exception {
        try {
            Risk r = RiskDAO.getInstance().searchByIdString(riskID);
            List<Comment> rCommentList = r.getCommentList(); //comments of an specific Plan.
            List<Comment> commentList = CommentDAO.getInstance().listByColumn("PK_ID", "DESC");
            if (rCommentList == null || commentList == null) {
                throw new IOException("Empty commentList exception");
            }
            for (int i = 0; i < rCommentList.size(); i++) {
                if (commentList.contains(rCommentList.get(i))) {
                    commentList.remove(rCommentList.get(i));
                }
            }
            return commentList;
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
     * @return a Risk object that matches with
     * @param id
     */
    public Risk searchByIdString(String id) {
        try {
            HashMap<String, Risk> riesgos = this.listAllHM();
            return riesgos.get(id);
        } catch (Exception e) {

        }
        return null;
    }
    
    /**
     * @return a casted HashMap of Risk objects from an HQL query sorted in
     * descending order by entryDate.
     * @throws java.lang.Exception
     */
    public HashMap<String, Risk> listAllHM() throws Exception {
        HashMap<String, Risk> risks = new HashMap<>();
        List<Risk> riskList = this.listByColumn("ENTRYDATE", "DESC");
        riskList.forEach(p -> {
            risks.put(p.getId(), p);
        });
        return risks;
    }

    /*
    public Risk getRiskByID(Integer id) {
        try{
            em = getEntityManager();
            Session session = em.unwrap(Session.class);
            Query query = session.createSQLQuery("CALL getRiskByID(:pID)").addEntity(Risk.class);
            query.setParameter("pID", id);
            return (Risk) query.getSingleResult();
        }catch(javax.persistence.NoResultException ex){
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
            return null; //Risk not found
        }
        catch(Exception e)  {  
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;       
        } finally {
            closeEntityManager();
        }
    }

     */
    /**
     *
     *
     * @return a List<Risk> object which contains matched attribute toString()
     * values with
     * @param value
     */
    public List<Risk> searchInAllColumns(String value) throws Exception {
        try {
            HashMap<String, Risk> resultHM = this.listAllHMByPK();
            Pattern p = Pattern.compile(value, Pattern.CASE_INSENSITIVE);
            ArrayList<Risk> result = new ArrayList<>();
            for (HashMap.Entry<String, Risk> risk : resultHM.entrySet()) {
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
        } catch (Exception ex) {
            Logger.getLogger(RiskDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }
    
    

}
