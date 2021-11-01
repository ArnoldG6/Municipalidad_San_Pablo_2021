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
 * @author GONCAR4, Arnold
 */
@Entity
@Table(name = "T_Risk")
public class Risk implements Serializable {
    @Id
    @Column(name = "PK_ID")
    private String id;
    @Column(name = "Name")
    private String name;
    @Column(name = "Factors")
    private String factors;
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
    @Column(name = "Magnitude")
    private Integer magnitude;
    @Column(name = "MitigationMeasures")
    private String mitigationMeasures;
    /*
    @ManyToMany
    @JoinTable(
            name = "T_RISKPLAN",
            joinColumns = @JoinColumn(name = "FK_PLAN"),
            inverseJoinColumns = @JoinColumn(name = "FK_RISK")
    )
    private List<Plan> plans;
    */
    public Risk() {

    }

    public Risk(String id) {
        this.id = id;
    }
    
    public Risk(String name, String desc, String generalType, String areaType, 
            String specificType,Float probability, Integer impact, 
            //Integer affectationLevel,  String mitigationMeasures, List<Plan> plans) {
            String mitigationMeasures) {
        this.name = name;
        this.factors = desc;
        this.generalType = generalType;
        this.specType = specificType;
        this.areaType = areaType;
        this.probability = probability;
        this.impact = impact;
        this.magnitude = (int)(probability*impact);
        this.mitigationMeasures = mitigationMeasures;
        //this.plans = plans;
        
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

    public String getFactors() {
        return factors;
    }

    public void setFactors(String factors) {
        this.factors = factors;
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

    public Integer getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Integer magnitude) {
        this.magnitude = magnitude;
    }

    public String getMitigationMeasures() {
        return mitigationMeasures;
    }

    public void setMitigationMeasures(String mitigationMeasures) {
        this.mitigationMeasures = mitigationMeasures;
    }

//    public List<Plan> getPlans() {
//        return plans;
//    }
//
//    public void setPlans(List<Plan> plans) {
//        this.plans = plans;
//    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Risk:");
        sb.append("{");
        sb.append("\"id\": \"").append(id).append("\", \n");
        sb.append("\"name\": \"").append(name).append("\", \n");
        sb.append("\"factors\": \"").append(factors).append("\", \n");
        sb.append("\"generalType\": \"").append(generalType).append("\", \n");
        sb.append("\"specType\": \"").append(specType).append("\", \n");
        sb.append("\"areaType\": \"").append(areaType).append("\", \n");
        sb.append("\"probability\": ").append(probability).append(", \n");
        sb.append("\"impact\": ").append(impact).append(", \n");
        sb.append("\"magnitude\": ").append(magnitude).append(", \n");
        sb.append("\"mitigationMeasures\": \"").append(mitigationMeasures).append("\" \n");
        //sb.append("\"mitigationMeasures\": \"").append(mitigationMeasures).append("\", \n");
        //sb.append("\"plans: \"").append(plans).append("\n");
        sb.append("} \n");
        return sb.toString();
    }
}
