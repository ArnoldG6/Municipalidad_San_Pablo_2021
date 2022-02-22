package SFR.frontend;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Luis D
 */
@Entity
@Table(name = "T_Plan_Types")
public class PlanType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ID")
    private int id;

    @Column(name = "Parent")
    private int parent;

    @Column(name = "Name")
    private String name;

    @Column(name = "ID_Name")
    private String idName;

    public PlanType() {

    }

    public PlanType(int id, int parent, String name, String idName) {
        this.id = id;
        this.parent = parent;
        this.name = name;
        this.idName = idName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }
}
