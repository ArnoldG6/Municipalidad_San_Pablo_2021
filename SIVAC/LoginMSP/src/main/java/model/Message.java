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

public class Message implements Serializable{
    private int ID;
    private String cedula;
    private String message;
    private String status;

    public Message(int ID, String cedula, String message, String status) {
        this.ID = ID;
        this.cedula = cedula;
        this.message = message;
        this.status = status;
    }

    public Message() {
        this.ID = 0;
        this.cedula = "";
        this.message = "";
        this.status = "";
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    @Override
    public String toString() {
        return "Message{" + "ID=" + ID + ", cedula=" + cedula + ", message=" + message + ", status=" + status + '}';
    }
}
