package sigep.model;

import common.model.Official;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "SP_BudgetCodeCertificateRequests")
public class BudgetCodeCertificateRequests implements Serializable {

    public BudgetCodeCertificateRequests(BudgetCodeCertificateRequestsId id, BudgetLock lock, Budget budgetItem, Official applicant, RequestStatus status, double amount, String description, String refusalReason) {
        this.id = id;
        this.budgetItem = budgetItem;
        this.applicant = applicant;
        this.status = status;
        this.lock = lock;
        this.amount = amount;
        this.description = description;
        this.refusalReason = refusalReason;
    }

    public BudgetCodeCertificateRequests(BudgetCodeCertificateRequestsId id) {
        this.id = id;
    }

    public BudgetCodeCertificateRequests() {

    }

    /**
     * @return the id
     */
    public BudgetCodeCertificateRequestsId getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(BudgetCodeCertificateRequestsId id) {
        this.id = id;
    }

    /**
     * @return the budget
     */
    public Budget getBudget() {
        return budgetItem;
    }

    /**
     * @param budget the budget to set
     */
    public void setBudget(Budget budgetItem) {
        this.budgetItem = budgetItem;
    }

    /**
     * @return the user
     */
    public Official getApplicant() {
        return applicant;
    }

    /**
     * @param user the user to set
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
    private BudgetCodeCertificateRequestsId id;

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
