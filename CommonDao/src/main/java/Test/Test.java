/*
 * To change this license header, choose License Headers in Project Properties.compare varchar vs compare text in sql
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import common.dao.UserDAO;

/**
 *
 * @author dicar
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(UserDAO.getInstance().
userAuth("50", "efcc06418ae6b9db034ad644e50c37391f13bc51ece24c84596bcbb677c80adb").toString());
    }
}
