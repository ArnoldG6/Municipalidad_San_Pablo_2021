/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import sfr.dao.PlanDAO;
import sfr.dao.RiskDAO;
import sfr.model.Risk;

/**
 *
 * @author Pc
 */
public class Tests {

    @Test
    public void main() {
        try {
            //System.out.println(PlanDAO.getInstance().listAll());
            PlanDAO.getInstance().listAll().forEach(p -> {
                System.out.printf("%s \n",p.toString());
            });
            System.out.printf("%s \n", "---------------------------------------------------");
            
            System.out.printf("%s \n", "---------------------------------------------------");
            //System.out.println(RiskDAO.getInstance().listAll());
            RiskDAO.getInstance().listAll().forEach(p -> {
                System.out.printf("%s \n",p.toString());
            });
//            Risk p = new Risk("2");
//            p.setName("PRUEBA AGAIN");
//            p.setDescription("ESTO ES UNA DESCRIPCION DE PRUEBA");
//            p.setGeneralType("EXTERNO");
//            p.setAreaType("POLITICO");
//            p.setSpecType("CAMBIO DE GOBIERNO");
//            p.setProbability(0.9f);
//            p.setImpact(30);
//            p.setAffectationLevel(27);
//            p.setMitigationMeasures("ESTA ES UNA MEDIDA DE MITIGACION");
//            RiskDAO.getInstance().add(p);
        } catch (Exception ex) {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
