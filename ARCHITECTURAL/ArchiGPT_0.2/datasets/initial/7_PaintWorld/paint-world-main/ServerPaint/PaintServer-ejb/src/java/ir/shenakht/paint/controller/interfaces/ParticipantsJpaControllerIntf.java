/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller.interfaces;

import ir.shenakht.paint.controller.exceptions.IllegalOrphanException;
import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.domain.Participants;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 *
 * @author hossien
 */
@Local
public interface ParticipantsJpaControllerIntf extends Serializable {

    Participants create(Participants participants) throws RollbackFailureException, Exception;

    void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception;

    void edit(Participants participants) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception;

    Participants findParticipants(Integer id);

    List<Participants> findParticipantsEntities();

    List<Participants> findParticipantsEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getParticipantsCount();

}
