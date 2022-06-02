package sigep.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SP_Budget")
public class Budget implements Serializable {

    public Budget(String idItem, Budget father, String description, double ordinaryAmount, double availableAmount, double extraordinaryAmount, double blockedAmount, double pettyCash, double tenders, double increased, double decreased) {
        this.idItem = idItem;
        this.father = father;
        this.description = description;
        this.ordinaryAmount = ordinaryAmount;
        this.availableAmount = availableAmount;
        this.extraordinaryAmount = extraordinaryAmount;
        this.blockedAmount = blockedAmount;
        this.pettyCash = pettyCash;
        this.tenders = tenders;
        this.increased = increased;
        this.decreased = decreased;
    }

    public Budget(String idItem) {
        this.idItem = idItem;
    }

    public Budget() {

    }

    /**
     * @return the idItem
     */
    public String getIdItem() {
        return idItem;
    }

    /**
     * @param idItem the idItem to set
     */
    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    /**
     * @return the father
     */
    public Budget getFather() {
        return father;
    }

    /**
     * @param father the father to set
     */
    public void setFather(Budget father) {
        this.father = father;
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
     * @return the ordinaryAmount
     */
    public double getOrdinaryAmount() {
        return ordinaryAmount;
    }

    /**
     * @param ordinaryAmount the ordinaryAmount to set
     */
    public void setOrdinaryAmount(double ordinaryAmount) {
        this.ordinaryAmount = ordinaryAmount;
    }

    /**
     * @return the availableAmount
     */
    public double getAvailableAmount() {
        return availableAmount;
    }

    /**
     * @param availableAmount the availableAmount to set
     */
    public void setAvailableAmount(double availableAmount) {
        this.availableAmount = availableAmount;
    }

    /**
     * @return the extraordinaryAmount
     */
    public double getExtraordinaryAmount() {
        return extraordinaryAmount;
    }

    /**
     * @param extraordinaryAmount the extraordinaryAmount to set
     */
    public void setExtraordinaryAmount(double extraordinaryAmount) {
        this.extraordinaryAmount = extraordinaryAmount;
    }

    /**
     * @return the blockedAmount
     */
    public double getBlockedAmount() {
        return blockedAmount;
    }

    /**
     * @param blockedAmount the blockedAmount to set
     */
    public void setBlockedAmount(double blockedAmount) {
        this.blockedAmount = blockedAmount;
    }

    /**
     * @return the pettyCash
     */
    public double getPettyCash() {
        return pettyCash;
    }

    /**
     * @param pettyCash the pettyCash to set
     */
    public void setPettyCash(double pettyCash) {
        this.pettyCash = pettyCash;
    }

    /**
     * @return the tenders
     */
    public double getTenders() {
        return tenders;
    }

    /**
     * @param tenders the tenders to set
     */
    public void setTenders(double tenders) {
        this.tenders = tenders;
    }

    /**
     * @return the increased
     */
    public double getIncreased() {
        return increased;
    }

    /**
     * @param increased the tenders to set
     */
    public void setIncreased(double increased) {
        this.increased = increased;
    }

    /**
     * @return the decreased
     */
    public double getDecreased() {
        return decreased;
    }

    /**
     * @param decreased the tenders to set
     */
    public void setDecreased(double decreased) {
        this.decreased = decreased;
    }

    public boolean isReceives() {
        return receives;
    }

    public void setReceives(boolean receives) {
        this.receives = receives;
    }

    @Id
    @Column(name = "PK_item")
    private String idItem;

    @OneToOne
    @JoinColumn(name = "FK_fatherItem")
    private Budget father;
    private String description;
    private double ordinaryAmount;
    private double availableAmount;
    private double extraordinaryAmount;
    private double blockedAmount;
    private double pettyCash;
    private double tenders;
    private double increased;
    private double decreased;
    private boolean receives;

}
