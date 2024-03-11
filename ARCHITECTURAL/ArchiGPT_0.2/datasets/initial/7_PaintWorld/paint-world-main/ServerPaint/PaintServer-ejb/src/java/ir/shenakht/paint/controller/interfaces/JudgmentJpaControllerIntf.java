/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller.interfaces;

import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.domain.Judgment;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 *
 * @author hossien
 */
@Local
public interface JudgmentJpaControllerIntf extends Serializable {

    Judgment create(Judgment judgment) throws RollbackFailureException, Exception;

    void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception;

    void edit(Judgment judgment) throws NonexistentEntityException, RollbackFailureException, Exception;

    Judgment findJudgment(Integer id);

    List<Judgment> findJudgmentEntities();

    List<Judgment> findJudgmentEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getJudgmentCount();

}
