/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller;

import ir.shenakht.paint.controller.interfaces.PictureJpacControllerIntf;
import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ir.shenakht.paint.domain.Gallery;
import ir.shenakht.paint.domain.Picture;
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
public class PictureJpaController implements PictureJpacControllerIntf {

    public PictureJpaController() {
    }

    @PersistenceContext(unitName = "PSE", type = PersistenceContextType.TRANSACTION)
    private EntityManager emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public Picture create(Picture picture) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Gallery galleryId = picture.getGalleryId();
            if (galleryId != null) {
                galleryId = em.getReference(galleryId.getClass(), galleryId.getId());
                picture.setGalleryId(galleryId);
            }
            System.out.println("picture.getGalleryId()  " + picture.getGalleryId());
            System.out.println("**************************");
            System.out.println("picture.getCreateAt().toString()   " + picture.getCreateAt().toString());
            System.out.println("**************************");
//            System.out.println(picture.getId());
            System.out.println("**************************");
            System.out.println("picture.getTitle()   " + picture.getTitle());
            System.out.println("**************************");
            System.out.println("picture.getUpdateAt().toString()   " + picture.getUpdateAt().toString());
            System.out.println("**************************");
            System.out.println("picture.getUrlMain()    " + picture.getUrlMain());
            System.out.println("**************************");
            System.out.println("picture.getUrlThumbnail()   " + picture.getUrlThumbnail());
            System.out.println("**************************");
            em.persist(picture);
            if (galleryId != null) {
                galleryId.getPictureList().add(picture);
                galleryId = em.merge(galleryId);
            }
            return picture;
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
    public void edit(Picture picture) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Picture persistentPicture = em.find(Picture.class, picture.getId());
            Gallery galleryIdOld = persistentPicture.getGalleryId();
            Gallery galleryIdNew = picture.getGalleryId();
            if (galleryIdNew != null) {
                galleryIdNew = em.getReference(galleryIdNew.getClass(), galleryIdNew.getId());
                picture.setGalleryId(galleryIdNew);
            }
            picture = em.merge(picture);
            if (galleryIdOld != null && !galleryIdOld.equals(galleryIdNew)) {
                galleryIdOld.getPictureList().remove(picture);
                galleryIdOld = em.merge(galleryIdOld);
            }
            if (galleryIdNew != null && !galleryIdNew.equals(galleryIdOld)) {
                galleryIdNew.getPictureList().add(picture);
                galleryIdNew = em.merge(galleryIdNew);
            }
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = picture.getId();
                if (findPicture(id) == null) {
                    throw new NonexistentEntityException("The picture with id " + id + " no longer exists.");
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
            Picture picture;
            try {
                picture = em.getReference(Picture.class, id);
                picture.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The picture with id " + id + " no longer exists.", enfe);
            }
            Gallery galleryId = picture.getGalleryId();
            if (galleryId != null) {
                galleryId.getPictureList().remove(picture);
                galleryId = em.merge(galleryId);
            }
            em.remove(picture);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.flush();
            }
        }
    }

    @Override
    public List<Picture> findPictureEntities() {
        return findPictureEntities(true, -1, -1);
    }

    @Override
    public List<Picture> findPictureEntities(int maxResults, int firstResult) {
        return findPictureEntities(false, maxResults, firstResult);
    }

    private List<Picture> findPictureEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Picture.class));
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
    public Picture findPicture(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Picture.class, id);
        } finally {
            em.flush();
        }
    }

    @Override
    public int getPictureCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Picture> rt = cq.from(Picture.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.flush();
        }
    }

}
