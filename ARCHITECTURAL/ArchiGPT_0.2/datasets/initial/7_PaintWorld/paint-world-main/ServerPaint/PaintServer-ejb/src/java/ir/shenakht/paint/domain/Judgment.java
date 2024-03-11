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
@Table(name = "judgment")
@NamedQueries({
    @NamedQuery(name = "Judgment.findAll", query = "SELECT j FROM Judgment j"),
    @NamedQuery(name = "Judgment.findById", query = "SELECT j FROM Judgment j WHERE j.id = :id"),
    @NamedQuery(name = "Judgment.findByJudgeUser", query = "SELECT j FROM Judgment j WHERE j.judgeUserId = :judgeUser"),
    @NamedQuery(name = "Judgment.findByParticipants", query = "SELECT j FROM Judgment j WHERE j.participantsId = :participants"),
    @NamedQuery(name = "Judgment.findByJudgeUser&Participants&Question&Racing",
            query = "SELECT j FROM Judgment j WHERE j.judgeUserId = :judgeUser AND j.participantsId = :participants AND j.questionId = :question AND j.racingId = :racing"),
    @NamedQuery(name = "Judgment.findByScore", query = "SELECT j FROM Judgment j WHERE j.score = :score")})
public class Judgment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "score")
    private float score;
    @JoinColumn(name = "judge_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private JudgeUser judgeUserId;
    @JoinColumn(name = "participants_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Participants participantsId;
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Question questionId;
    @JoinColumn(name = "racing_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Racing racingId;

    public Judgment() {
    }

    public Judgment(Integer id) {
        this.id = id;
    }

    public Judgment(Integer id, float score) {
        this.id = id;
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public JudgeUser getJudgeUserId() {
        return judgeUserId;
    }

    public void setJudgeUserId(JudgeUser judgeUserId) {
        this.judgeUserId = judgeUserId;
    }

    public Participants getParticipantsId() {
        return participantsId;
    }

    public void setParticipantsId(Participants participantsId) {
        this.participantsId = participantsId;
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
        if (!(object instanceof Judgment)) {
            return false;
        }
        Judgment other = (Judgment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ir.shenakht.paint.domain.Judgment[ id=" + id + " ]";
    }

}
