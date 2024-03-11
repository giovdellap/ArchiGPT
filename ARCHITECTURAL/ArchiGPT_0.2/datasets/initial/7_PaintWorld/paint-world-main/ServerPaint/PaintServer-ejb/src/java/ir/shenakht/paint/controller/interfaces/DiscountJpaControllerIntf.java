/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller.interfaces;

import ir.shenakht.paint.controller.exceptions.IllegalOrphanException;
import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.domain.Discount;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 *
 * @author hossien
 */
@Local
public interface DiscountJpaControllerIntf extends Serializable {

    Discount create(Discount discount) throws RollbackFailureException, Exception;

    void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception;

    void edit(Discount discount) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception;

    Discount findDiscount(Integer id);

    List<Discount> findDiscountEntities();

    List<Discount> findDiscountEntities(int maxResults, int firstResult);

    int getDiscountCount();

    EntityManager getEntityManager();

}
