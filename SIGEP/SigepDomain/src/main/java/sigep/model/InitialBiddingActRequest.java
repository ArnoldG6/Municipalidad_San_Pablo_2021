package sigep.model;

import common.model.Official;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SP_InitialBiddingActRequests")
public class InitialBiddingActRequest implements Serializable {

    public InitialBiddingActRequest(InitialBiddingActRequestId id, Official applicant, BudgetBalanceCertificate certificate, String documentPath, RequestStatus status) {
        this.id = id;
        this.applicant = applicant;
        this.certificate = certificate;
        this.documentPath = documentPath;
        this.status = status;
    }

    public InitialBiddingActRequest(InitialBiddingActRequestId id) {
        this.id = id;
    }

    public InitialBiddingActRequest() {
    }

    public InitialBiddingActRequestId getId() {
        return id;
    }

    public void setId(InitialBiddingActRequestId id) {
        this.id = id;
    }

    public Official getApplicant() {
        return applicant;
    }

    public void setApplicant(Official applicant) {
        this.applicant = applicant;
    }

    public BudgetBalanceCertificate getCertificate() {
        return certificate;
    }

    public void setCertificate(BudgetBalanceCertificate certificate) {
        this.certificate = certificate;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private InitialBiddingActRequestId id;

    @OneToOne
    @JoinColumn(name = "FK_applicant")
    private Official applicant;

    @OneToOne
    @JoinColumns({
        @JoinColumn(name = "FK_cspConsecutive", referencedColumnName = "PK_consecutive"),
        @JoinColumn(name = "FK_cspDate", referencedColumnName = "PK_date")
    })
    private BudgetBalanceCertificate certificate;

    @OneToOne
    @JoinColumn(name = "FK_status")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private RequestStatus status;

    private String documentPath;

}
