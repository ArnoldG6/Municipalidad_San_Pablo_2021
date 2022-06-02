package sigep.model;

import common.model.Official;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SP_BudgetCodeCertificates")
public class BudgetCodeCertificate implements Serializable {

    public BudgetCodeCertificate(BudgetCodeCertificateId id, Official transmitter, Official beneficiary, BudgetLock budgetLock, Budget budgetItem, BudgetCodeCertificateRequests request, double amount, String documentPath) {
        this.id = id;
        this.transmitter = transmitter;
        this.beneficiary = beneficiary;
        this.budgetItem = budgetItem;
        this.budgetLock = budgetLock;
        this.request = request;
        this.amount = amount;
        this.documentPath = documentPath;
    }

    public BudgetCodeCertificate(BudgetCodeCertificateId id) {
        this.id = id;
    }

    public BudgetCodeCertificate() {
    }

    /**
     * @return the id
     */
    public BudgetCodeCertificateId getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(BudgetCodeCertificateId id) {
        this.id = id;
    }

    /**
     * @return the transmitter
     */
    public Official getTransmitter() {
        return transmitter;
    }

    /**
     * @param transmitter the transmitter to set
     */
    public void setTransmitter(Official transmitter) {
        this.transmitter = transmitter;
    }

    /**
     * @return the beneficiary
     */
    public Official getBeneficiary() {
        return beneficiary;
    }

    /**
     * @param beneficiary the beneficiary to set
     */
    public void setBeneficiary(Official beneficiary) {
        this.beneficiary = beneficiary;
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
     * @return the request
     */
    public BudgetCodeCertificateRequests getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(BudgetCodeCertificateRequests request) {
        this.request = request;
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
     * @return the documentPath
     */
    public String getDocumentPath() {
        return documentPath;
    }

    /**
     * @param documentPath the documentPath to set
     */
    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    /**
     * @return the budgetLock
     */
    public BudgetLock getBudgetLock() {
        return budgetLock;
    }

    /**
     * @param budgetLock the budgetLock to set
     */
    public void setBudgetLock(BudgetLock budgetLock) {
        this.budgetLock = budgetLock;
    }

    @EmbeddedId
    private BudgetCodeCertificateId id;

    @OneToOne
    @JoinColumn(name = "FK_transmitter")
    private Official transmitter;

    @OneToOne
    @JoinColumn(name = "FK_beneficiary")
    private Official beneficiary;

    @OneToOne
    @JoinColumn(name = "FK_budgetItem")
    private Budget budgetItem;

    @OneToOne
    @JoinColumns({
        @JoinColumn(name = "FK_sccpConsecutive", referencedColumnName = "PK_consecutive"),
        @JoinColumn(name = "FK_sccpDate", referencedColumnName = "PK_date")
    })
    private BudgetCodeCertificateRequests request;

    @OneToOne
    @JoinColumn(name = "FK_budgetItemLocked")
    private BudgetLock budgetLock;
    private double amount;
    private String documentPath;

}
