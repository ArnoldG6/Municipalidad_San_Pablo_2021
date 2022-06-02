package sigep.model;

import common.model.Official;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SP_BudgetBalanceCertificateRequests")
public class BudgetBalanceCertificateRequest implements Serializable {

    public BudgetBalanceCertificateRequest(BudgetBalanceCertificateRequestId id, Budget budgetItem, BudgetLock lock, Official applicant, RequestStatus status, double amount, String description, String refusalReason) {
        this.id = id;
        this.budgetItem = budgetItem;
        this.applicant = applicant;
        this.status = status;
        this.amount = amount;
        this.description = description;
        this.refusalReason = refusalReason;
        this.lock = lock;
    }

    public BudgetBalanceCertificateRequest(BudgetBalanceCertificateRequestId id) {
        this.id = id;
    }

    public BudgetBalanceCertificateRequest() {
    }

    /**
     * @return the BudgetBalanceCertificateRequestId
     */
    public BudgetBalanceCertificateRequestId getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(BudgetBalanceCertificateRequestId id) {
        this.id = id;
    }

    /**
     * @return the budgetItem
     */
    public Budget getBudgetItem() {
        return budgetItem;
    }

    /**
     * @param budgetItem the budgetItem to set
     */
    public void setBudgetItem(Budget budgetItem) {
        this.budgetItem = budgetItem;
    }

    /**
     * @return the applicant
     */
    public Official getApplicant() {
        return applicant;
    }

    /**
     * @param applicant the applicant to set
     */
    public void setApplicant(Official applicant) {
        this.applicant = applicant;
    }

    /**
     * @return the status
     */
    public RequestStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(RequestStatus status) {
        this.status = status;
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

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the refusalReason
     */
    public String getRefusalReason() {
        return refusalReason;
    }

    /**
     * @param refusalReason the refusalReason to set
     */
    public void setRefusalReason(String refusalReason) {
        this.refusalReason = refusalReason;
    }

    public BudgetLock getLock() {
        return lock;
    }

    public void setLock(BudgetLock lock) {
        this.lock = lock;
    }

    @EmbeddedId
    private BudgetBalanceCertificateRequestId id;

    @OneToOne
    @JoinColumn(name = "FK_budgetItem")
    private Budget budgetItem;

    @OneToOne
    @JoinColumn(name = "FK_applicant")
    private Official applicant;

    @OneToOne
    @JoinColumn(name = "FK_status")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private RequestStatus status;

    @OneToOne
    @JoinColumn(name = "FK_budgetItemLocked")
    private BudgetLock lock;

    private double amount;
    private String description;
    private String refusalReason;

}
