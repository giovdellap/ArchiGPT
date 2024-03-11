/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic.interfaces;

import ir.shenakht.paint.domain.Question;
import ir.shenakht.paint.domain.Racing;
import ir.shenakht.paint.domain.ScaleQuestionForRacing;
import javax.ejb.Local;

/**
 *
 * @author hossien
 */
@Local
public interface ScaleQuestionForRacingLogicIntf {

    ScaleQuestionForRacing createScaleQuestionForRacing(Racing racing, Question question, ScaleQuestionForRacing scaleQuestionForRacing);

    boolean updateScaleQuestionForRacing(Racing racing, Question question, ScaleQuestionForRacing scaleQuestionForRacing);

    boolean deleteScaleQuestionForRacing(Integer id);

    ScaleQuestionForRacing findScaleQuestionForRacing(Integer id);

}
