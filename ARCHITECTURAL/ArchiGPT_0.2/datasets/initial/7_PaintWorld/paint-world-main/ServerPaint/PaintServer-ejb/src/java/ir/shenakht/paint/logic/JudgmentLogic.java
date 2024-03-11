/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic;

import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.controller.interfaces.JudgmentJpaControllerIntf;
import ir.shenakht.paint.domain.JudgeUser;
import ir.shenakht.paint.domain.Judgment;
import ir.shenakht.paint.domain.Participants;
import ir.shenakht.paint.domain.Question;
import ir.shenakht.paint.domain.Racing;
import ir.shenakht.paint.logic.interfaces.JudgeUserLogicIntf;
import ir.shenakht.paint.logic.interfaces.JudgmentLogicIntf;
import ir.shenakht.paint.logic.interfaces.ParticipantsLogicIntf;
import ir.shenakht.paint.logic.interfaces.QuestionLogicIntf;
import ir.shenakht.paint.logic.interfaces.RacingLogicIntf;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author hossien
 */
@Stateless
public class JudgmentLogic implements JudgmentLogicIntf {

    @EJB
    private QuestionLogicIntf ql;

    @EJB
    private RacingLogicIntf rl;

    @EJB
    private JudgeUserLogicIntf jul;

    @EJB
    private ParticipantsLogicIntf pl;

    @EJB
    private JudgmentJpaControllerIntf jj;

    @Override
    public Judgment createJudgment(Question question, Racing racing, JudgeUser judgeUser, Participants participants, Judgment judgment) {
        question = ql.findQuestion(question.getId());
        racing = rl.findRaingWithId(racing.getId());
        judgeUser = jul.findJudgeUserById(judgeUser.getId());
        participants = pl.findParitcipants(participants.getId());
        if (question != null && racing != null && judgeUser != null && participants != null) {
            Judgment judgmentEntered = findJudgmentByRacingAndJudgeUserAndParicipantsAndQuestion(question, racing, judgeUser, participants);
            if (judgmentEntered == null) {
                try {
                    judgment.setJudgeUserId(judgeUser);
                    judgment.setParticipantsId(participants);
                    judgment.setQuestionId(question);
                    judgment.setRacingId(racing);
                    judgment = jj.create(judgment);
                    return judgment;
                } catch (Exception ex) {
                    Logger.getLogger(JudgmentLogic.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                return judgmentEntered;
            }
        }
        return null;
    }

    @Override
    public boolean updateJudgment(Judgment judgment) {
        Judgment judgmentOld = findJudgmentById(judgment.getId());
        if (judgmentOld != null) {
            try {
                if (judgment.getJudgeUserId() != null) {
                    judgmentOld.setJudgeUserId(judgment.getJudgeUserId());
                }
                if (judgment.getParticipantsId() != null) {
                    judgmentOld.setParticipantsId(judgment.getParticipantsId());
                }
                if (judgment.getQuestionId() != null) {
                    judgmentOld.setQuestionId(judgment.getQuestionId());
                }
                if (judgment.getRacingId() != null) {
                    judgmentOld.setRacingId(judgment.getRacingId());
                }
                judgmentOld.setScore(judgment.getScore());
                jj.edit(judgmentOld);
                return true;
            } catch (RollbackFailureException ex) {
                Logger.getLogger(JudgmentLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(JudgmentLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public boolean deleteJudgment(Integer id) {
        Judgment judgment = findJudgmentById(id);
        if (judgment != null) {
            try {
                jj.destroy(id);
                return true;
            } catch (RollbackFailureException ex) {
                Logger.getLogger(JudgmentLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(JudgmentLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public Judgment findJudgmentById(Integer id) {
        return jj.findJudgment(id);
    }

    @Override
    public Judgment findJudgmentByRacingAndJudgeUserAndParicipantsAndQuestion(Question question, Racing racing, JudgeUser judgeUser, Participants participants) {
        List<Judgment> judgments = jj.getEntityManager()
                .createNamedQuery("Judgment.findByJudgeUser&Participants&Question&Racing")
                .setParameter("judgeUser", judgeUser)
                .setParameter("participants", participants)
                .setParameter("question", question)
                .setParameter("racing", racing)
                .getResultList();
        return judgments.isEmpty() ? null : judgments.get(0);
    }

    @Override
    public List<Judgment> findAllJudgment() {
        List<Judgment> judgments = jj.getEntityManager()
                .createNamedQuery("Judgment.findAll").getResultList();
        return judgments.isEmpty() ? null : judgments;
    }

    @Override
    public List<Judgment> findListJudgmentByJudgeUser(JudgeUser judgeUser) {
        List<Judgment> judgments = jj.getEntityManager()
                .createNamedQuery("Judgment.findByJudgeUser")
                .setParameter("judgeUser", judgeUser).getResultList();
        return judgments.isEmpty() ? null : judgments;
    }

    @Override
    public List<Judgment> findListJudgmentByParticipants(Participants participants) {
        List<Judgment> judgments = jj.getEntityManager()
                .createNamedQuery("Judgment.findByParticipants")
                .setParameter("participants", participants).getResultList();
        return judgments.isEmpty() ? null : judgments;
    }

}
