/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author hossien
 */
@Entity
@Table(name = "scale_question_for_racing")
@NamedQueries({
    @NamedQuery(name = "ScaleQuestionForRacing.findAll", query = "SELECT s FROM ScaleQuestionForRacing s"),
    @NamedQuery(name = "ScaleQuestionForRacing.findById", query = "SELECT s FROM ScaleQuestionForRacing s WHERE s.id = :id"),
    @NamedQuery(name = "ScaleQuestionForRacing.findByScale", query = "SELECT s FROM ScaleQuestionForRacing s WHERE s.scale = :scale")})
public class ScaleQuestionForRacing implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "scale")
    private float scale;
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Question questionId;
    @JoinColumn(name = "racing_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Racing racingId;

    public ScaleQuestionForRacing() {
    }

    public ScaleQuestionForRacing(Integer id) {
        this.id = id;
    }

    public ScaleQuestionForRacing(Integer id, float scale) {
        this.id = id;
        this.scale = scale;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Question getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Question questionId) {
        this.questionId = questionId;
    }

    public Racing getRacingId() {
        return racingId;
    }

    public void setRacingId(Racing racingId) {
        this.racingId = racingId;
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
        if (!(object instanceof ScaleQuestionForRacing)) {
            return false;
        }
        ScaleQuestionForRacing other = (ScaleQuestionForRacing) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ir.shenakht.paint.domain.ScaleQuestionForRacing[ id=" + id + " ]";
    }
    
}
