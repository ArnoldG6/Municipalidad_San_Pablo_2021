/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import sfr.dao.PlanDAO;
import sfr.model.Plan;

/**
 *
 * @author Pc
 */
public class Tests {

    @Test
    public void main() {
        System.out.println(PlanDAO.getInstance().countPlans());
        try {
            System.out.println(PlanDAO.getInstance().listByColumn("pk_id", "ASC"));
            System.out.println(PlanDAO.getInstance().listByColumn("pk_id", "DESC"));
            System.out.println("ID: "+PlanDAO.getInstance().searchById("1111"));
            System.out.println("ID: "+PlanDAO.getInstance().searchById("wea"));
        } catch (Exception ex) {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
