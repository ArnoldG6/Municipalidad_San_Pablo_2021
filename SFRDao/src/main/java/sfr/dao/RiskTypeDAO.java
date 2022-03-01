package sfr.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import static sfr.dao.GenericDAO.em;
import sfr.model.RiskType;

/**
 *
 * @author Luis D
 */
public class RiskTypeDAO extends GenericDAO {

    private static RiskTypeDAO uniqueInstance; //Singleton Pattern Object

    /**
     * @return the Singleton Pattern Object of RiskTypeDAO class.
     */
    public static RiskTypeDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new RiskTypeDAO();
        }
        return uniqueInstance;
    }

        /**
     * @return a sorted list of RiskType objects depending on the next parameters:
     * @throws java.lang.Exception
     */
    public List<RiskType> listAll() throws Exception {
        try {
            em = getEntityManager();
            Query query = em.createQuery("SELECT p from sfr.model.RiskType p");
            return (List<RiskType>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        }
    }

    /**
     * @return a casted HashMap of RiskType objects from an HQL query
     * @throws java.lang.Exception
     */
    public Map<String, List<RiskType>> listAllPlanTypeHM() throws Exception {
        HashMap<String, List<RiskType>> data = new HashMap<>();
        List<RiskType> dataList = this.listAll();
        List<RiskType> parents = new ArrayList<>();
        //Parents
        dataList.forEach(d -> {
            if (d.getParent() == null) {
                parents.add(d);
            }
        });

        data.put("parents", parents);

        //Children
        parents.forEach(p -> {
            List<RiskType> children = new ArrayList<>();
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
