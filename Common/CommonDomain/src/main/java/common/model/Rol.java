package common.model;

import com.google.gson.Gson;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SI_ROLES")
public class Rol implements Serializable {

    @Id
    @Column(name = "PK_ROL")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer idRol;
    @Column(name = "description")
    private String description;
    
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

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
    
        @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Rol r = (Rol) obj;
        if (this.idRol == r.getIdRol()) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.idRol);
        return hash;
    }

}
