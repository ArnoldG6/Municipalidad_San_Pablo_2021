/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import sfr.dao.PlanTypeDAO;

/**
 *
 * @author Pc
 */
public class Tests {

    @Test
    public void main() {
        try {
            System.out.println(PlanTypeDAO.getInstance().listAll());
        } catch (Exception ex) {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
