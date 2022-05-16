/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author jorsu
 */
@Entity
@Table(name = "SI_USER_ROLES")
public class UserRoles implements Serializable {

    @Id
    @Column(name = "PK_USER_ROL")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;
    @Column(name = "FK_user")
    private User user;
    @Column(name = "FK_rol")
    private Rol rol;

    public UserRoles() {
    }

    public UserRoles(Integer id, User user, Rol rol) {
        this.id = id;
        this.user = user;
        this.rol = rol;
    }

    public Integer getId() {
        return id;
    }

    public Rol getRol() {
        return rol;
    }

    public User getUser() {
        return user;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public void setUser(User user) {
        this.user = user;
    }




}
