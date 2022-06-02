package sigep.model;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;

@Embeddable
public class BudgetModificationResolutionId implements Serializable {

    public BudgetModificationResolutionId(int consecutive, Date date) {
        this.consecutive = consecutive;
        this.date = date;
    }

    public BudgetModificationResolutionId(Date date) {
        this.date = date;
    }

    public BudgetModificationResolutionId() {
    }

    /**
     * @return the consecutive
     */
    public int getConsecutive() {
        return consecutive;
    }

    /**
     * @param consecutive the consecutive to set
     */
    public void setConsecutive(int consecutive) {
        this.consecutive = consecutive;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "PK_consecutive")
    private int consecutive;

    @Column(name = "PK_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;

}
