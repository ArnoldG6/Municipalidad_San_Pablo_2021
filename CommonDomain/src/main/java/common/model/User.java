package common.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.List;
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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SI_USERS")
public class User implements Serializable {

    @Id
    @Column(name = "PK_USER")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Expose
    private Integer idUser;
    @OneToOne
    //@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_official", referencedColumnName = "PK_OFFICIAL")
    @Expose
    private Official official;
    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(
            name = "SI_USER_ROLES",
            joinColumns = @JoinColumn(name = "FK_user"),
            inverseJoinColumns = @JoinColumn(name = "FK_rol")
    )
    @Expose
    private List<Rol> roles;
    @Column(name = "FK_email")
    @Expose
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    public User(Integer idUser, Official official, String email, List<Rol> roles, String password) {
        this.idUser = idUser;
        this.official = official;
        this.email = email;
        this.roles = roles;
        this.password = password;
    }

    public User(Integer idUser) {
        this.idUser = idUser;
    }

    public User() {

    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
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

    public boolean hasRol(String rol) {
        return roles.stream().filter(r -> r.getDescription().equals(rol)).findFirst().isPresent();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
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
        User u = (User) obj;
        if (this.idUser.equals(u.getIdUser())) {
            return true;
        }
        return false;
    }

}
