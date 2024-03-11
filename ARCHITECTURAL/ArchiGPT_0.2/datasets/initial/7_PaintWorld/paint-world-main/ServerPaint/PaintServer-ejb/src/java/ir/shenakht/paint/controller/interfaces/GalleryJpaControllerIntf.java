/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller.interfaces;

import ir.shenakht.paint.controller.exceptions.IllegalOrphanException;
import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.domain.Gallery;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 *
 * @author hossien
 */
@Local
public interface GalleryJpaControllerIntf extends Serializable {

    Gallery create(Gallery gallery) throws RollbackFailureException, Exception;

    void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception;

    void edit(Gallery gallery) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception;

    Gallery findGallery(Integer id);

    List<Gallery> findGalleryEntities();

    List<Gallery> findGalleryEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getGalleryCount();

}
