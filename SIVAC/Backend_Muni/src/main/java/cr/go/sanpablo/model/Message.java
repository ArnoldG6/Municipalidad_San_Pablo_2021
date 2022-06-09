/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author jegon
 */

@Entity(name = "message")
@Table(name = "T_Message")
public class Message implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_message")
    private int ID;
    
    @Column(name = "id_user")
    private String cedula;
    
    @Column(name = "Message")
    private String message;
    
    @Column(name = "status")
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
