/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfr.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
            String cmd = "SELECT p.id, p.name, p.description, p.generalType, p.areaType, p.specType, "
                    + "p.impact, p.probability, p.affectationLevel, p.mitigationMeasures FROM Risk p";
            em = getEntityManager();
            Query query = em.createQuery(cmd);
            List<Risk> objList = (List<Risk>) query.getResultList();
            List<Risk> l = new ArrayList<>();
            Iterator itr = objList.iterator();
            //Hibernate consigue lista de Object
            //Realizar conversion al objeto correcto
            //Posiciones estan basadas en las posiciones de la tabla en la base de datos
            while (itr.hasNext()) {
                Object[] obj = (Object[]) itr.next();
                Risk p = new Risk(String.valueOf(obj[0]));
                p.setName(String.valueOf(obj[1]));
                p.setDescription(String.valueOf(obj[2]));
                p.setGeneralType(String.valueOf(obj[3]));
                p.setAreaType(String.valueOf(obj[4]));
                p.setSpecType(String.valueOf(obj[5]));
                p.setImpact(Integer.parseInt(obj[6].toString()));
                p.setProbability(Float.parseFloat((obj[7].toString())));
                p.setAffectationLevel(Integer.parseInt(obj[8].toString()));
                p.setMitigationMeasures(String.valueOf(obj[9]));
                l.add(p);
            }
            return l;
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
            String cmd = "SELECT p.id, p.name, p.description, p.generalType, p.areaType, p.specType, "
                    + "p.impact, p.probability, p.affectationLevel, p.mitigationMeasures FROM Risk p "
                    + " WHERE p.id = '" + id + "'";
            em = getEntityManager();
            Query query = em.createQuery(cmd);
            Object[] obj = (Object[]) query.getSingleResult();

            Risk p = new Risk(String.valueOf(obj[0]));
            p.setName(String.valueOf(obj[1]));
            p.setDescription(String.valueOf(obj[2]));
            p.setGeneralType(String.valueOf(obj[3]));
            p.setAreaType(String.valueOf(obj[4]));
            p.setSpecType(String.valueOf(obj[5]));
            p.setImpact(Integer.parseInt(obj[6].toString()));
            p.setProbability(Float.parseFloat((obj[7].toString())));
            p.setAffectationLevel(Integer.parseInt(obj[8].toString()));
            p.setMitigationMeasures(String.valueOf(obj[9]));

            return p;
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
