/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfr.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author arnol
 */

@Entity
@Table(name = "T_Incidence")
public class Incidence implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ID")
    private int pkID;



    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "Date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date Date;

    
    @Column(name = "Affectation")
    private String affectation;
    
    @Column(name = "Cause")
    private String cause;
    
    
    //cambiar a one to one y nueva tabla 
    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(
            name = "T_RISKPLAN",
            joinColumns = @JoinColumn(name = "FK_PLAN"),
            inverseJoinColumns = @JoinColumn(name = "FK_RISK")
    )
    private Risk risk;

    public Incidence(int pkID, String name, String description, Date entryDate, String affectation, String cause, Risk risk) {
        this.pkID = pkID;

        this.name = name;
        this.description = description;
        this.Date = Date;
        this.affectation = affectation;
        this.cause = cause;
        this.risk = risk;
    }

    public Incidence() {
    }
    
    
    

    public int getPkID() {
        return pkID;
    }

    public void setPkID(int pkID) {
        this.pkID = pkID;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }

    public String getAffectation() {
        return affectation;
    }

    public void setAffectation(String affectation) {
        this.affectation = affectation;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Risk getRisk() {
        return risk;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
    }
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Incidence:").append("{\n");
        sb.append("\"name\": \"").append(name).append("\",");
        sb.append("\"description\": \"").append(description).append("\",\n");
        sb.append("\"Date\": \"").append(Date).append("\",\n");
        sb.append("\"affectation\": \"").append(affectation).append("\",\n");
        sb.append("\"cause\":").append(cause).append("\",\n");
        sb.append("\"risk\":").append(risk).append("\n");
        //sb.append("\"involvedList\": ").append(involvedList.toString());
        sb.append("}\n");
        return sb.toString();
    }
    
    
    
}
