package common.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_official", referencedColumnName = "PK_OFFICIAL")
    private Official official;

    @OneToMany
    @JoinTable(
            name = "SI_USER_ROLES",
            joinColumns = @JoinColumn(name = "FK_user"),
            inverseJoinColumns = @JoinColumn(name = "FK_rol")
    )
    /*
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final User other = (User) obj;
        if (!Objects.equals(idUser, other.idUser)) return false;
        if (!Objects.equals(official, other.official)) return false;
        if (!Objects.equals(email, other.email)) return false;
        if (!Objects.equals(password, other.password)) return false;
        return true;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Username: ").append(idUser);
        sb.append("E-mail: ").append(email);
        sb.append("Role: ").append(official);
        //sb.append("Pwd: ").append(password);
        return sb.toString();
    }
    */
    private List<Rol> roles;

    @Column(name = "FK_email")
    private String email;

    private String password;

    public User(int idUser, Official official, String email, String password) {
        this.idUser = idUser;
        this.official = official;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
