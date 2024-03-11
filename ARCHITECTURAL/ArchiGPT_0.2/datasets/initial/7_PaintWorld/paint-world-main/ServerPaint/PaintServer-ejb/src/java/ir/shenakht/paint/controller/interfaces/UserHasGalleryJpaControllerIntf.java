/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller.interfaces;

import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.domain.UserHasGallery;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 *
 * @author hossien
 */
@Local
public interface UserHasGalleryJpaControllerIntf extends Serializable {

    UserHasGallery create(UserHasGallery userHasGallery) throws RollbackFailureException, Exception;

    void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception;

    void edit(UserHasGallery userHasGallery) throws NonexistentEntityException, RollbackFailureException, Exception;

    UserHasGallery findUserHasGallery(Integer id);

    List<UserHasGallery> findUserHasGalleryEntities();

    List<UserHasGallery> findUserHasGalleryEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getUserHasGalleryCount();

}
