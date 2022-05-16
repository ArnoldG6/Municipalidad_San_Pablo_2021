/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common.dao;

import common.dao.generic.GenericDAO;
import common.model.UserRoles;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author jorsu
 */
public class UserRolesDAO extends GenericDAO {

    private static UserRolesDAO uniqueInstance;

    public static UserRolesDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new UserRolesDAO();
        }
        return uniqueInstance;
    }

    public void add(UserRoles usurol) {
        try {
            StoredProcedureQuery proc = getEntityManager().createStoredProcedureQuery("insertUser_roles");
            proc.registerStoredProcedureParameter("P_IN_PK_USER_ROL", Integer.class, ParameterMode.IN);
            proc.registerStoredProcedureParameter("P_IN_FK_USER", Integer.class, ParameterMode.IN);
            proc.registerStoredProcedureParameter("P_IN_FK_ROL", String.class, ParameterMode.IN);
            proc.setParameter("P_IN_PK_USER_ROL", usurol.getId());
            proc.setParameter("P_IN_FK_USER", usurol.getUser().getIdUser());
            proc.setParameter("P_IN_FK_ROL", usurol.getRol().getIdRol());
            proc.execute();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }

    public void update(UserRoles usurol) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(usurol);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.err.println(ex.getMessage());
            throw ex;
        } finally {
            closeEntityManager();
        }
    }
}
