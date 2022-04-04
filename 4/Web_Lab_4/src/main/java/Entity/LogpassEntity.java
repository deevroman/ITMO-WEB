package Entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users4", schema = "s311693", catalog="studs")
@NamedQuery(name = "findByLogin", query = "from LogpassEntity where login = :login")
@NamedQuery(name = "findByToken", query = "from LogpassEntity where token = :token")
public class LogpassEntity {
    @Id
    @Basic
    private String login;

    @Basic
    private String pass;

    @Id
    @Basic
    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "pass")
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Basic
    @Column(name = "token")
    public String token;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogpassEntity that = (LogpassEntity) o;

        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (pass != null ? !pass.equals(that.pass) : that.pass != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (pass != null ? pass.hashCode() : 0);
        return result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void generateToken(){
        this.setToken(UUID.randomUUID().toString());
    }
}
