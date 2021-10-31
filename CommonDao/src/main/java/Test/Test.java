/*
 * To change this license header, choose License Headers in Project Properties.
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
        //UserDAO.getInstance().listAll().forEach(u -> {
        //    System.out.printf("%s \n", u.toString());
        //});
        System.out.println(UserDAO.getInstance().searchById(50));
        System.out.println(UserDAO.getInstance().searchById(51));
        System.out.println(UserDAO.getInstance().searchById(52));
        System.out.println(UserDAO.getInstance().searchById(53));
        System.out.println(UserDAO.getInstance().searchById(54));
    }

}
