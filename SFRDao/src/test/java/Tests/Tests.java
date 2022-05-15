/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import common.dao.DepartmentDAO;
import common.dao.EmailFactory;
import common.dao.OfficialDAO;
import common.dao.RolDAO;
import common.dao.UserDAO;
import common.model.Department;
import common.model.Official;
import common.model.Rol;
import common.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

/**
 *
 * @author Pc
 */
public class Tests {
    
    @Test
    public void main() throws Exception {
        try {
            EmailFactory.getInstance().sendAddUser(UserDAO.getInstance().searchByEmail("ld.ramirezch14@gmail.com"));
            /*
            Rol rol = RolDAO.getInstance().searchById(1);
            List<Rol> roles = new ArrayList();
            roles.add(rol);
            Department department = DepartmentDAO.getInstance().searchById(102);
            Official official = OfficialDAO.getInstance().searchById(123);
            System.out.print(official);
            official.setDepartment(department);
            official.setName("no soy diego");
            OfficialDAO.getInstance().update(official);
            System.out.print(UserDAO.getInstance().searchById(123));
*/
        } catch (Exception ex) {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
