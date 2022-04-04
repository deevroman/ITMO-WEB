package Entity;

import javax.persistence.*;

@Entity
@Table(name = "points", schema = "s311693", catalog = "studs")
public class PointsEntity {
    private Double x;
    private Double y;
    private Double r;
    private Boolean result;
    private String login;
    @Id
    @Basic
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "x")
    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    @Basic
    @Column(name = "y")
    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    @Basic
    @Column(name = "r")
    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    @Basic
    @Column(name = "result")
    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }


    @Basic
    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointsEntity that = (PointsEntity) o;

        if (x != null ? !x.equals(that.x) : that.x != null) return false;
        if (y != null ? !y.equals(that.y) : that.y != null) return false;
        if (r != null ? !r.equals(that.r) : that.r != null) return false;
        if (result != null ? !result.equals(that.result) : that.result != null) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result1 = x != null ? x.hashCode() : 0;
        result1 = 31 * result1 + (y != null ? y.hashCode() : 0);
        result1 = 31 * result1 + (r != null ? r.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (login != null ? login.hashCode() : 0);
        result1 = 31 * result1 + (id != null ? id.hashCode() : 0);
        return result1;
    }
}
