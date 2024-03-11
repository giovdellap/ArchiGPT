/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller.interfaces;

import ir.shenakht.paint.controller.exceptions.IllegalOrphanException;
import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.PreexistingEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.domain.Question;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 *
 * @author hossien
 */
@Local
public interface QuestionJpaControllerIntf extends Serializable {

    Question create(Question question) throws PreexistingEntityException, RollbackFailureException, Exception;

    void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception;

    void edit(Question question) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception;

    Question findQuestion(Integer id);

    List<Question> findQuestionEntities();

    List<Question> findQuestionEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getQuestionCount();
    
}
