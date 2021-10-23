package sfr.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author GONCAR4
 */
@Entity
@Table(name = "T_Risk")
public class Risk implements Serializable {
    
    @Id
    @Column(name = "PK_ID")
    private String id;
    
    @Column(name = "Name")
    private String name;
    
    @Column(name = "Description")
    private String description;
    
    @Column(name = "GeneralType")
    private String generalType;
    
    @Column(name = "AreaType")
    private String areaType;
        
   @Column(name = "SpecType")
    private String specType;
    
    @Column(name = "Probability")
    private Float probability;
    
    @Column(name = "Impact")
    private Integer impact;
    
    @Column(name = "AffectationLevel")
    private Integer affectationLevel;
    
    @Column(name = "MitigationMeasures")
    private String mitigationMeasures;
    
    @ManyToMany
    @JoinTable(
            name = "T_RISKPLAN",
            joinColumns = @JoinColumn(name = "FK_PLAN"),
            inverseJoinColumns = @JoinColumn(name = "FK_RISK")
    )
    private List<Plan> plans;
    public Risk() {

    }

    public Risk(String id) {
        this.id = id;
    }
    
    public Risk(String name, String desc, String generalType, String areaType, 
            String specificType,Float probability, Integer impact, 
            Integer affectationLevel,  String mitigationMeasures) {
        this.name = name;
        this.description = desc;
        this.generalType = generalType;
        this.specType = specificType;
        this.areaType = areaType;
        this.probability = probability;
        this.impact = impact;
        this.affectationLevel = affectationLevel;
        this.mitigationMeasures = mitigationMeasures;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGeneralType() {
        return generalType;
    }

    public void setGeneralType(String generalType) {
        this.generalType = generalType;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public String getSpecType() {
        return specType;
    }

    public void setSpecType(String specType) {
        this.specType = specType;
    }

    public Float getProbability() {
        return probability;
    }

    public void setProbability(Float probability) {
        this.probability = probability;
    }

    public Integer getImpact() {
        return impact;
    }

    public void setImpact(Integer impact) {
        this.impact = impact;
    }

    public Integer getAffectationLevel() {
        return affectationLevel;
    }

    public void setAffectationLevel(Integer affectationLevel) {
        this.affectationLevel = affectationLevel;
    }

    public String getMitigationMeasures() {
        return mitigationMeasures;
    }

    public void setMitigationMeasures(String mitigationMeasures) {
        this.mitigationMeasures = mitigationMeasures;
    }

    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Risk : ");
        sb.append("{");
        sb.append("\"id\": \"").append(id).append("\", ");
        sb.append("\"name\": \"").append(name).append("\", ");
        sb.append("\"description\": \"").append(description).append("\", ");
        sb.append("\"generalType\": \"").append(generalType).append("\", ");
        sb.append("\"specType\": \"").append(specType).append("\", ");
        sb.append("\"areaType\": \"").append(areaType).append("\", ");
        sb.append("\"probability\": ").append(probability).append(", ");
        sb.append("\"impact\": ").append(impact).append(", ");
        sb.append("\"affectationLevel\": ").append(affectationLevel).append(", ");
        sb.append("\"mitigationMeasures\": \"").append(mitigationMeasures).append("\"");
        sb.append("}");
        return sb.toString();
    }
}
