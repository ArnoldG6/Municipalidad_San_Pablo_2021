package sfr.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Luis D
 */
@Entity(name = "PlanType")
@Table(name = "SFR.T_Plan_Types")
public class PlanType implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "PK_ID")
    @Expose
    private Integer id;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "parent")
    private PlanType parent;

    @OneToMany(mappedBy = "parent")
    private List<PlanType> children = new ArrayList<PlanType>();

    @Column(name = "Name")
    @Expose
    private String name;

    @Column(name = "ID_Name")
    @Expose
    private String idName;

    public PlanType() {
        //super();
    }

    public PlanType(Integer id) {
        this.id = id;
    }

    public PlanType(Integer id, String name, String idName) {
        this.id = id;
        this.name = name;
        this.idName = idName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PlanType getParent() {
        return parent;
    }

    public void setParent(PlanType parent) {
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

    public List<PlanType> getSons() {
        return children;
    }

    public void setSons(List<PlanType> sons) {
        this.children = sons;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.id).append(":{\n\t\"id\": ").append(this.id).append(",\n");
        sb.append("\t\"parent\": ");
        if (parent == null) {
            sb.append("null,\n");
        } else {
            sb.append(this.parent.getId()).append(",\n");
        }
        sb.append("\t\"name\": \"").append(this.name).append("\",\n");
        sb.append("\t\"children\": [");
        for (int i = 0; i < this.children.size(); i++) {
            sb.append(children.get(i).id);
            if (i != this.children.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        if (this.idName == null) {
            sb.append("\n\t\"idName\": ").append(this.idName).append("\n}\n");
        } else {
            sb.append("\n\t\"idName\": \"").append(this.idName).append("\"\n}\n");
        }
        return sb.toString();
    }
}
