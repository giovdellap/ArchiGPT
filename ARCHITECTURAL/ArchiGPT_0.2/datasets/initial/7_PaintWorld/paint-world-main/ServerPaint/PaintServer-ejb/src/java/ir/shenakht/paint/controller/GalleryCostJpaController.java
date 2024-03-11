/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller;

import ir.shenakht.paint.controller.interfaces.GalleryCostJpaControllerIntf;
import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ir.shenakht.paint.domain.Gallery;
import ir.shenakht.paint.domain.GalleryCost;
import java.util.ArrayList;
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
public class GalleryCostJpaController implements GalleryCostJpaControllerIntf {

    public GalleryCostJpaController() {
    }

    @PersistenceContext(unitName = "PSE", type = PersistenceContextType.TRANSACTION)
    private EntityManager emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public GalleryCost create(GalleryCost galleryCost) throws RollbackFailureException, Exception {
        if (galleryCost.getGalleryList() == null) {
            galleryCost.setGalleryList(new ArrayList<Gallery>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            List<Gallery> attachedGalleryList = new ArrayList<Gallery>();
            for (Gallery galleryListGalleryToAttach : galleryCost.getGalleryList()) {
                galleryListGalleryToAttach = em.getReference(galleryListGalleryToAttach.getClass(), galleryListGalleryToAttach.getId());
                attachedGalleryList.add(galleryListGalleryToAttach);
            }
            galleryCost.setGalleryList(attachedGalleryList);
            em.persist(galleryCost);
            for (Gallery galleryListGallery : galleryCost.getGalleryList()) {
                galleryListGallery.getGalleryCostList().add(galleryCost);
                galleryListGallery = em.merge(galleryListGallery);
            }
            return galleryCost;
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
    public void edit(GalleryCost galleryCost) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            GalleryCost persistentGalleryCost = em.find(GalleryCost.class, galleryCost.getId());
            List<Gallery> galleryListOld = persistentGalleryCost.getGalleryList();
            List<Gallery> galleryListNew = galleryCost.getGalleryList();
            List<Gallery> attachedGalleryListNew = new ArrayList<Gallery>();
            for (Gallery galleryListNewGalleryToAttach : galleryListNew) {
                galleryListNewGalleryToAttach = em.getReference(galleryListNewGalleryToAttach.getClass(), galleryListNewGalleryToAttach.getId());
                attachedGalleryListNew.add(galleryListNewGalleryToAttach);
            }
            galleryListNew = attachedGalleryListNew;
            galleryCost.setGalleryList(galleryListNew);
            galleryCost = em.merge(galleryCost);
            for (Gallery galleryListOldGallery : galleryListOld) {
                if (!galleryListNew.contains(galleryListOldGallery)) {
                    galleryListOldGallery.getGalleryCostList().remove(galleryCost);
                    galleryListOldGallery = em.merge(galleryListOldGallery);
                }
            }
            for (Gallery galleryListNewGallery : galleryListNew) {
                if (!galleryListOld.contains(galleryListNewGallery)) {
                    galleryListNewGallery.getGalleryCostList().add(galleryCost);
                    galleryListNewGallery = em.merge(galleryListNewGallery);
                }
            }
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = galleryCost.getId();
                if (findGalleryCost(id) == null) {
                    throw new NonexistentEntityException("The galleryCost with id " + id + " no longer exists.");
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
            GalleryCost galleryCost;
            try {
                galleryCost = em.getReference(GalleryCost.class, id);
                galleryCost.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The galleryCost with id " + id + " no longer exists.", enfe);
            }
            List<Gallery> galleryList = galleryCost.getGalleryList();
            for (Gallery galleryListGallery : galleryList) {
                galleryListGallery.getGalleryCostList().remove(galleryCost);
                galleryListGallery = em.merge(galleryListGallery);
            }
            em.remove(galleryCost);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.flush();
            }
        }
    }

    @Override
    public List<GalleryCost> findGalleryCostEntities() {
        return findGalleryCostEntities(true, -1, -1);
    }

    @Override
    public List<GalleryCost> findGalleryCostEntities(int maxResults, int firstResult) {
        return findGalleryCostEntities(false, maxResults, firstResult);
    }

    private List<GalleryCost> findGalleryCostEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GalleryCost.class));
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
    public GalleryCost findGalleryCost(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GalleryCost.class, id);
        } finally {
            em.flush();
        }
    }

    @Override
    public int getGalleryCostCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GalleryCost> rt = cq.from(GalleryCost.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.flush();
        }
    }

}
