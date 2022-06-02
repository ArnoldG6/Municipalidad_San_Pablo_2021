package sigep.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;


@Embeddable
public class BudgetCodeCertificateRequestsId implements Serializable{
    
    public BudgetCodeCertificateRequestsId(int consecutive, Date date) {
        this.consecutive = consecutive;
        this.date = date;
    }

    public BudgetCodeCertificateRequestsId(Date date) {
        this.date = date;
    }

    public BudgetCodeCertificateRequestsId() {
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
        int hash = 7;
        hash = 37 * hash + this.consecutive;
        hash = 37 * hash + Objects.hashCode(this.date);
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
        final BudgetCodeCertificateRequestsId other = (BudgetCodeCertificateRequestsId) obj;
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