package sigep.model;

import common.model.Official;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "SP_BudgetLocks")
public class BudgetLock implements Serializable {

    public BudgetLock(int idLock, Budget budgetItem, Date initialDate, Date endDate, double amount, Official applicant) {
        this.idLock = idLock;
        this.budgetItem = budgetItem;
        this.applicant = applicant;
        this.initialDate = initialDate;
        this.endDate = endDate;
        this.amount = amount;
    }

    public BudgetLock(Budget budgetItem, Official applicant, Date initialDate, Date endDate, double amount) {
        this.budgetItem = budgetItem;
        this.applicant = applicant;
        this.initialDate = initialDate;
        this.endDate = endDate;
        this.amount = amount;
    }

    public BudgetLock(int idLock) {
        this.idLock = idLock;
    }

    public BudgetLock() {

    }

    /**
     * @return the idLock
     */
    public int getIdLock() {
        return idLock;
    }

    /**
     * @param idLock the idLock to set
     */
    public void setIdLock(int idLock) {
        this.idLock = idLock;
    }

    /**
     * @return the budgetItem
     */
    public Budget getBudgetItem() {
        return budgetItem;
    }

    /**
     * @param applicant the idLock to set
     */
    public void setApplicant(Official applicant) {
        this.applicant = applicant;
    }

    /**
     * @return the applicant
     */
    public Official getApplicant() {
        return applicant;
    }

    /**
     * @param budgetItem the budgetItem to set
     */
    public void setBudgetItem(Budget budgetItem) {
        this.budgetItem = budgetItem;
    }

    /**
     * @return the initialDate
     */
    public Date getInitialDate() {
        return initialDate;
    }

    /**
     * @param initialDate the initialDate to set
     */
    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the endDate to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
      /**
     * @return the used
     */
    public boolean getUsed() {
        return used;
    }

    /**
     * @param used the endDate to set
     */
    public void setUsed(boolean used) {
        this.used = used;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_consecutive")
    private int idLock;

    @OneToOne
    @JoinColumn(name = "FK_budgetItem")
    private Budget budgetItem;

    @OneToOne
    @JoinColumn(name = "FK_applicant")
    private Official applicant;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date initialDate;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date endDate;
    private double amount;
    private boolean used;

}
