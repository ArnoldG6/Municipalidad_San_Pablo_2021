/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common.dao;

import common.dao.generic.GenericDAO;
import common.model.Department;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author jorsu
 */
public class DepartmentDAO extends GenericDAO {

    private static DepartmentDAO uniqueInstance;

    public static DepartmentDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new DepartmentDAO();
        }
        return uniqueInstance;
    }

    public List<Department> listAll() {
        try {
            em = getEntityManager();
            Query query = em.createQuery("SELECT d from common.model.Department d");
            return (List<Department>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.err.println(e.getMessage());
            throw e;
        } finally {
            closeEntityManager();
        }
    }

    public Department searchById(int id) {
        em = getEntityManager();
        return (Department) em.find(Department.class, id);
    }

}
