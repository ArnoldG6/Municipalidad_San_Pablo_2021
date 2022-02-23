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
@Entity(name = "RiskType")
@Table(name = "SFR.T_Risk_Types")
public class RiskType implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "PK_ID")
    @Expose
    private Integer id;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "parent")
    private RiskType parent;

    @OneToMany(mappedBy = "parent")
    private List<RiskType> children = new ArrayList<RiskType>();

    @Column(name = "Name")
    @Expose
    private String name;

    @Column(name = "ID_Name")
    @Expose
    private String idName;

    public RiskType() {
        //super();
    }

    public RiskType(Integer id) {
        this.id = id;
    }

    public RiskType(Integer id, String name, String idName) {
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

    public RiskType getParent() {
        return parent;
    }

    public void setParent(RiskType parent) {
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

    public List<RiskType> getSons() {
        return children;
    }

    public void setSons(List<RiskType> sons) {
        this.children = sons;
    }
}
