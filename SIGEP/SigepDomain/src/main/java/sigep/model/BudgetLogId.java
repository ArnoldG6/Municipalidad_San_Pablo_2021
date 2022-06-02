package sigep.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;

@Embeddable
public class BudgetLogId implements Serializable {

    public BudgetLogId(int consecutive, Date date) {
        this.consecutive = consecutive;
        this.date = date;
    }

    public BudgetLogId(Date date) {
        this.date = date;
    }

    public BudgetLogId() {
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + this.consecutive;
        hash = 23 * hash + Objects.hashCode(this.date);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BudgetLogId other = (BudgetLogId) obj;
        if (this.consecutive != other.consecutive) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }
    
    @Column(name = "PK_consecutive")
    private int consecutive;

    @Column(name = "PK_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;

}
