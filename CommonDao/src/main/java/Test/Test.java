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
        System.out.println(UserDAO.getInstance().userAuth("50", "contra1").getEmail());
        System.out.println(UserDAO.getInstance().userAuth("informatica@sanpablo.go.cr", "contra1").getEmail());
        System.out.println(UserDAO.getInstance().userAuth("informatica@sanpablo.go.cr", "contrA1"));
    }
}
