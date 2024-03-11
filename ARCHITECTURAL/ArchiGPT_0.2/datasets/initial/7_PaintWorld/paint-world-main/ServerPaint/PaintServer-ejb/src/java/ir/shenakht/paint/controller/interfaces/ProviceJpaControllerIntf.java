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
import ir.shenakht.paint.domain.Provice;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 *
 * @author hossien
 */
@Local
public interface ProviceJpaControllerIntf extends Serializable {

    Provice create(Provice provice) throws PreexistingEntityException, RollbackFailureException, Exception;

    void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception;

    void edit(Provice provice) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception;

    Provice findProvice(Integer id);

    List<Provice> findProviceEntities();

    List<Provice> findProviceEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getProviceCount();

}
