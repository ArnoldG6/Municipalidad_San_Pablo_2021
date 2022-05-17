/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.model;

import java.io.Serializable;
import javax.persistence.*;
/*import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;*/

/**
 *
 * @author jegon
 */

@Entity(name = "holiday")
@Table(name = "T_Holidays")
public class Holidays implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Holidays")
    private int idHolidays;
    
    @Column(name = "day")
    private int day;
    
    @Column(name = "month")
    private int month;
    
    @Column(name = "name")
    private String name;

    public Holidays(int idHolidays, int day, int month, String name) {
        this.idHolidays = idHolidays;
        this.day = day;
        this.month = month;
        this.name = name;
    }

    public Holidays() {
        this.day = 0;
        this.idHolidays = 0;
        this.month = 0;
        this.name = "";
    }

    public int getIdHolidays() {
        return idHolidays;
    }

    public void setIdHolidays(int idHolidays) {
        this.idHolidays = idHolidays;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Holidays{" + "idHolidays=" + idHolidays + ", day=" + day + ", month=" + month + 
                ", name=" + name + '}';
    }
    
}
