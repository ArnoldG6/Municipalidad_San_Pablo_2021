/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import sfr.model.Incidence;
import sfr.model.Risk;
import java.io.IOException;
import static sfr.dao.GenericDAO.em;

/**
 *
 * @author tebya
 */
public class IncidenceDAO extends GenericDAO {
   
    private static IncidenceDAO uniqueInstance; //Singleton Pattern Object

    /**
     * @return the Singleton Pattern Object of IncidenceDAO class.
     */
    public static IncidenceDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new IncidenceDAO();
        }
        return uniqueInstance;

    }
    
     public String translateColumnName(String column, String order) throws IOException {
        order = order.toUpperCase();
        if (!(order.equals("ASC") || order.equals("DESC"))) {
            throw new IOException("Invalid order parameter");
        }
        switch (column.toUpperCase()) {
            case "PK_ID":
                return "pkID";         
            case "NAME":
                return "name";
            case "DESCRIPTION":
                return "description";
            case "DATE":
                return "entryDate";
            case "CAUSE": 
                return "cause";
            case "AFFECTATION":
                return "affectation";
            case "RISK_ID":
                return "risk_id";
            default:
                throw new IOException("Invalid column");
        }
    }
     
    /**
     *
     * @return a Plan object that matches with
     * @param id
     */
    public Incidence searchById(int id) {
        em = getEntityManager();
        return (Incidence) em.find(Incidence.class, id);
    } 
     
     /**
     * @return a sorted list of Incidence objects depending on the next parameters:
     * @param column specifies the sorting criteria from the selected column.
     * Default value for this project is: "ENTRYDATE".
     * @param order specifies the 'ascendent' or 'descendent' sorting criteria.
     * Default value for this project is: "DESC".
     * @throws java.lang.Exception
     */
    public List<Incidence> listByColumn(String column, String order) throws Exception {
        try {
            order = order.toUpperCase();
            column = this.translateColumnName(column, order);
            String cmd = new StringBuilder().append("SELECT p from t_incidence p order by p.")
                    .append(column).append(" ").append(order).toString();
            em = getEntityManager();
            Query query = em.createQuery(cmd);
            return (List<Incidence>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }

    /**
     * @return a casted HashMap of Incidence objects from an HQL query sorted in
     * descending order by entryDate.
     * @throws java.lang.Exception
     */
    public HashMap<String, Incidence> listAllHM() throws Exception {
        HashMap<String, Incidence> incidences = new HashMap<>();
        List<Incidence> insList = this.listByColumn("DATE", "DESCRIPTION");
        insList.forEach(p -> {
            incidences.put(Integer.toString(p.getPkID()), p);
        });
        return incidences;
    }
    
    
     /**
     * This method adds a Plan object into the DB record, using HQL.
     *
     * @param incidence is the Incidence Object to be added.
     */
    public void add(Incidence incidence) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(incidence);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        } finally {
            closeEntityManager();
        }
    }
    
    /**
     * This method updates a Incidence object into the DB record, using HQL.
     *
     * @param incidence is the Incidence Object to be updated.
     */
    public void update(Incidence incidence) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(incidence);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        } finally {
            closeEntityManager();
        }
    }
    
    /**
     * This method deletes a Incidence object into the DB record, using HQL.
     *
     * @param incidence is the Incidence Object to be deleted.
     */
    public void delete(Incidence incidence) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.remove(em.merge(incidence));
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
        } finally {
            closeEntityManager();
        }
    }
    
    public List<Incidence> searchInAllColumns(String value) throws Exception {
        try {
            HashMap<String, Incidence> resultHM = this.listAllHM();
            SimpleDateFormat dateFor = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            Pattern p = Pattern.compile(new StringBuilder().append("(.*)").append(value).append("(.*)").toString(),
                    Pattern.CASE_INSENSITIVE);
            ArrayList<Incidence> result = new ArrayList<>();
            for (HashMap.Entry<String, Incidence> incidence : resultHM.entrySet()) {
                Incidence pl = incidence.getValue();
                //
                if (p.matcher(pl.getName()).find()
                        || p.matcher(pl.getDescription()).find()
                        || p.matcher(dateFor.format(pl.getEntryDate())).find()
                        || p.matcher(pl.getAffectation().toString()).find()
                        || p.matcher(pl.getCause()).find()) {
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
     * This method associates a single Incidence object to a Risk object.
     *
     * @param incidenceID the Plan object that will be associated to the Risk objects
     * @param riskID risk id to associate with incidenceID parameter.
     * @throws java.lang.Exception
     */
    public void associateRisksToIncidence(int incidenceID, Integer riskID) throws Exception {
        try {
            if (riskID == null) {
                throw new IOException("Invalid RiskID field");
            }
            Incidence ins = IncidenceDAO.getInstance().searchById(incidenceID);
            if (ins == null) {
                throw new IOException("Invalid incidenceID field");
            }
            Risk risk = ins.getRisk();
            Risk r = RiskDAO.getInstance().searchById(riskID);
            if(risk != r){
                risk = r;
            } else {
                throw new IOException("This incidence already contains this risk");
            }
            ins.setRisk(risk);
            IncidenceDAO.getInstance().update(ins);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }
    
   
}
