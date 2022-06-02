package sigep.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SP_BudgetLog")
public class BudgetLog implements Serializable {

    public BudgetLog(BudgetLogId id, Budget bugetItem, double initialAmount, double finalAmount, double increased, double decreased) {
        this.id = id;
        this.bugetItem = bugetItem;
        this.initialAmount = initialAmount;
        this.finalAmount = finalAmount;
        this.increased = increased;
        this.decreased = decreased;
    }
    
    public BudgetLog() {
        
    }

    public BudgetLog(BudgetLogId id) {
        this.id = id;
    }

    public BudgetLogId getId() {
        return id;
    }

    public void setId(BudgetLogId id) {
        this.id = id;
    }

    public Budget getBugetItem() {
        return bugetItem;
    }

    public void setBugetItem(Budget bugetItem) {
        this.bugetItem = bugetItem;
    }

    public double getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(double initialAmount) {
        this.initialAmount = initialAmount;
    }

    public double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }

    public double getIncreased() {
        return increased;
    }

    public void setIncreased(double increased) {
        this.increased = increased;
    }

    public double getDecreased() {
        return decreased;
    }

    public void setDecreased(double decreased) {
        this.decreased = decreased;
    }

    @EmbeddedId
    private BudgetLogId id;

    @OneToOne
    @JoinColumn(name = "FK_budgetItem")
    private Budget bugetItem;
    
    private double initialAmount;
    private double finalAmount;
    private double increased;
    private double decreased;

}
