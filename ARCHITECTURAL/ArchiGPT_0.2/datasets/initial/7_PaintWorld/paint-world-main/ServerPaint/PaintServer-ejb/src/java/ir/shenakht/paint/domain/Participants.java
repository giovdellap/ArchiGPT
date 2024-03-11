/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hossien
 */
@Entity
@Table(name = "participants")
@NamedQueries({
    @NamedQuery(name = "Participants.findAll", query = "SELECT p FROM Participants p")
    ,
    @NamedQuery(name = "Participants.findById", query = "SELECT p FROM Participants p WHERE p.id = :id")
    ,
    @NamedQuery(name = "Participants.findByUser", query = "SELECT p FROM Participants p WHERE p.userId = :user")
    ,
    @NamedQuery(name = "Participants.findByRacing", query = "SELECT p FROM Participants p WHERE p.racingId = :racing")
    ,
    @NamedQuery(name = "Participants.findByFirstName", query = "SELECT p FROM Participants p WHERE p.firstName = :firstName")
    ,
    @NamedQuery(name = "Participants.findByLastName", query = "SELECT p FROM Participants p WHERE p.lastName = :lastName")
    ,
    @NamedQuery(name = "Participants.findByAge", query = "SELECT p FROM Participants p WHERE p.age = :age")
    ,
    @NamedQuery(name = "Participants.findByConditionType", query = "SELECT p FROM Participants p WHERE p.conditionType = :conditionType")
    ,
    @NamedQuery(name = "Participants.findByAddress", query = "SELECT p FROM Participants p WHERE p.address = :address")
    ,
    @NamedQuery(name = "Participants.findByTotalScore", query = "SELECT p FROM Participants p WHERE p.totalScore = :totalScore")
    ,
    @NamedQuery(name = "Participants.findByIsSelective", query = "SELECT p FROM Participants p WHERE p.isSelective = :isSelective")
    ,
    @NamedQuery(name = "Participants.findByCreateAt", query = "SELECT p FROM Participants p WHERE p.createAt = :createAt")
    ,
    @NamedQuery(name = "Participants.findByCreateAtBiggerNow", query = "SELECT p FROM Participants p WHERE p.createAt >= :time")
    ,
    @NamedQuery(name = "Participants.findByUpdateAt", query = "SELECT p FROM Participants p WHERE p.updateAt = :updateAt")})
public class Participants implements Serializable {

    @Size(max = 50)
    @Column(name = "display_time")
    private String displayTime;

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
    @Column(name = "age")
    private Integer age;
    @Lob
    @Size(max = 65535)
    @Column(name = "url")
    private String url;
    @Size(max = 500)
    @Column(name = "address")
    private String address;
    @Column(name = "total_score")
    private Integer totalScore;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_selective")
    private boolean isSelective;
    @Basic(optional = false)
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    @Basic(optional = false)
    @Column(name = "update_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
    @Column(name = "birth_year")
    private Integer birthYear;
    @Column(name = "birth_month")
    private Integer birthMonth;
    @Column(name = "birth_day")
    private Integer birthDay;
    @Size(max = 6)
    @Column(name = "gender")
    private String gender;
    @Size(max = 100)
    @Column(name = "city")
    private String city;
    @Size(max = 100)
    @Column(name = "tag")
    private String tag;
    @Size(max = 1000)
    @Column(name = "desciption")
    private String desciption;
    @Size(max = 100)
    @Column(name = "status")
    private String status;
    @Column(name = "condition_type")
    private Integer conditionType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "participantsId", fetch = FetchType.LAZY)
    private List<Judgment> judgmentList;
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private City cityId;
    @JoinColumn(name = "racing_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Racing racingId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User userId;

    public Participants() {
    }

    public Participants(Integer id) {
        this.id = id;
    }

    public Participants(Integer id, boolean isSelective, Date createAt, Date updateAt) {
        this.id = id;
        this.isSelective = isSelective;
        this.createAt = createAt;
        this.updateAt = updateAt;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Boolean getIsSelective() {
        return isSelective;
    }

    public void setIsSelective(boolean isSelective) {
        this.isSelective = isSelective;
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

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(Integer birthMonth) {
        this.birthMonth = birthMonth;
    }

    public Integer getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Integer birthDay) {
        this.birthDay = birthDay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getConditionType() {
        return conditionType;
    }

    public void setConditionType(Integer conditionType) {
        this.conditionType = conditionType;
    }

    @XmlTransient
    public List<Judgment> getJudgmentList() {
        return judgmentList;
    }

    public void setJudgmentList(List<Judgment> judgmentList) {
        this.judgmentList = judgmentList;
    }

    public City getCityId() {
        return cityId;
    }

    public void setCityId(City cityId) {
        this.cityId = cityId;
    }

    public Racing getRacingId() {
        return racingId;
    }

    public void setRacingId(Racing racingId) {
        this.racingId = racingId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
        if (!(object instanceof Participants)) {
            return false;
        }
        Participants other = (Participants) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ir.apora.domain.Participants[ id=" + id + " ]";
    }

    public String getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(String displayTime) {
        this.displayTime = displayTime;
    }

}
