/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic.interfaces;

import ir.shenakht.paint.domain.JudgeUser;
import ir.shenakht.paint.domain.Judgment;
import ir.shenakht.paint.domain.Participants;
import ir.shenakht.paint.domain.Question;
import ir.shenakht.paint.domain.Racing;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hossien
 */
@Local
public interface JudgmentLogicIntf {

    Judgment createJudgment(Question question, Racing racing, JudgeUser judgeUser, Participants participants, Judgment judgment);

    boolean updateJudgment(Judgment judgment);

    boolean deleteJudgment(Integer id);

    Judgment findJudgmentById(Integer id);

    Judgment findJudgmentByRacingAndJudgeUserAndParicipantsAndQuestion(Question question, Racing racing, JudgeUser judgeUser, Participants participants);

    List<Judgment> findAllJudgment();

    List<Judgment> findListJudgmentByJudgeUser(JudgeUser judgeUser);

    List<Judgment> findListJudgmentByParticipants(Participants participants);
}
