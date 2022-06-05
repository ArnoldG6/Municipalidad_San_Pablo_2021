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
 * @author jegon
 */

public class VacationDays implements Serializable {

    private int id_day;
    private Date days;
    private String status;
    private AdminFile file;

    public VacationDays(int id_day, Date days, String status, AdminFile file) {
        this.id_day = id_day;
        this.days = days;
        this.status = status;
        this.file = file;
    }

    public VacationDays() {
        this.days = new Date();
        this.file = new AdminFile();
        this.id_day = 0;
        this.status = "";
    }

    public int getId_day() {
        return id_day;
    }

    public void setId_day(int id_day) {
        this.id_day = id_day;
    }

    public Date getDays() {
        return days;
    }

    public void setDays(Date days) {
        this.days = days;
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
        return "VacationDays{" + "id_day=" + id_day + ", days=" + days
                + ", status=" + status + ", file=" + file + '}';
    }
}
