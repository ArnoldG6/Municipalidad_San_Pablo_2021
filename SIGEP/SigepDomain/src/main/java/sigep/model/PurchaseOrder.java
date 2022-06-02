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
@Table(name = "SP_PurchaseOrders")
public class PurchaseOrder implements Serializable {

    public PurchaseOrder(PurchaseOrderId id, Official transmitter, BiddingAct biddingAct, String documentPath) {
        this.id = id;
        this.transmitter = transmitter;
        this.biddingAct = biddingAct;
        this.documentPath = documentPath;
    }

    public PurchaseOrder(PurchaseOrderId id) {
        this.id = id;
    }

    public PurchaseOrder() {
    }

    public PurchaseOrderId getId() {
        return id;
    }

    public void setId(PurchaseOrderId id) {
        this.id = id;
    }

    public Official getTransmitter() {
        return transmitter;
    }

    public void setTransmitter(Official transmitter) {
        this.transmitter = transmitter;
    }

    public BiddingAct getBiddingAct() {
        return biddingAct;
    }

    public void setBiddingAct(BiddingAct biddingAct) {
        this.biddingAct = biddingAct;
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
    private PurchaseOrderId id;

    @OneToOne
    @JoinColumn(name = "FK_transmitter")
    private Official transmitter;

    @OneToOne
    @JoinColumns({
        @JoinColumn(name = "FK_alConsecutive", referencedColumnName = "PK_consecutive"),
        @JoinColumn(name = "FK_alDate", referencedColumnName = "PK_date")
    })
    private BiddingAct biddingAct;

    private String documentPath;

    @OneToOne
    @JoinColumn(name = "FK_status")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private RequestStatus status;

}
