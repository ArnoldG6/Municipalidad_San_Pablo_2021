package common.model;

import com.google.gson.Gson;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SI_DEPARTMENTS")
public class Department implements Serializable {

    public Department(Integer idDepartment, String description) {
        this.idDepartment = idDepartment;
        this.description = description;
    }

    public Department(Integer idDepartment) {
        this.idDepartment = idDepartment;
    }

    public Department() {

    }

    /**
     * @return the idDepartment
     */
    public Integer getIdDepartment() {
        return idDepartment;
    }

    /**
     * @param idDepartment the idDepartment to set
     */
    public void setIdDepartment(Integer idDepartment) {
        this.idDepartment = idDepartment;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Id
    @Column(name = "PK_DEPARTMENT")
    private Integer idDepartment;
    @Column(name = "description")
    private String description;
    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
