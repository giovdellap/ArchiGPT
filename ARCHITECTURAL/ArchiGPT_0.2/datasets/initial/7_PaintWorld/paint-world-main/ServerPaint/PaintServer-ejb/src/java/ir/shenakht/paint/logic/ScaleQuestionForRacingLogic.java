/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic;

import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.controller.interfaces.ScaleQuestionForRacingJpaControllerIntf;
import ir.shenakht.paint.domain.Question;
import ir.shenakht.paint.domain.Racing;
import ir.shenakht.paint.domain.ScaleQuestionForRacing;
import ir.shenakht.paint.logic.interfaces.QuestionLogicIntf;
import ir.shenakht.paint.logic.interfaces.RacingLogicIntf;
import ir.shenakht.paint.logic.interfaces.ScaleQuestionForRacingLogicIntf;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author hossien
 */
@Stateless
public class ScaleQuestionForRacingLogic implements ScaleQuestionForRacingLogicIntf {

    @EJB
    private RacingLogicIntf rl;

    @EJB
    private QuestionLogicIntf ql;

    @EJB
    private ScaleQuestionForRacingJpaControllerIntf sqrj;

    @Override
    public ScaleQuestionForRacing createScaleQuestionForRacing(Racing racing, Question question, ScaleQuestionForRacing scaleQuestionForRacing) {
        racing = rl.findRaingWithId(racing.getId());
        question = ql.findQuestion(question.getId());
        if (racing != null && question != null) {
            try {
                scaleQuestionForRacing.setQuestionId(question);
                scaleQuestionForRacing.setRacingId(racing);
                scaleQuestionForRacing = sqrj.create(scaleQuestionForRacing);
                return scaleQuestionForRacing;
            } catch (Exception ex) {
                Logger.getLogger(ScaleQuestionForRacingLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public boolean updateScaleQuestionForRacing(Racing racing, Question question, ScaleQuestionForRacing scaleQuestionForRacing) {
        ScaleQuestionForRacing sqfrOld = findScaleQuestionForRacing(scaleQuestionForRacing.getId());
        if (sqfrOld != null) {
            try {
                if (scaleQuestionForRacing.getScale() != null) {
                    sqfrOld.setScale(scaleQuestionForRacing.getScale());
                }
                if (scaleQuestionForRacing.getQuestionId() != null) {
                    Question q = ql.findQuestion(scaleQuestionForRacing.getQuestionId().getId());
                    if (q != null) {
                        sqfrOld.setQuestionId(q);
                    }
                }
                if (scaleQuestionForRacing.getRacingId() != null) {
                    Racing r = rl.findRaingWithId(scaleQuestionForRacing.getRacingId().getId());
                    if (r != null) {
                        sqfrOld.setRacingId(r);
                    }
                }
                sqrj.edit(sqfrOld);
                return true;
            } catch (RollbackFailureException ex) {
                Logger.getLogger(ScaleQuestionForRacingLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ScaleQuestionForRacingLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public boolean deleteScaleQuestionForRacing(Integer id) {
        ScaleQuestionForRacing sqfr = findScaleQuestionForRacing(id);
        if (sqfr != null) {
            try {
                sqrj.destroy(id);
                return true;
            } catch (RollbackFailureException ex) {
                Logger.getLogger(ScaleQuestionForRacingLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ScaleQuestionForRacingLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public ScaleQuestionForRacing findScaleQuestionForRacing(Integer id) {
        return sqrj.findScaleQuestionForRacing(id);
    }

}
