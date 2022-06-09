/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author YENDRI
 */
public class Vacations {
      
    private int vacations_ID;
    private Date start_Date;
    private Date final_Date;
    private String status;
    private AdminFile file;

    public Vacations() {
        this.final_Date = new Date();
        this.start_Date = new Date();
        this.vacations_ID = 0;
        this.status = "";
        this.file = new AdminFile();
    }

    public Vacations(int vacations_ID, Date start_Date, Date final_Date, String status) {
        this.vacations_ID = vacations_ID;
        this.start_Date = start_Date;
        this.final_Date = final_Date;
        this.status = status;
    }

    public int getVacations_ID() {
        return vacations_ID;
    }

    public void setVacations_ID(int vacations_ID) {
        this.vacations_ID = vacations_ID;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AdminFile getFile() {
        return file;
    }

    public void setFile(AdminFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Vacations{" + "vacations_ID=" + vacations_ID + ", start_Date=" + start_Date + ", final_Date=" + final_Date + ", status=" + status + '}';
    }
    
}
 

