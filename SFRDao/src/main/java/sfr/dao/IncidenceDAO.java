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
                return "date";
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
     * @return a casted HashMap of Plan objects from an HQL query sorted in
     * descending order by entryDate.
     * @throws java.lang.Exception
     */
    public HashMap<String, Incidence> listAllHM() throws Exception {
        HashMap<String, Incidence> plans = new HashMap<>();
        List<Incidence> plansList = this.listByColumn("DATE", "DESCRIPTION");
        plansList.forEach(p -> {
            plans.put(Integer.toString(p.getPkID()), p);
        });
        return plans;
    }
    
    
     /**
     * This method adds a Plan object into the DB record, using HQL.
     *
     * @param plan is the Plan Object to be added.
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
    
    
    
    
    
    
    
    
    
}
