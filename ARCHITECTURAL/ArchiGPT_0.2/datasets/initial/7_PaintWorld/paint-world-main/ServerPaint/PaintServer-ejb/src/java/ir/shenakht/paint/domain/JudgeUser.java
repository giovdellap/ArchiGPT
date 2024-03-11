/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author hossien
 */
@Entity
@Table(name = "judge_user")
@NamedQueries({
    @NamedQuery(name = "JudgeUser.findByEmail&&!UserId", query = "SELECT j FROM JudgeUser j WHERE j.email = :email AND j.id <> :id"),
    @NamedQuery(name = "JudgeUser.findByUsername&&!UserId", query = "SELECT j FROM JudgeUser j WHERE j.username = :username AND j.id <> :id"),
    @NamedQuery(name = "JudgeUser.findByUserType", query = "SELECT j FROM JudgeUser j WHERE j.typeUser = :typeUser"),
    @NamedQuery(name = "JudgeUser.findByWithLastTimeCreate", query = "SELECT j FROM JudgeUser j WHERE j.createAt > :time"),
    @NamedQuery(name = "JudgeUser.findAll", query = "SELECT j FROM JudgeUser j"),
    @NamedQuery(name = "JudgeUser.findById", query = "SELECT j FROM JudgeUser j WHERE j.id = :id"),
    @NamedQuery(name = "JudgeUser.findByFirstName", query = "SELECT j FROM JudgeUser j WHERE j.firstName = :firstName"),
    @NamedQuery(name = "JudgeUser.findByLastName", query = "SELECT j FROM JudgeUser j WHERE j.lastName = :lastName"),
    @NamedQuery(name = "JudgeUser.findByUsername", query = "SELECT j FROM JudgeUser j WHERE j.username = :username"),
    @NamedQuery(name = "JudgeUser.findByPhone", query = "SELECT j FROM JudgeUser j WHERE j.phone = :phone"),
    @NamedQuery(name = "JudgeUser.findByEmail", query = "SELECT j FROM JudgeUser j WHERE j.email = :email"),
    @NamedQuery(name = "JudgeUser.findByCreateAt", query = "SELECT j FROM JudgeUser j WHERE j.createAt = :createAt"),
    @NamedQuery(name = "JudgeUser.findByUpdateAt", query = "SELECT j FROM JudgeUser j WHERE j.updateAt = :updateAt"),
    @NamedQuery(name = "JudgeUser.findByLastLogin", query = "SELECT j FROM JudgeUser j WHERE j.lastLogin = :lastLogin"),
    @NamedQuery(name = "JudgeUser.findByUserCode", query = "SELECT j FROM JudgeUser j WHERE j.userCode = :userCode"),
    @NamedQuery(name = "JudgeUser.findByPassword", query = "SELECT j FROM JudgeUser j WHERE j.password = :password"),
    @NamedQuery(name = "JudgeUser.findByActive", query = "SELECT j FROM JudgeUser j WHERE j.active = :active"),
    @NamedQuery(name = "JudgeUser.findByDeleteUser", query = "SELECT j FROM JudgeUser j WHERE j.deleteUser = :deleteUser"),
    @NamedQuery(name = "JudgeUser.findByGender", query = "SELECT j FROM JudgeUser j WHERE j.gender = :gender")})
public class JudgeUser implements Serializable {

    @Column(name = "type_user")
    private Integer typeUser;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "first_name")
    private String firstName;
    @Size(max = 100)
    @Column(name = "last_name")
    private String lastName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "username")
    private String username;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "phone")
    private String phone;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 150)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    @Basic(optional = false)
    @Column(name = "update_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
    @Column(name = "last_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "user_code")
    private String userCode;
    @Size(max = 150)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private boolean active;
    @Basic(optional = false)
    @NotNull
    @Column(name = "delete_user")
    private boolean deleteUser;
    @Column(name = "gender")
    private Integer gender;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "judgeUserId", fetch = FetchType.LAZY)
    private List<Judgment> judgmentList;

    public JudgeUser() {
    }

    public JudgeUser(Integer id) {
        this.id = id;
    }

    public JudgeUser(Integer id, String username, String phone, Date createAt, Date updateAt, String userCode, boolean active, boolean deleteUser) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.userCode = userCode;
        this.active = active;
        this.deleteUser = deleteUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(boolean deleteUser) {
        this.deleteUser = deleteUser;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public List<Judgment> getJudgmentList() {
        return judgmentList;
    }

    public void setJudgmentList(List<Judgment> judgmentList) {
        this.judgmentList = judgmentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JudgeUser)) {
            return false;
        }
        JudgeUser other = (JudgeUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ir.shenakht.paint.domain.JudgeUser[ id=" + id + " ]";
    }

    public Integer getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(Integer typeUser) {
        this.typeUser = typeUser;
    }

}
