/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import sfr.dao.IncidenceDAO;
import sfr.dao.RiskDAO;
import sfr.model.Incidence;
import sfr.model.Risk;

/**
 *
 * @author Pc
 */
public class Tests {

    @Test
    public void main() {
        try {
            Risk r = RiskDAO.getInstance().searchById(1);
            Incidence i = new Incidence("name", "description", new Date(), 5, "cause", r);
            System.out.println(i.toString());
            IncidenceDAO.getInstance().add(i);
        } catch (Exception ex) {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
