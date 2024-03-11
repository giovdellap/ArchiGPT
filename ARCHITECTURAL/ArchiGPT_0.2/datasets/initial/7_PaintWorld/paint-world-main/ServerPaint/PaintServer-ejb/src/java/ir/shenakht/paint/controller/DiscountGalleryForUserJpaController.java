/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller;

import ir.shenakht.paint.controller.interfaces.DiscountGalleryForUserJpaControllerIntf;
import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ir.shenakht.paint.domain.Discount;
import ir.shenakht.paint.domain.DiscountGalleryForUser;
import ir.shenakht.paint.domain.Gallery;
import ir.shenakht.paint.domain.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 *
 * @author hossien
 */
@Stateless
public class DiscountGalleryForUserJpaController implements DiscountGalleryForUserJpaControllerIntf {

    public DiscountGalleryForUserJpaController() {
    }

    @PersistenceContext(unitName = "PSE", type = PersistenceContextType.TRANSACTION)
    private EntityManager emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public DiscountGalleryForUser create(DiscountGalleryForUser discountGalleryForUser) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Discount discountId = discountGalleryForUser.getDiscountId();
            if (discountId != null) {
                discountId = em.getReference(discountId.getClass(), discountId.getId());
                discountGalleryForUser.setDiscountId(discountId);
            }
            Gallery galleryId = discountGalleryForUser.getGalleryId();
            if (galleryId != null) {
                galleryId = em.getReference(galleryId.getClass(), galleryId.getId());
                discountGalleryForUser.setGalleryId(galleryId);
            }
            User userId = discountGalleryForUser.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getId());
                discountGalleryForUser.setUserId(userId);
            }
            em.persist(discountGalleryForUser);
            if (discountId != null) {
                discountId.getDiscountGalleryForUserList().add(discountGalleryForUser);
                discountId = em.merge(discountId);
            }
            if (galleryId != null) {
                galleryId.getDiscountGalleryForUserList().add(discountGalleryForUser);
                galleryId = em.merge(galleryId);
            }
            if (userId != null) {
                userId.getDiscountGalleryForUserList().add(discountGalleryForUser);
                userId = em.merge(userId);
            }
            return discountGalleryForUser;
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
    public void edit(DiscountGalleryForUser discountGalleryForUser) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            DiscountGalleryForUser persistentDiscountGalleryForUser = em.find(DiscountGalleryForUser.class, discountGalleryForUser.getId());
            Discount discountIdOld = persistentDiscountGalleryForUser.getDiscountId();
            Discount discountIdNew = discountGalleryForUser.getDiscountId();
            Gallery galleryIdOld = persistentDiscountGalleryForUser.getGalleryId();
            Gallery galleryIdNew = discountGalleryForUser.getGalleryId();
            User userIdOld = persistentDiscountGalleryForUser.getUserId();
            User userIdNew = discountGalleryForUser.getUserId();
            if (discountIdNew != null) {
                discountIdNew = em.getReference(discountIdNew.getClass(), discountIdNew.getId());
                discountGalleryForUser.setDiscountId(discountIdNew);
            }
            if (galleryIdNew != null) {
                galleryIdNew = em.getReference(galleryIdNew.getClass(), galleryIdNew.getId());
                discountGalleryForUser.setGalleryId(galleryIdNew);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getId());
                discountGalleryForUser.setUserId(userIdNew);
            }
            discountGalleryForUser = em.merge(discountGalleryForUser);
            if (discountIdOld != null && !discountIdOld.equals(discountIdNew)) {
                discountIdOld.getDiscountGalleryForUserList().remove(discountGalleryForUser);
                discountIdOld = em.merge(discountIdOld);
            }
            if (discountIdNew != null && !discountIdNew.equals(discountIdOld)) {
                discountIdNew.getDiscountGalleryForUserList().add(discountGalleryForUser);
                discountIdNew = em.merge(discountIdNew);
            }
            if (galleryIdOld != null && !galleryIdOld.equals(galleryIdNew)) {
                galleryIdOld.getDiscountGalleryForUserList().remove(discountGalleryForUser);
                galleryIdOld = em.merge(galleryIdOld);
            }
            if (galleryIdNew != null && !galleryIdNew.equals(galleryIdOld)) {
                galleryIdNew.getDiscountGalleryForUserList().add(discountGalleryForUser);
                galleryIdNew = em.merge(galleryIdNew);
            }
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getDiscountGalleryForUserList().remove(discountGalleryForUser);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getDiscountGalleryForUserList().add(discountGalleryForUser);
                userIdNew = em.merge(userIdNew);
            }
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = discountGalleryForUser.getId();
                if (findDiscountGalleryForUser(id) == null) {
                    throw new NonexistentEntityException("The discountGalleryForUser with id " + id + " no longer exists.");
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
            DiscountGalleryForUser discountGalleryForUser;
            try {
                discountGalleryForUser = em.getReference(DiscountGalleryForUser.class, id);
                discountGalleryForUser.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The discountGalleryForUser with id " + id + " no longer exists.", enfe);
            }
            Discount discountId = discountGalleryForUser.getDiscountId();
            if (discountId != null) {
                discountId.getDiscountGalleryForUserList().remove(discountGalleryForUser);
                discountId = em.merge(discountId);
            }
            Gallery galleryId = discountGalleryForUser.getGalleryId();
            if (galleryId != null) {
                galleryId.getDiscountGalleryForUserList().remove(discountGalleryForUser);
                galleryId = em.merge(galleryId);
            }
            User userId = discountGalleryForUser.getUserId();
            if (userId != null) {
                userId.getDiscountGalleryForUserList().remove(discountGalleryForUser);
                userId = em.merge(userId);
            }
            em.remove(discountGalleryForUser);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.flush();
            }
        }
    }

    @Override
    public List<DiscountGalleryForUser> findDiscountGalleryForUserEntities() {
        return findDiscountGalleryForUserEntities(true, -1, -1);
    }

    @Override
    public List<DiscountGalleryForUser> findDiscountGalleryForUserEntities(int maxResults, int firstResult) {
        return findDiscountGalleryForUserEntities(false, maxResults, firstResult);
    }

    private List<DiscountGalleryForUser> findDiscountGalleryForUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DiscountGalleryForUser.class));
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
    public DiscountGalleryForUser findDiscountGalleryForUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DiscountGalleryForUser.class, id);
        } finally {
            em.flush();
        }
    }

    @Override
    public int getDiscountGalleryForUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DiscountGalleryForUser> rt = cq.from(DiscountGalleryForUser.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.flush();
        }
    }

}
