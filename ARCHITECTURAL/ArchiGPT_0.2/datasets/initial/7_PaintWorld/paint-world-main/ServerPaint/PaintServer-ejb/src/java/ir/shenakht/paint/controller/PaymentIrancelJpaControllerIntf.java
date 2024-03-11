/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller;

import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.domain.PaymentIrancel;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 *
 * @author hossien
 */
@Local
public interface PaymentIrancelJpaControllerIntf extends Serializable {

    PaymentIrancel create(PaymentIrancel paymentIrancel) throws RollbackFailureException, Exception;

    void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception;

    void edit(PaymentIrancel paymentIrancel) throws NonexistentEntityException, RollbackFailureException, Exception;

    PaymentIrancel findPaymentIrancel(Integer id);

    List<PaymentIrancel> findPaymentIrancelEntities();

    List<PaymentIrancel> findPaymentIrancelEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getPaymentIrancelCount();
    
}
