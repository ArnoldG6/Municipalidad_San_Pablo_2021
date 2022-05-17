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
    private String message;

    public Message(int ID, String message) {
        this.ID = ID;
        this.message = message;
    }

    public Message() {
        this.ID = 0;
        this.message = "";
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

    @Override
    public String toString() {
        return "Message{" + "ID=" + ID + ", message=" + message + '}';
    }
}
