package sigep.model;
import common.model.Official;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sp_budgetmodificationrequest")
public class BudgetModificationRequest implements Serializable{

    public BudgetModificationRequest(BudgetModificationRequestId id, Official applicant, RequestStatus status){
        this.id = id;
        this.applicant = applicant;
        this.status = status;
    }
    
    public BudgetModificationRequest(BudgetModificationRequestId id){
        this.id = id;
    }
    
    public BudgetModificationRequest(){
    }
    
    
    /**
     * @return the id
     */
    public BudgetModificationRequestId getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(BudgetModificationRequestId id) {
        this.id = id;
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

    @EmbeddedId
    private BudgetModificationRequestId id;
    
    @OneToOne
    @JoinColumn(name = "FK_applicant")
    private Official applicant;
    
    @OneToOne
    @JoinColumn(name = "FK_status")
    private RequestStatus status;
    
    
    
}
