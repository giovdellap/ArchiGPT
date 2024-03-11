/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic.interfaces;

import ir.shenakht.paint.domain.Question;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hossien
 */
@Local
public interface QuestionLogicIntf {

    Question createQuestion(Question question);

    boolean updateQuestion(Question question);

    boolean deleteQuestion(Integer id);

    Question findQuestion(Integer id);

    List<Question> findAllQuestion();

}
