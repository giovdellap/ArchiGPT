/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller.interfaces;

import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.domain.Picture;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 *
 * @author hossien
 */
@Local
public interface PictureJpacControllerIntf extends Serializable {

    Picture create(Picture picture) throws RollbackFailureException, Exception;

    void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception;

    void edit(Picture picture) throws NonexistentEntityException, RollbackFailureException, Exception;

    Picture findPicture(Integer id);

    List<Picture> findPictureEntities();

    List<Picture> findPictureEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getPictureCount();

}
