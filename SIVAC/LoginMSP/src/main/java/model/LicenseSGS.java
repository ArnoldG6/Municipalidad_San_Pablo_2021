/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author YENDRI
 */
public class LicenseSGS implements Serializable{
    private int number_License;
    private String category;
    private Date start_Date;
    private Date final_Date;
    private String justification;
    private String status;
    private AdminFile file;

    public LicenseSGS() {
        this.category = "";
        this.final_Date = new Date();
        this.justification = "";
        this.number_License = 0;
        this.start_Date = new Date();
        this.status = "";
        this.file = new AdminFile();
    }

    public LicenseSGS(int number_License, String category, Date start_Date, Date final_Date, String justification, String status) {
        this.number_License = number_License;
        this.category = category;
        this.start_Date = start_Date;
        this.final_Date = final_Date;
        this.justification = justification;
        this.status = status;
    }

    public int getNumber_License() {
        return number_License;
    }

    public void setNumber_License(int number_License) {
        this.number_License = number_License;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
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
        return "License_CGS{" + "number_License=" + number_License + ", category=" + category + ", start_Date=" + start_Date + ", final_Date=" + final_Date + ", justification=" + justification + ", status=" + status + '}';
    }
}
