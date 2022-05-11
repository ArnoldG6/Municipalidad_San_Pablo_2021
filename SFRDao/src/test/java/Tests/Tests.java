/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import common.dao.UserDAO;
import common.model.User;
import jakarta.mail.MessagingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import sfr.dao.EmailFactory;
import sfr.dao.PlanDAO;
import sfr.dao.Transaction;

/**
 *
 * @author Pc
 */
public class Tests {

    @Test
    public void main() throws Exception {
        try {
            PlanDAO.getInstance().recordTransaction(UserDAO.getInstance().searchById(50), Transaction.INSERT_PLAN, Boolean.TRUE, "a");
        } catch (Exception ex) {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
