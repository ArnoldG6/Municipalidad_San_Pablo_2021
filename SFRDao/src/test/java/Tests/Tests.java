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
//            System.out.printf("%s \n", "----------------------PLANES------------------------");
//            PlanDAO.getInstance().listAll().forEach(p -> {
//                System.out.printf("%s \n", p.toString());
//            });
//            System.out.printf("%s \n", "-----------------------RIESGOS----------------------");
//            RiskDAO.getInstance().listAll().forEach(r -> {
//                System.out.printf("%s \n", r.toString());
//            });
//            System.out.printf("Buscando al riesgo 2:  \n", RiskDAO.getInstance().searchByIdSmall("2"));
//            System.out.printf("Buscando al riesgo 777:  \n", RiskDAO.getInstance().searchByIdSmall("777"));
//            System.out.printf("Buscando al riesgo 2:  \n", RiskDAO.getInstance().searchById("2"));
//            System.out.printf("Buscando al riesgo 777:  \n", RiskDAO.getInstance().searchById("777"));
//            System.out.printf("Buscando al PLAN 2020LA-00002-01:  \n", RiskDAO.getInstance().searchById("2020LA-00002-01"));
//            System.out.printf("Buscando al PLAN X42:  \n", RiskDAO.getInstance().searchById("42"));
//            System.out.printf("%s \n", "---------------------------------------------------");
//            System.out.printf("Listando planes por columna: %s\n", PlanDAO.getInstance().listByColumn("PK_ID", "ASC"));
//            System.out.printf("Listando planes por columna:  %s\n", PlanDAO.getInstance().listByColumn("PK_ID", "DESC"));
            System.out.printf("Listando riesgos por columna: %s\n", RiskDAO.getInstance().listByColumn("PK_ID", "ASC"));
            System.out.printf("Listando riesgos por columna:  %s\n", RiskDAO.getInstance().listByColumn("PK_ID", "DESC"));

        } catch (Exception ex) {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
