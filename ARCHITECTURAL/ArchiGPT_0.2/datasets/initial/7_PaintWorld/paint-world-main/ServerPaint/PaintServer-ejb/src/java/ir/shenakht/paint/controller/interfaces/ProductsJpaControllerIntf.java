/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller.interfaces;

import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.domain.Products;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 *
 * @author hossien
 */
@Local
public interface ProductsJpaControllerIntf extends Serializable {

    Products create(Products products) throws RollbackFailureException, Exception;

    void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception;

    void edit(Products products) throws NonexistentEntityException, RollbackFailureException, Exception;

    Products findProducts(Integer id);

    List<Products> findProductsEntities();

    List<Products> findProductsEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getProductsCount();

}
