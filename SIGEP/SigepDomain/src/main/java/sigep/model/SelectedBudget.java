package sigep.model;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sp_selectedbudget")
public class SelectedBudget implements Serializable{

    public SelectedBudget(int id, BudgetModificationRequest budgetModificationRequest, Budget budgetA, Budget budgetB,double amount){
        this.id = id;
        this.budgetModificationRequest = budgetModificationRequest;
        this.budgetA = budgetA;
        this.budgetB = budgetB;
        this.amount = amount;
    }
    
    public SelectedBudget(){
        
    }
    
    /**
     * @return the budgetMR
     */
    public BudgetModificationRequest getBudgetModificationRequest() {
        return budgetModificationRequest;
    }

    /**
     * @param budgetMR the budgetMR to set
     */
    public void setBudgetModificationRequest(BudgetModificationRequest budgetModificationRequest) {
        this.budgetModificationRequest = budgetModificationRequest;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the budget
     */
    public Budget getBudgetA() {
        return budgetA;
    }

    /**
     * @param budget the budget to set
     */
    public void setBudgetA(Budget budgetA) {
        this.budgetA = budgetA;
    }
    
    /**
     * @return the budget
     */
    public Budget getBudgetB() {
        return budgetB;
    }

    /**
     * @param budget the budget to set
     */
    public void setBudgetB(Budget budgetB) {
        this.budgetB = budgetB;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Id
    @Column(name = "PK_id")
    private int id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name="FK_modConsecutive", referencedColumnName="PK_consecutive"),
        @JoinColumn(name="FK_modDate", referencedColumnName="PK_date")
    })
    private BudgetModificationRequest budgetModificationRequest;
    
    @OneToOne
    @JoinColumn(name = "FK_budgetA")
    private Budget budgetA;
    @OneToOne
    @JoinColumn(name = "FK_budgetB")
    private Budget budgetB;
    
    private double amount;
    
}
