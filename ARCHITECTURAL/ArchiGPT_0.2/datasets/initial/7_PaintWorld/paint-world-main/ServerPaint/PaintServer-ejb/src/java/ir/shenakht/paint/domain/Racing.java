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
import javax.persistence.Lob;
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
@Table(name = "racing")
@NamedQueries({
    @NamedQuery(name = "Racing.findAll", query = "SELECT r FROM Racing r"),
    @NamedQuery(name = "Racing.findById", query = "SELECT r FROM Racing r WHERE r.id = :id"),
    @NamedQuery(name = "Racing.findByStart", query = "SELECT r FROM Racing r WHERE r.start = :start"),
    @NamedQuery(name = "Racing.findByEnd", query = "SELECT r FROM Racing r WHERE r.end = :end"),
    @NamedQuery(name = "Racing.findByTitle", query = "SELECT r FROM Racing r WHERE r.title = :title"),
    @NamedQuery(name = "Racing.findByCreateAt", query = "SELECT r FROM Racing r WHERE r.createAt = :createAt"),
    @NamedQuery(name = "Racing.findByUpdateAt", query = "SELECT r FROM Racing r WHERE r.updateAt = :updateAt")})
public class Racing implements Serializable {

    @Lob
    @Size(max = 65535)
    @Column(name = "url")
    private String url;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date start;
    @Basic(optional = false)
    @NotNull
    @Column(name = "end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date end;
    @Size(max = 500)
    @Column(name = "title")
    private String title;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Lob
    @Size(max = 65535)
    @Column(name = "extra_field")
    private String extraField;
    @Basic(optional = false)
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    @Basic(optional = false)
    @Column(name = "update_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "racingId", fetch = FetchType.LAZY)
    private List<Judgment> judgmentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "racingId", fetch = FetchType.LAZY)
    private List<ScaleQuestionForRacing> scaleQuestionForRacingList;
    @OneToMany(mappedBy = "racingId", fetch = FetchType.LAZY)
    private List<Participants> participantsList;

    public Racing() {
    }

    public Racing(Integer id) {
        this.id = id;
    }

    public Racing(Integer id, Date start, Date end, Date createAt, Date updateAt) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtraField() {
        return extraField;
    }

    public void setExtraField(String extraField) {
        this.extraField = extraField;
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

    public List<Judgment> getJudgmentList() {
        return judgmentList;
    }

    public void setJudgmentList(List<Judgment> judgmentList) {
        this.judgmentList = judgmentList;
    }

    public List<ScaleQuestionForRacing> getScaleQuestionForRacingList() {
        return scaleQuestionForRacingList;
    }

    public void setScaleQuestionForRacingList(List<ScaleQuestionForRacing> scaleQuestionForRacingList) {
        this.scaleQuestionForRacingList = scaleQuestionForRacingList;
    }

    public List<Participants> getParticipantsList() {
        return participantsList;
    }

    public void setParticipantsList(List<Participants> participantsList) {
        this.participantsList = participantsList;
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
        if (!(object instanceof Racing)) {
            return false;
        }
        Racing other = (Racing) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ir.shenakht.paint.domain.Racing[ id=" + id + " ]";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}
