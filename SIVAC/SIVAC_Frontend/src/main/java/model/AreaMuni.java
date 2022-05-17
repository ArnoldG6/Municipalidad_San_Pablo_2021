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
public class AreaMuni implements Serializable{
    private int id_area;
    private String description;

    public AreaMuni(int id_area, String description) {
        this.id_area = id_area;
        this.description = description;
    }

    public AreaMuni() {
        this.description = "";
        this.id_area = 0;
    }

    public int getId_area() {
        return id_area;
    }

    public void setId_area(int id_area) {
        this.id_area = id_area;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "AreaMuni{" + "id_area=" + id_area + ", description=" + description + '}';
    }
}
