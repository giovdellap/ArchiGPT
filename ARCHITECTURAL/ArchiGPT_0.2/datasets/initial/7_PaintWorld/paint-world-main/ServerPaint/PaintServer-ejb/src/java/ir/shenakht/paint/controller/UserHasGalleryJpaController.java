/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller;

import ir.shenakht.paint.controller.interfaces.UserHasGalleryJpaControllerIntf;
import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ir.shenakht.paint.domain.Gallery;
import ir.shenakht.paint.domain.User;
import ir.shenakht.paint.domain.UserHasGallery;
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
public class UserHasGalleryJpaController implements UserHasGalleryJpaControllerIntf {

    public UserHasGalleryJpaController() {
    }

    @PersistenceContext(unitName = "PSE", type = PersistenceContextType.TRANSACTION)
    private EntityManager emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public UserHasGallery create(UserHasGallery userHasGallery) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Gallery galleryId = userHasGallery.getGalleryId();
            if (galleryId != null) {
                galleryId = em.getReference(galleryId.getClass(), galleryId.getId());
                userHasGallery.setGalleryId(galleryId);
            }
            User userId = userHasGallery.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getId());
                userHasGallery.setUserId(userId);
            }
            em.persist(userHasGallery);
            if (galleryId != null) {
                galleryId.getUserHasGalleryList().add(userHasGallery);
                galleryId = em.merge(galleryId);
            }
            if (userId != null) {
                userId.getUserHasGalleryList().add(userHasGallery);
                userId = em.merge(userId);
            }
            return userHasGallery;
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
    public void edit(UserHasGallery userHasGallery) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            UserHasGallery persistentUserHasGallery = em.find(UserHasGallery.class, userHasGallery.getId());
            Gallery galleryIdOld = persistentUserHasGallery.getGalleryId();
            Gallery galleryIdNew = userHasGallery.getGalleryId();
            User userIdOld = persistentUserHasGallery.getUserId();
            User userIdNew = userHasGallery.getUserId();
            if (galleryIdNew != null) {
                galleryIdNew = em.getReference(galleryIdNew.getClass(), galleryIdNew.getId());
                userHasGallery.setGalleryId(galleryIdNew);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getId());
                userHasGallery.setUserId(userIdNew);
            }
            userHasGallery = em.merge(userHasGallery);
            if (galleryIdOld != null && !galleryIdOld.equals(galleryIdNew)) {
                galleryIdOld.getUserHasGalleryList().remove(userHasGallery);
                galleryIdOld = em.merge(galleryIdOld);
            }
            if (galleryIdNew != null && !galleryIdNew.equals(galleryIdOld)) {
                galleryIdNew.getUserHasGalleryList().add(userHasGallery);
                galleryIdNew = em.merge(galleryIdNew);
            }
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getUserHasGalleryList().remove(userHasGallery);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getUserHasGalleryList().add(userHasGallery);
                userIdNew = em.merge(userIdNew);
            }
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = userHasGallery.getId();
                if (findUserHasGallery(id) == null) {
                    throw new NonexistentEntityException("The userHasGallery with id " + id + " no longer exists.");
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
            UserHasGallery userHasGallery;
            try {
                userHasGallery = em.getReference(UserHasGallery.class, id);
                userHasGallery.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userHasGallery with id " + id + " no longer exists.", enfe);
            }
            Gallery galleryId = userHasGallery.getGalleryId();
            if (galleryId != null) {
                galleryId.getUserHasGalleryList().remove(userHasGallery);
                galleryId = em.merge(galleryId);
            }
            User userId = userHasGallery.getUserId();
            if (userId != null) {
                userId.getUserHasGalleryList().remove(userHasGallery);
                userId = em.merge(userId);
            }
            em.remove(userHasGallery);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.flush();
            }
        }
    }

    @Override
    public List<UserHasGallery> findUserHasGalleryEntities() {
        return findUserHasGalleryEntities(true, -1, -1);
    }

    @Override
    public List<UserHasGallery> findUserHasGalleryEntities(int maxResults, int firstResult) {
        return findUserHasGalleryEntities(false, maxResults, firstResult);
    }

    private List<UserHasGallery> findUserHasGalleryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserHasGallery.class));
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
    public UserHasGallery findUserHasGallery(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserHasGallery.class, id);
        } finally {
            em.flush();
        }
    }

    @Override
    public int getUserHasGalleryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserHasGallery> rt = cq.from(UserHasGallery.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.flush();
        }
    }

}
