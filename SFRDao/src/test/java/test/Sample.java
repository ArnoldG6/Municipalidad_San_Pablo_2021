/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import sfr.dao.PlanDAO;
/**
 *
 * @author arnol
 */
public class Sample {
    @Test
    public void main(){
        System.out.println(PlanDAO.getInstance().listAll().toString());
    }
}
