package common.model;

import com.google.gson.Gson;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SI_OFFICIALS")
public class Official implements Serializable {

    public Official(int idOfficial, String name, String surname, String email, Department department) {
        this.idOfficial = idOfficial;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.department = department;
    }

    public Official(int idOfficial) {
        this.idOfficial = idOfficial;
    }

    public Official() {

    }

    public int getIdOfficial() {
        return idOfficial;
    }

    public void setIdOfficial(int idOfficial) {
        this.idOfficial = idOfficial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    @Id
    @Column(name = "PK_OFFICIAL")
    private int idOfficial;
    
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "email")
    private String email;
    
    @OneToOne
    @JoinColumn(name = "FK_department")
    private Department department;
    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
