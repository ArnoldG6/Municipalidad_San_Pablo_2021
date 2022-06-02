package sigep.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SP_RequestStatuses")
public class RequestStatus implements Serializable {

    public RequestStatus(int idRequestStatus, String description) {
        this.idRequestStatus = idRequestStatus;
        this.description = description;
    }

    public RequestStatus(int idRequestStatus) {
        this.idRequestStatus = idRequestStatus;
    }

    public RequestStatus() {
    }

    /**
     * @return the idRequestStatus
     */
    public int getIdRequestStatus() {
        return idRequestStatus;
    }

    /**
     * @param idRequestStatus the idRequestStatus to set
     */
    public void setIdRequestStatus(int idRequestStatus) {
        this.idRequestStatus = idRequestStatus;
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

    @Id
    @Column(name = "PK_consecutive")
    private int idRequestStatus;
    private String description;

}
