package sfr.model;

import common.model.User;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author GONCAR4, Arnold
 */
@Entity
@Table(name = "T_SFR_Risk")
public class Risk implements Serializable {

    @Id
    @Column(name = "PK_ID")
    @GeneratedValue(generator = "increment", strategy = GenerationType.IDENTITY)
    private int pkID;
    @Column(name = "ID")
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
    private Float magnitude;
    @Column(name = "MitigationMeasures")
    private String mitigationMeasures;
    @JoinColumn(name = "Author")
    @ManyToOne
    private User author;

    /*
    @ManyToMany
    @JoinTable(
            name = "T_RISKPLAN",
            joinColumwns = @JoinColumn(name = "FK_PLAN"),
            inverseJoinColumns = @JoinColumn(name = "FK_RISK")
    )
    private List<Plan> plans;
     */
    
    public Risk() {

    }

    public Risk(String id) {
        this.id = id;
    }

    public Risk(String id, String name, String desc, String generalType, String areaType,
            String specificType, Float probability, Integer impact, User author) {
        this.id = id;
        this.name = name;
        this.factors = desc;
        this.generalType = generalType;
        this.specType = specificType;
        this.areaType = areaType;
        this.probability = probability;
        this.impact = impact;
        this.magnitude = (probability * impact);
        this.mitigationMeasures = "";
        this.author = author;
    }

    public int getPkId() {
        return pkID;
    }

    public String getId() {
        return id;
    }

    public void updateMagnitude() {
        this.setMagnitude((Float) (probability * impact));
    }

    public void setPkId(int pkID) {
        this.pkID = pkID;
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

    public Float getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Float magnitude) {
        this.magnitude = magnitude;
    }

    public String getMitigationMeasures() {
        return mitigationMeasures;
    }

    public void setMitigationMeasures(String mitigationMeasures) {
        this.mitigationMeasures = mitigationMeasures;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        Risk r = (Risk) o;
        return this.pkID == r.getPkId();
    }

    @Override
    public String toString() {
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
        sb.append("\"magnitude\": ").append(magnitude).append("\n");
        //sb.append("\"magnitude\": ").append(magnitude).append(", \n");
        //sb.append("\"mitigationMeasures\": \"").append(mitigationMeasures).append("\" \n");
        //sb.append("\"mitigationMeasures\": \"").append(mitigationMeasures).append("\", \n");
        //sb.append("\"plans: \"").append(plans).append("\n");
        sb.append("} \n");
        return sb.toString();
    }

}
