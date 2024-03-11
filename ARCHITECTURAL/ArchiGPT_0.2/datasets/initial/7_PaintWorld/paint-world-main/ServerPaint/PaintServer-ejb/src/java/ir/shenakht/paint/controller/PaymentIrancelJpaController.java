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
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ir.shenakht.paint.domain.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.UserTransaction;

/**
 *
 * @author hossien
 */
@Stateless
public class PaymentIrancelJpaController implements PaymentIrancelJpaControllerIntf {

    public PaymentIrancelJpaController() {

    }
    
    @PersistenceContext(unitName = "PSE",type = PersistenceContextType.TRANSACTION)
    private EntityManager emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public PaymentIrancel create(PaymentIrancel paymentIrancel) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            User userId = paymentIrancel.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getId());
                paymentIrancel.setUserId(userId);
            }
            em.persist(paymentIrancel);
            if (userId != null) {
                userId.getPaymentIrancelList().add(paymentIrancel);
                userId = em.merge(userId);
            }
            return paymentIrancel;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.flush();
            }
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void edit(PaymentIrancel paymentIrancel) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            PaymentIrancel persistentPaymentIrancel = em.find(PaymentIrancel.class, paymentIrancel.getId());
            User userIdOld = persistentPaymentIrancel.getUserId();
            User userIdNew = paymentIrancel.getUserId();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getId());
                paymentIrancel.setUserId(userIdNew);
            }
            paymentIrancel = em.merge(paymentIrancel);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getPaymentIrancelList().remove(paymentIrancel);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getPaymentIrancelList().add(paymentIrancel);
                userIdNew = em.merge(userIdNew);
            }
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = paymentIrancel.getId();
                if (findPaymentIrancel(id) == null) {
                    throw new NonexistentEntityException("The paymentIrancel with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.flush();
            }
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            PaymentIrancel paymentIrancel;
            try {
                paymentIrancel = em.getReference(PaymentIrancel.class, id);
                paymentIrancel.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The paymentIrancel with id " + id + " no longer exists.", enfe);
            }
            User userId = paymentIrancel.getUserId();
            if (userId != null) {
                userId.getPaymentIrancelList().remove(paymentIrancel);
                userId = em.merge(userId);
            }
            em.remove(paymentIrancel);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.flush();
            }
        }
    }

    @Override
    public List<PaymentIrancel> findPaymentIrancelEntities() {
        return findPaymentIrancelEntities(true, -1, -1);
    }

    @Override
    public List<PaymentIrancel> findPaymentIrancelEntities(int maxResults, int firstResult) {
        return findPaymentIrancelEntities(false, maxResults, firstResult);
    }

    private List<PaymentIrancel> findPaymentIrancelEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PaymentIrancel.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.flush();
        }
    }

    @Override
    public PaymentIrancel findPaymentIrancel(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PaymentIrancel.class, id);
        } finally {
            em.flush();
        }
    }

    @Override
    public int getPaymentIrancelCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PaymentIrancel> rt = cq.from(PaymentIrancel.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.flush();
        }
    }

}
