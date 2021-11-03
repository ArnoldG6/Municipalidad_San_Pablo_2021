package sfr.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author arnold
 */
@Entity
@Table(name = "T_Plan")
public class Plan implements Serializable {

    @Id
    @Column(name = "PK_ID")
    private String id;

    @Column(name = "AuthorName")
    private String authorName;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "EntryDate")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date entryDate;

    @Column(name = "Status")
    private String status;

    @Column(name = "Type")
    private String type;

//    @OneToMany
//    @JoinTable(
//            name = "T_USERPLAN",
//            joinColumns = @JoinColumn(name = "FK_PLAN"),
//            inverseJoinColumns = @JoinColumn(name = "FK_USER")
//    )
//    private List<User> involvedList;
    @OneToMany//(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "T_RISKPLAN",
            joinColumns = @JoinColumn(name = "FK_PLAN"),
            inverseJoinColumns = @JoinColumn(name = "FK_RISK")
    )
    private List<Risk> riskList;
    public Plan() {
        
    }

    public Plan(String id) {
        this.id = id;
    }

    public Plan(String authorName, String name, 
            String desc, Date dateOfAdm, 
            String status, 
            String type,List<Risk> riskList) {
          //,List<User> involvedList) {
        this.authorName = authorName;
        this.name = name;
        this.description = desc;
        this.entryDate = dateOfAdm;
        this.status = status;
        this.type = type;
        this.riskList = riskList;
        //this.involvedList = involvedList;
    }

    public String getId() {
        return id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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

    //public List<User> getInvolvedList() {
    //    return involvedList;
    //}

    //public void setInvolvedList(List<User> involvedList) {
    //    this.involvedList = involvedList;
    // }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Plan:").append("{\n");
        sb.append("\"id\": \"").append(id).append("\",\n");
        sb.append("\"authorName\": \"").append(authorName).append("\",\n");
        sb.append("\"name\": \"").append(name).append("\",");
        sb.append("\"description\": \"").append(description).append("\",\n");
        sb.append("\"entryDate\": \"").append(entryDate).append("\",\n");
        sb.append("\"status\": \"").append(status).append("\",\n");
        sb.append("\"type\": \"").append(type).append("\"\n");
        sb.append("\"riskList\":").append(riskList).append("\n");
        //sb.append("\"involvedList\": ").append(involvedList.toString());
        sb.append("}\n");
        return sb.toString();
    }
    /*
    public List<Incidence> getIncidenceList() {
        return incidenceList;
    }
    public void setIncidenceList(List<Incidence> incidenceList) {
        this.incidenceList = incidenceList;
    }
    */
    public List<Risk> getRiskList() {
        return riskList;
    }
    
    public void setRiskList(List<Risk> riskList) {
        this.riskList = riskList;
    }
    /*
    public List<Comment> getCommentList() {
        return commentList;
    }
    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
     */

}