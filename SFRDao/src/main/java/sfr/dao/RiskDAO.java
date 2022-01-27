/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

/**
 *
 * @author Arnold y Migue
 */
public class RiskDAO extends GenericDAO {

    private static RiskDAO uniqueInstance;

    public static RiskDAO getInstance() {
        if (uniqueInstance == null)
            uniqueInstance = new RiskDAO();
        return uniqueInstance;
    }

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

    public HashMap<Integer, Risk> listAllHM() throws Exception {
        HashMap<Integer, Risk> Risks = new HashMap<>();
        List<Risk> RisksList = this.listByColumn("PK_ID","DESC");
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

    public Risk searchByIdSmall(int id) {
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
