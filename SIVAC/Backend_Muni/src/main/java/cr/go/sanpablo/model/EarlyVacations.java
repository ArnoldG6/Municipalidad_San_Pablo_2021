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
@Entity(name = "early")
@Table(name = "T_Early_Vacations")
public class EarlyVacations implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "early_id")
    private int early_ID;

    @Column(name = "start_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date start_Date;

    @Column(name = "final_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date final_Date;

    @ManyToOne
    @JoinColumn(name = "T_Administrative_File_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
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
