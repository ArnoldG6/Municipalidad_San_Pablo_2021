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

public class Notifications implements Serializable{
    
    
    private int id_Notification;
    private int id_Transmitter;
    private int idReceiver;
    private boolean isRead;
    private String description;

    public Notifications(int id_Notification, int id_Transmitter, int idReceiver, boolean isRead, String description) {
        this.id_Notification = id_Notification;
        this.id_Transmitter = id_Transmitter;
        this.idReceiver = idReceiver;
        this.isRead = isRead;
        this.description = description;
    }

    public Notifications() {
        this.id_Notification = 0;
        this.description = "";
        this.isRead = false;
        this.id_Transmitter = 0;
        this.idReceiver = 0;
    }

    public int getId_Notification() {
        return id_Notification;
    }

    public void setId_Notification(int id_Notification) {
        this.id_Notification = id_Notification;
    }

    public int getId_Transmitter() {
        return id_Transmitter;
    }

    public void setId_Transmitter(int id_Transmitter) {
        this.id_Transmitter = id_Transmitter;
    }

    public int getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(int idReceiver) {
        this.idReceiver = idReceiver;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Notifications{" + "id_Notification=" + id_Notification + ", id_Transmitter=" + id_Transmitter + ", idReceiver=" + idReceiver + ", isRead=" + isRead + ", description=" + description + '}';
    }
}
