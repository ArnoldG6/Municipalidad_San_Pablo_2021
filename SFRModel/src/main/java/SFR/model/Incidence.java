/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sfr.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
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
    private Date date;
    
    @Column(name = "Affectation")
    private Integer affectation;
    
    @Column(name = "Cause")
    private String cause;
    
    //Se cambió a many to one para aplicar la FK 
    @ManyToOne
    @JoinColumn(name = "Risk_ID", referencedColumnName = "ID")
    private Risk risk;

    public Incidence(String name, String description, Date entryDate, Integer affectation, String cause, Risk risk) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.affectation = affectation;
        this.cause = cause;
        this.risk = risk;
    }
    
    public Incidence(String name, String description, Date entryDate, Integer affectation, String cause) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.affectation = affectation;
        this.cause = cause;
        this.risk = new Risk();
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
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getAffectation() {
        return affectation;
    }

    public void setAffectation(Integer affectation) {
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
        sb.append("\"Date\": \"").append(date).append("\",\n");
        sb.append("\"affectation\": \"").append(affectation).append("\",\n");
        sb.append("\"cause\":").append(cause).append("\",\n");
        sb.append("\"risk\":").append(risk).append("\n");
        sb.append("}\n");
        return sb.toString();
    }         
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Incidence other = (Incidence) obj;
        if (this.pkID != other.pkID) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.affectation, other.affectation)) {
            return false;
        }
        if (!Objects.equals(this.cause, other.cause)) {
            return false;
        }
        if (!Objects.equals(this.risk, other.risk)) {
            return false;
        }
        return true;
    }
}