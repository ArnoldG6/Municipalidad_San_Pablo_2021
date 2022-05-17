/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author jegon
 */


public class Holidays implements Serializable{
    

    private int idHolidays;
    private int day;
    private int month;
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
