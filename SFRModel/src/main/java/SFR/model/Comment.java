package sfr.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author arnol
 */
@Entity
@Table(name = "T_SFR_Comment")
public class Comment implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "increment")
    @Column(name = "PK_ID")
    private Integer pkID;
    
    @Column(name = "Comment")
    private String comment;
    
    @Column(name = "Url")
    private String url;
    
    @Column(name = "Author")
    private String author;

    public Comment() {
        
    }
    
    @Column(name = "EntryDate")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date entryDate;
    
    public Comment(String comment, String author, String url, Date entryDate){
        this.comment = comment;
        this.author = author;
        this.url = url;
        this.entryDate = entryDate;
    }

    public Integer getPkID() {
        return pkID;
    }

    public void setPkID(Integer pkID) {
        this.pkID = pkID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Comment:").append("{\n");
        sb.append("\"comment\": \"").append(comment).append("\",");
        sb.append("\"url\": \"").append(url).append("\",");
        sb.append("\"author\": \"").append(author).append("\",");
        sb.append("\"entrydate\":").append(entryDate).append("\n");
        sb.append("}\n");
        return sb.toString();
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
        final Comment other = (Comment) obj;
        if (this.pkID != other.pkID) {
            return false;
        }
        if (!Objects.equals(this.comment, other.comment)) {
            return false;
        }
        if (!Objects.equals(this.author, other.author)) {
            return false;
        }
        return true;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
}
