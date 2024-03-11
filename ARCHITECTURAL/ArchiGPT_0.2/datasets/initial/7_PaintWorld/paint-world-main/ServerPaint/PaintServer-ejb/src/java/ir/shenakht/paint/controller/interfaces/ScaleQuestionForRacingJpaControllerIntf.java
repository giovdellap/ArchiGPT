/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller.interfaces;

import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.domain.ScaleQuestionForRacing;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 *
 * @author hossien
 */
@Local
public interface ScaleQuestionForRacingJpaControllerIntf extends Serializable {

    ScaleQuestionForRacing create(ScaleQuestionForRacing scaleQuestionForRacing) throws RollbackFailureException, Exception;

    void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception;

    void edit(ScaleQuestionForRacing scaleQuestionForRacing) throws NonexistentEntityException, RollbackFailureException, Exception;

    ScaleQuestionForRacing findScaleQuestionForRacing(Integer id);

    List<ScaleQuestionForRacing> findScaleQuestionForRacingEntities();

    List<ScaleQuestionForRacing> findScaleQuestionForRacingEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getScaleQuestionForRacingCount();

}
