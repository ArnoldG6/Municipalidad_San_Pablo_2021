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
public class Tests {

    public void main() {
        System.out.println(UserDAO.getInstance().listAll());
    }
}
