package common.model;

import com.google.gson.Gson;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SI_ROLES")
public class Rol implements Serializable {

    public Rol(Integer idRol, String description) {
        this.idRol = idRol;
        this.description = description;
    }

    public Rol(Integer idRol) {
        this.idRol = idRol;
    }

    public Rol() {

    }

    /**
     * @return the idRol
     */
    public Integer getIdRol() {
        return idRol;
    }

    /**
     * @param idRol the idRol to set
     */
    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
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
    @Column(name = "PK_ROL")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer idRol;
    @Column(name = "description")
    private String description;
    @Override
    public String toString(){
        return new Gson().toJson(this);
    }

}
