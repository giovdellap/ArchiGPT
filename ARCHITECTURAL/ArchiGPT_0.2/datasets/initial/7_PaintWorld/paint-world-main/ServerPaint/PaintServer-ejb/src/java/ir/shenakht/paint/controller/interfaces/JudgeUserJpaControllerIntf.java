/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller.interfaces;

import ir.shenakht.paint.controller.exceptions.IllegalOrphanException;
import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.domain.JudgeUser;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 *
 * @author hossien
 */
@Local
public interface JudgeUserJpaControllerIntf extends Serializable {

    JudgeUser create(JudgeUser judgeUser) throws RollbackFailureException, Exception;

    void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception;

    void edit(JudgeUser judgeUser) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception;

    JudgeUser findJudgeUser(Integer id);

    List<JudgeUser> findJudgeUserEntities();

    List<JudgeUser> findJudgeUserEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getJudgeUserCount();

}
