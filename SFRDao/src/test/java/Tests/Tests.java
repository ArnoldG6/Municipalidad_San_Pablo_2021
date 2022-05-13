/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import common.dao.DepartmentDAO;
import common.dao.UserDAO;
import common.model.User;
import java.io.File;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import sfr.dao.PdfFactory;
import sfr.dao.PlanDAO;
import sfr.model.Plan;

/**
 *
 * @author Pc
 */
public class Tests {

    @Test
    public void main() throws Exception {
        try {
            UserDAO.getInstance().recordTransaction("usernameOrEmail", common.dao.generic.Transaction.USER_EDITION, Boolean.TRUE, null);
        } catch (Exception ex) {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
