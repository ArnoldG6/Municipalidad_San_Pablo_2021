/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author jegon
 */
@Entity(name = "vacationday")
@Table(name = "T_Vacation_Days")
public class VacationDays implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_day")
    private int id_day;

    @Column(name = "days")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date days;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_day_user", referencedColumnName = "id", nullable = false)
    @JsonBackReference
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
