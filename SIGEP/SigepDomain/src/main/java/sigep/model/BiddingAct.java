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
@Table(name = "SP_BiddingActs")
public class BiddingAct implements Serializable {

    public BiddingAct(BiddingActId id, Official transmitter, Official beneficiary, InitialBiddingActRequest initialAct, String documentPath) {
        this.id = id;
        this.transmitter = transmitter;
        this.beneficiary = beneficiary;
        this.initialAct = initialAct;
        this.documentPath = documentPath;
    }

    public BiddingAct(BiddingActId id) {
        this.id = id;
    }

    public BiddingAct() {
    }

    public BiddingActId getId() {
        return id;
    }

    public void setId(BiddingActId id) {
        this.id = id;
    }

    public Official getTransmitter() {
        return transmitter;
    }

    public void setTransmitter(Official transmitter) {
        this.transmitter = transmitter;
    }

    public Official getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Official beneficiary) {
        this.beneficiary = beneficiary;
    }

    public InitialBiddingActRequest getInitialAct() {
        return initialAct;
    }

    public void setInitialAct(InitialBiddingActRequest initialAct) {
        this.initialAct = initialAct;
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
    private BiddingActId id;
    
    @OneToOne
    @JoinColumn(name = "FK_transmitter")
    private Official transmitter;
    
    @OneToOne
    @JoinColumn(name = "FK_beneficiary")
    private Official beneficiary;
    
    @OneToOne
    @JoinColumns({
        @JoinColumn(name = "FK_ailConsecutive", referencedColumnName = "PK_consecutive"),
        @JoinColumn(name = "FK_ailDate", referencedColumnName = "PK_date")
    })
    private InitialBiddingActRequest initialAct;
    
    private String documentPath;
    
    @OneToOne
    @JoinColumn(name = "FK_status")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private RequestStatus status;
}
