package common.model;

import com.google.gson.Gson;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
    private Integer idUser;
    @OneToOne
    //@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_official", referencedColumnName = "PK_OFFICIAL")
    private Official official;
    @ManyToMany(
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "SI_USER_ROLES",
            joinColumns = @JoinColumn(name = "FK_user"),
            inverseJoinColumns = @JoinColumn(name = "FK_rol")
    )
    private List<Rol> roles = new ArrayList<>();
    @Column(name = "FK_email")
    private String email;

    public User(Integer idUser, Official official, String email, String password, List<Rol> roles) {
        this.idUser = idUser;
        this.official = official;
        this.email = email;
        this.roles = roles;
    }

    public User(Integer idUser) {
        this.idUser = idUser;
    }

    public User() {

    }

    public User(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
        public static boolean validateSuperAdminRol(User user) {
        return user.getRoles().stream().anyMatch(r -> (r.getIdRol() == 2));
    }

    public static boolean validateBudgetAdminRol(User user) {
        return user.getRoles().stream().anyMatch(rol -> (rol.getIdRol() == 4));
    }

    public static boolean validateTenderAdminRol(User user) {
        return user.getRoles().stream().anyMatch(rol -> (rol.getIdRol() == 5));
    }

    public static boolean validateUserRol(User user) {
        return user.getRoles().stream().anyMatch(rol -> (rol.getIdRol() == 3));
    }

    public static boolean validateRol(User user, Rol rol) {
        return user.getRoles().stream().anyMatch(r -> (r.getIdRol() == rol.getIdRol()));
    }

    public static void removeRol(User user, Rol rol) {
        Iterator<Rol> itr = user.getRoles().iterator();
        while (itr.hasNext()) {
            Rol r = itr.next();
            if (r.getIdRol() == rol.getIdRol()) {
                itr.remove();
            }
        }
    }

}
