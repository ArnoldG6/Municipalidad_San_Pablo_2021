package test;

import common.dao.UserDAO;
import jakarta.mail.MessagingException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Arnold
 */
public class TestM {
    public void main() throws MessagingException{
        UserDAO.getInstance().handlePasswordReset(UserDAO.getInstance().searchById(50), 5);
    }
}
