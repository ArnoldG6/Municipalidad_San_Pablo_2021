package sfr.model;
import com.google.gson.Gson;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    private Integer id;
    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="parent")
    private PlanType parent;
    @OneToMany(mappedBy="parent")
    private List<PlanType> sons = new ArrayList<PlanType>();
    @Column(name = "Name")
    private String name;
    @Column(name = "ID_Name")
    private String idName;
    public PlanType() {
        //super();
    }
    public PlanType(Integer id){
        this.id = id;
    }
    public PlanType(Integer id,String name, String idName){
        this.id=id;
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
    public List<PlanType> getSons(){
        return sons;    
    }

    public void setSons(List<PlanType> sons){
        this.sons=sons;
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{id: ").append(this.id).append(",");
        sb.append("parent: ");
        if(parent == null) sb.append("null, ");
        else sb.append(this.parent.getId()).append(",");
        sb.append("name: ").append(this.name).append(",");
        sb.append("sons: [");
        for(int i = 0; i<this.sons.size(); i++){
            sb.append(sons.get(i).id);
        if (i != this.sons.size() -1)
            sb.append(",");
        }
        sb.append("]");
        sb.append("idName: ").append(this.idName).append("}");
        return sb.toString();
    }
}
