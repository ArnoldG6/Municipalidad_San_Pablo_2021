package sfr.model;

import common.model.User;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author arnold
 */
@Entity
@Table(name="T_Plan")
public class Plan {
    
    @Id
    @Column(name = "PK_ROL")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;
    private String author;
    private String name;
    private String desc;
    private Date entryDate;
    private HashMap<String, User> involvedList;
    private HashMap<String, Incidence> incidenceList;
    private HashMap<String, Risk> riskList;
    private HashMap<String, Comment> commentList;

    public Plan() {
    }
    
    public Plan(int id) {
        this.id = id;
    }

    public Plan(int id, String author, String name, String desc, Date dateOfAdm) {
        this.id = id;
        this.author = author;
        this.name = name;
        this.desc = desc;
        this.entryDate = dateOfAdm;
        this.involvedList = new HashMap<>();
        this.incidenceList = new HashMap<>();
        this.riskList = new HashMap<>();
        this.commentList = new HashMap<>();
    }

    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return the entryDate
     */
    public Date getEntryDate() {
        return entryDate;
    }

    /**
     * @param entryDate the entryDate to set
     */
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    /**
     * @return the involvedList
     */
    public HashMap<String, User> getInvolvedList() {
        return involvedList;
    }

    /**
     * @param involvedList the involvedList to set
     */
    public void setInvolvedList(HashMap<String, User> involvedList) {
        this.involvedList = involvedList;
    }

    /**
     * @return the incidenceList
     */
    public HashMap<String, Incidence> getIncidenceList() {
        return incidenceList;
    }

    /**
     * @param incidenceList the incidenceList to set
     */
    public void setIncidenceList(HashMap<String, Incidence> incidenceList) {
        this.incidenceList = incidenceList;
    }

    /**
     * @return the riskList
     */
    public HashMap<String, Risk> getRiskList() {
        return riskList;
    }

    /**
     * @param riskList the riskList to set
     */
    public void setRiskList(HashMap<String, Risk> riskList) {
        this.riskList = riskList;
    }

    /**
     * @return the commentList
     */
    public HashMap<String, Comment> getCommentList() {
        return commentList;
    }

    /**
     * @param commentList the commentList to set
     */
    public void setCommentList(HashMap<String, Comment> commentList) {
        this.commentList = commentList;
    }
    
    
}
