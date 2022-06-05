/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;



/**
 *
 * @author jegon
 */

public class EarlyVacations  {

    
    private int early_ID;
    private Date start_Date;
    private Date final_Date;
    private AdminFile file;

    public EarlyVacations(int early_ID, Date start_Date, Date final_Date, AdminFile file) {
        this.early_ID = early_ID;
        this.start_Date = start_Date;
        this.final_Date = final_Date;
        this.file = file;
    }

    public EarlyVacations() {
        this.early_ID = 0;
        this.final_Date = new Date();
        this.final_Date = new Date();
        this.file = new AdminFile();
    }

    public int getEarly_ID() {
        return early_ID;
    }

    public void setEarly_ID(int early_ID) {
        this.early_ID = early_ID;
    }

    public Date getStart_Date() {
        return start_Date;
    }

    public void setStart_Date(Date start_Date) {
        this.start_Date = start_Date;
    }

    public Date getFinal_Date() {
        return final_Date;
    }

    public void setFinal_Date(Date final_Date) {
        this.final_Date = final_Date;
    }

    public AdminFile getFile() {
        return file;
    }

    public void setFile(AdminFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "EarlyVacations{" + "early_ID=" + early_ID + ", start_Date=" + start_Date
                + ", final_Date=" + final_Date + ", file=" + file + '}';
    }
}
