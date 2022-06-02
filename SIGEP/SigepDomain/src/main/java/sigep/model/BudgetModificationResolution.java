/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sigep.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Diego Quiros
 */
@Entity
@Table(name = "sp_budgetmodificationresolution")
public class BudgetModificationResolution implements Serializable{

    
    public BudgetModificationResolution(BudgetModificationResolutionId id, String resolutionPath, BudgetModificationRequest budgetModificationRequest, String description){
        this.id = id;
        this.resolutionPath = resolutionPath;
        this.budgetModificationRequest = budgetModificationRequest;
        this.description = description;
    }
    
     public BudgetModificationResolution(BudgetModificationResolutionId id){
        this.id = id;
    }
    
    public BudgetModificationResolution(){
    }
    
    /**
     * @return the id
     */
    public BudgetModificationResolutionId getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(BudgetModificationResolutionId id) {
        this.id = id;
    }

    /**
     * @return the resolutionPath
     */
    public String getResolutionPath() {
        return resolutionPath;
    }

    /**
     * @param resolutionPath the resolutionPath to set
     */
    public void setResolutionPath(String resolutionPath) {
        this.resolutionPath = resolutionPath;
    }

    /**
     * @return the budgetModificationRequest
     */
    public BudgetModificationRequest getBudgetModificationRequest() {
        return budgetModificationRequest;
    }

    /**
     * @param budgetModificationRequest the budgetModificationRequest to set
     */
    public void setBudgetModificationRequest(BudgetModificationRequest budgetModificationRequest) {
        this.budgetModificationRequest = budgetModificationRequest;
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
    
    @EmbeddedId
    private BudgetModificationResolutionId id;
    
    private String resolutionPath;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name="FK_requestConsecutive", referencedColumnName="PK_consecutive"),
        @JoinColumn(name="FK_requestDate", referencedColumnName="PK_date")
    })
    private BudgetModificationRequest budgetModificationRequest;
    
    private String description;
    
}
