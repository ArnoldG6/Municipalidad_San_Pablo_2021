/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author jegon
 */
@Entity(name = "role")
@Table(name = "T_Role")
public class Role implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_Role")
    private int id_role;

    public Role() {
        this.id_role = 0;
    }

    public int getId_role() {
        return id_role;
    }

    public void setId_role(int id_role) {
        this.id_role = id_role;
    }
}
