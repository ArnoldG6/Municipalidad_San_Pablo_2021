/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import common.dao.UserDAO;
import common.model.User;
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
            User user = UserDAO.getInstance().searchById(50);
            Plan plan = PlanDAO.getInstance().searchById(1);
            new PdfFactory().createRiskMatrix(null, "asd", user, plan);
        } catch (Exception ex) {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
