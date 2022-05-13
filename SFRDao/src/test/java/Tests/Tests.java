/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import common.dao.DepartmentDAO;
import common.dao.UserDAO;
import common.model.User;
import jakarta.mail.MessagingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import sfr.dao.EmailFactory;
import sfr.dao.PlanDAO;

/**
 *
 * @author Pc
 */
public class Tests {

    @Test
    public void main() throws Exception {
        try {
            System.out.print(DepartmentDAO.getInstance().listAll());
        } catch (Exception ex) {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
