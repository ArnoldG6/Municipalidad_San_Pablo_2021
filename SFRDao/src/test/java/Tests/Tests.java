/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import java.util.Date;
import java.util.List;
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
        List<Plan> l = PlanDAO.getInstance().listTenPlans(1);
        l.forEach(p -> System.out.println(p.toString()));
    }
}
