/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common.dao;

import common.dao.generic.GenericDAO;
import common.model.Rol;
import javax.management.relation.Role;

/**
 *
 * @author jorsu
 */
public class RolDAO extends GenericDAO {

    private static RolDAO uniqueInstance;

    public static RolDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new RolDAO();
        }
        return uniqueInstance;
    }

    public Rol searchById(int id) {
        em = getEntityManager();
        return (Rol) em.find(Rol.class, id);
    }
}
