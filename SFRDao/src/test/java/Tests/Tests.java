/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;
import common.dao.UserDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import sfr.dao.PlanDAO;
import sfr.dao.RiskDAO;

/**
 *
 * @author Pc
 */
public class Tests {

    @Test
    public void main() {
        try {
            System.out.println(PlanDAO.getInstance().searchById(1));
        } catch (Exception ex) {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
