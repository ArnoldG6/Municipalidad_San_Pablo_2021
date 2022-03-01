package sfr.dao;

import java.util.ArrayList;
import java.util.HashMap;
import sfr.model.PlanType;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;

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
     * @return a sorted list of PlanType objects depending on the next parameters:
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

}
