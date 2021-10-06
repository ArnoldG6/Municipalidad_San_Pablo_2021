package sfr.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Query;
import sfr.model.Plan;
import org.hibernate.Session;

/**
 *
 * @author arnold
 */
public class PlanDAO extends GenericDAO {

    private static PlanDAO uniqueInstance;

    public static PlanDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new PlanDAO();
        }
        return uniqueInstance;
    }

    public List<Plan> listAll() {
        try {
            String cmd = "SELECT p.id, p.name, p.description, p.entryDate, p.status, p.authorName FROM Plan p";
            em = getEntityManager();
            Query query = em.createQuery(cmd);
            List<Plan> objList = (List<Plan>) query.getResultList();

            List<Plan> l = new ArrayList<>();
            Iterator itr = objList.iterator();

            //Hibernate consigue lista de Object
            //Realizar conversion al objeto correcto
            //Posiciones estan basadas en las posiciones de la tabla en la base de datos
            while (itr.hasNext()) {
                Object[] obj = (Object[]) itr.next();

                Plan p = new Plan((Integer) (obj[0]));
                p.setAuthorName(String.valueOf(obj[5]));
                p.setName(String.valueOf(obj[1]));
                p.setDesc(String.valueOf(obj[2]));
                p.setEntryDate((Date) (obj[3]));
                p.setStatus(String.valueOf(obj[4]));

                l.add(p);
            }

            return l;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }

    public HashMap<Integer, Plan> listAllHM() {
        HashMap<Integer, Plan> plans = new HashMap<>();
        List<Plan> plansList = this.listAll();
        plansList.forEach(p -> {
            plans.put(p.getId(), p);
        });
        return plans;
    }

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

    public Plan searchById(int id) {
        em = getEntityManager();
        return (Plan) em.find(Plan.class, id);
    }

    public List<Plan> listSearchBy(String toSearch, String value) {
        try {
            String cmd = "SELECT p.id, p.name, p.description, p.entryDate, p.status, p.authorName FROM Plan p WHERE p." + toSearch + " LIKE '" + value + "%'";
            em = getEntityManager();
            Query query = em.createQuery(cmd);
            List<Plan> objList = (List<Plan>) query.getResultList();

            List<Plan> l = new ArrayList<>();
            Iterator itr = objList.iterator();

            //Hibernate consigue lista de Object
            //Realizar conversion al objeto correcto
            //Posiciones estan basadas en las posiciones de la tabla en la base de datos
            while (itr.hasNext()) {
                Object[] obj = (Object[]) itr.next();

                Plan p = new Plan((Integer) (obj[0]));
                p.setAuthorName(String.valueOf(obj[5]));
                p.setName(String.valueOf(obj[1]));
                p.setDesc(String.valueOf(obj[2]));
                p.setEntryDate((Date) (obj[3]));
                p.setStatus(String.valueOf(obj[4]));

                l.add(p);
            }

            return l;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }

    public List<Plan> listTenPlans(int page) {
        try {
            String cmd = "CALL selectTenPlans("+ page +"0)";
            
            em = getEntityManager();
            Session session = em.unwrap(Session.class);
            
            Query query = session.createSQLQuery(cmd);
            List<Plan> objList = (List<Plan>) query.getResultList();

            List<Plan> l = new ArrayList<>();
            Iterator itr = objList.iterator();

            //Hibernate consigue lista de Object
            //Realizar conversion al objeto correcto
            //Posiciones estan basadas en las posiciones de la tabla en la base de datos
            while (itr.hasNext()) {
                Object[] obj = (Object[]) itr.next();

                Plan p = new Plan((Integer) (obj[0]));
                p.setAuthorName(String.valueOf(obj[5]));
                p.setName(String.valueOf(obj[1]));
                p.setDesc(String.valueOf(obj[2]));
                p.setEntryDate((Date) (obj[3]));
                p.setStatus(String.valueOf(obj[4]));

                l.add(p);
            }

            return l;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }

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
