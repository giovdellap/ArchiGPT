/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic;

import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.controller.interfaces.QuestionJpaControllerIntf;
import ir.shenakht.paint.domain.Question;
import ir.shenakht.paint.logic.interfaces.QuestionLogicIntf;
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

public class QuestionLogic implements QuestionLogicIntf {

    @EJB
    private QuestionJpaControllerIntf qj;

    @Override
    public Question createQuestion(Question question) {
        try {
            question = qj.create(question);
            return question;
        } catch (RollbackFailureException ex) {
            Logger.getLogger(QuestionLogic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(QuestionLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean updateQuestion(Question question) {
        Question questionOld = findQuestion(question.getId());
        if (questionOld != null) {
            try {
                if (question.getText() != null) {
                    questionOld.setText(question.getText());
                }
                qj.edit(questionOld);
                return true;
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(QuestionLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RollbackFailureException ex) {
                Logger.getLogger(QuestionLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(QuestionLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public boolean deleteQuestion(Integer id) {
        Question question = findQuestion(id);
        if (question != null) {
            try {
                qj.destroy(id);
                return true;
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(QuestionLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RollbackFailureException ex) {
                Logger.getLogger(QuestionLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(QuestionLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public Question findQuestion(Integer id) {
        return qj.findQuestion(id);
    }

    @Override
    public List<Question> findAllQuestion() {
        List<Question> questions = qj.getEntityManager()
                .createNamedQuery("Question.findAll").getResultList();
        return questions.isEmpty() ? null : questions;
    }

}
