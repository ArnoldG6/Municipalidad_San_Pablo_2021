package common.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SI_USERS")
public class User implements Serializable {

    @Id
    @Column(name = "PK_USER")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int idUser;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_official", referencedColumnName = "PK_OFFICIAL")
    private Official official;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "SI_USER_ROLES",
            joinColumns = @JoinColumn(name = "FK_user"),
            inverseJoinColumns = @JoinColumn(name = "FK_rol")
    )
    private List<Rol> roles;
    @Column(name = "FK_email")
    private String email;
    public User(int idUser, Official official, String email, String password,List<Rol> roles) {
        this.idUser = idUser;
        this.official = official;
        this.email = email;
        this.roles = roles;
    }

    public User(int idUser) {
        this.idUser = idUser;
    }

    public User() {

    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Official getOfficial() {
        return official;
    }

    public void setOfficial(Official official) {
        this.official = official;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
        @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"User\":{");
        sb.append("\"idUser\": ").append(idUser).append(",");
        sb.append("\"email\": \"").append(email).append("\",");
        sb.append(official.toString()).append(",");
        sb.append("\"roles\": ").append(roles.toString());
        sb.append("}");
        return sb.toString();
    }

}
