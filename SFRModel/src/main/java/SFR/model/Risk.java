package sfr.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
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
    
    @Column(name = "Type")
    private String type;
    
    @Column(name = "Probability")
    private float probability;
    
    @Column(name = "Impact")
    private int impact;
    
    @Column(name = "AffectationLevel")
    private int affectationLevel;
    
    @Column(name = "MitigationMeasures")
    private String mitigationMeasures;
    
//    @OneToMany
//    @JoinTable(
//            name = "T_RISKPLAN",
//            joinColumns = @JoinColumn(name = "FK_PLANRISK"),
//            inverseJoinColumns = @JoinColumn(name = "FK_RISK")
//    )
    
    public Risk() {

    }

    public Risk(String id) {
        this.id = id;
    }
    
    public Risk(String name, String desc, String type, float probability, int impact, int affectationLevel, String mitigationMeasures) {
        this.name = name;
        this.description = desc;
        this.type = type;
        this.probability = probability;
        this.impact = impact;
        this.affectationLevel = affectationLevel;
        this.mitigationMeasures = mitigationMeasures;
    }
    
}
