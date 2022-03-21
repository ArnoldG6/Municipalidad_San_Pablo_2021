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
import sfr.dao.*;
import sfr.model.Incidence;

/**
 *
 * @author Pc
 */
public class Tests {

    @Test
    public void main() {
        try {
//            System.out.println(PlanTypeDAO.getInstance().handleIDAmount("EDM01"));
            Incidence i = new Incidence("Choque","Hubo un choque",new Date(),80,"No lo se");
            IncidenceDAO.getInstance().add(i);
        } catch (Exception ex) {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
