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
@Table(name = "question")
@NamedQueries({
    @NamedQuery(name = "Question.findAll", query = "SELECT q FROM Question q"),
    @NamedQuery(name = "Question.findById", query = "SELECT q FROM Question q WHERE q.id = :id"),
    @NamedQuery(name = "Question.findByText", query = "SELECT q FROM Question q WHERE q.text = :text"),
    @NamedQuery(name = "Question.findByCreateAt", query = "SELECT q FROM Question q WHERE q.createAt = :createAt"),
    @NamedQuery(name = "Question.findByUpdateAt", query = "SELECT q FROM Question q WHERE q.updateAt = :updateAt")})
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "text")
    private String text;
    @Basic(optional = false)
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    @Basic(optional = false)
    @Column(name = "update_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionId", fetch = FetchType.LAZY)
    private List<Judgment> judgmentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionId", fetch = FetchType.LAZY)
    private List<ScaleQuestionForRacing> scaleQuestionForRacingList;

    public Question() {
    }

    public Question(Integer id) {
        this.id = id;
    }

    public Question(Integer id, String text, Date createAt, Date updateAt) {
        this.id = id;
        this.text = text;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ir.shenakht.paint.domain.Question[ id=" + id + " ]";
    }
    
}
