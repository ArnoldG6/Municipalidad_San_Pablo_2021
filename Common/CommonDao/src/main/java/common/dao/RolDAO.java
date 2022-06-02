/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common.dao;

import common.dao.generic.GenericDAO;
import common.model.Rol;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

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
    
        public List<Rol> listAll() {

        String cmd = "SELECT r FROM Rol r";

        EntityManager manager = emf.createEntityManager();

        Query query = manager.createQuery(cmd);
        List<Rol> roles = query.getResultList();

        manager.close();

        return roles;

    }
    
     public Rol searchById(Rol rol) {
        EntityManager manager = emf.createEntityManager();
        Rol rolSearched = (Rol) manager.find(Rol.class, rol.getIdRol());
        manager.close();
        return rolSearched;
    }
}
