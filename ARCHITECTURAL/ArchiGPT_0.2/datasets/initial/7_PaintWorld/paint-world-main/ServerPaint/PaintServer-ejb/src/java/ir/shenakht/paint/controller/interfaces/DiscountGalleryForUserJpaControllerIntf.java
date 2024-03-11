/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller.interfaces;

import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.domain.DiscountGalleryForUser;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 *
 * @author hossien
 */
@Local
public interface DiscountGalleryForUserJpaControllerIntf extends Serializable {

    DiscountGalleryForUser create(DiscountGalleryForUser discountGalleryForUser) throws RollbackFailureException, Exception;

    void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception;

    void edit(DiscountGalleryForUser discountGalleryForUser) throws NonexistentEntityException, RollbackFailureException, Exception;

    DiscountGalleryForUser findDiscountGalleryForUser(Integer id);

    List<DiscountGalleryForUser> findDiscountGalleryForUserEntities();

    List<DiscountGalleryForUser> findDiscountGalleryForUserEntities(int maxResults, int firstResult);

    int getDiscountGalleryForUserCount();

    EntityManager getEntityManager();

}
