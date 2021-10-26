/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import common.dao.UserDAO;
import common.model.User;

/**
 *
 * @author dicar
 */
public class Test {

    public static void main(String[] args) {
        UserDAO.getInstance().listAll().forEach(u -> {
            System.out.printf("%s \n", u.toString());
        });
    }

}
