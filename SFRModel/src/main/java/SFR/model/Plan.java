package sfr.model;

import common.model.User;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author arnold
 */
@Entity
@Table(name="T_Plan")
public class Plan implements Serializable {
    
    @Id
    @Column(name = "PK_ID")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;
    @Column(name = "AuthorName")
    private String author;
    @Column(name = "Name")
    private String name;
    @Column(name = "Description")
    private String description;
    @Column(name = "EntryDate")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date entryDate;
    @Column(name = "Status")
    private String status;
    
    @OneToMany
    @JoinTable(
        name = "T_USERPLAN",
        joinColumns = @JoinColumn(name = "FK_PLAN"),
        inverseJoinColumns = @JoinColumn(name = "FK_USER")
    )
    private List<User> involvedList;
    //private List<Incidence> incidenceList;
    //private List<Risk> riskList;
    //private List<Comment> commentList;

    public Plan() {
    }
    
    public Plan(int id) {
        this.id = id;
    }

    public Plan(int id, String author, String name, String desc, Date dateOfAdm,String status) {
        this.id = id;
        this.author = author;
        this.name = name;
        this.description = desc;
        this.entryDate = dateOfAdm;
        this.status = status;
    }

    public int getId() {
        return id;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return status;
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
        return description;
    }
    public void setDesc(String desc) {
        this.description = desc;
    }
    public Date getEntryDate() {
        return entryDate;
    }
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
    public List<User> getInvolvedList() {
        return involvedList;
    }
    public void setInvolvedList(List<User> involvedList) {
        this.involvedList = involvedList;
    }
    /*
    public List<Incidence> getIncidenceList() {
        return incidenceList;
    }
    public void setIncidenceList(List<Incidence> incidenceList) {
        this.incidenceList = incidenceList;
    }
    public List<Risk> getRiskList() {
        return riskList;
    }
    public void setRiskList(List<Risk> riskList) {
        this.riskList = riskList;
    }
    public List<Comment> getCommentList() {
        return commentList;
    }
    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
    */
    
    
}
