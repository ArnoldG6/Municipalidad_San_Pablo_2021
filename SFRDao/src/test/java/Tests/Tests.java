/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

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
            User user = UserDAO.getInstance().searchById(50);
            Plan plan = PlanDAO.getInstance().searchById(1);
            new PdfFactory().createPlanReport(new FileOutputStream(new File("D:/reporte.pdf")), user, plan, "src/main/resources/images/MSPH_LOGO.png");
            //new PdfFactory().createRiskMatrix(new FileOutputStream(new File("D:/my.pdf")), user, plan, "src/main/resources/images/MSPH_LOGO.png");
        } catch (Exception ex) {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
