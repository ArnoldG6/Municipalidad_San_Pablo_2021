package sfr.model;

import common.model.User;
import java.io.Serializable;
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
public class Plan implements Serializable {
    
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public Date getEntryDate() {
        return entryDate;
    }
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
    public HashMap<String, User> getInvolvedList() {
        return involvedList;
    }
    public void setInvolvedList(HashMap<String, User> involvedList) {
        this.involvedList = involvedList;
    }
    public HashMap<String, Incidence> getIncidenceList() {
        return incidenceList;
    }
    public void setIncidenceList(HashMap<String, Incidence> incidenceList) {
        this.incidenceList = incidenceList;
    }
    public HashMap<String, Risk> getRiskList() {
        return riskList;
    }
    public void setRiskList(HashMap<String, Risk> riskList) {
        this.riskList = riskList;
    }
    public HashMap<String, Comment> getCommentList() {
        return commentList;
    }
    public void setCommentList(HashMap<String, Comment> commentList) {
        this.commentList = commentList;
    }
    
    
}
