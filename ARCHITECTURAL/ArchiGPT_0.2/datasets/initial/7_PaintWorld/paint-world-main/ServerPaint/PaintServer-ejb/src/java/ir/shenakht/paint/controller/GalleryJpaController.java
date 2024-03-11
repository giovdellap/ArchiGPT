/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller;

import ir.shenakht.paint.controller.interfaces.GalleryJpaControllerIntf;
import ir.shenakht.paint.controller.exceptions.IllegalOrphanException;
import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ir.shenakht.paint.domain.GalleryCost;
import java.util.ArrayList;
import java.util.List;
import ir.shenakht.paint.domain.Picture;
import ir.shenakht.paint.domain.UserHasGallery;
import ir.shenakht.paint.domain.DiscountGalleryForUser;
import ir.shenakht.paint.domain.Gallery;
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
public class GalleryJpaController implements GalleryJpaControllerIntf {

    public GalleryJpaController() {
    }

    @PersistenceContext(unitName = "PSE", type = PersistenceContextType.TRANSACTION)
    private EntityManager emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public Gallery create(Gallery gallery) throws RollbackFailureException, Exception {
        if (gallery.getGalleryCostList() == null) {
            gallery.setGalleryCostList(new ArrayList<GalleryCost>());
        }
        if (gallery.getPictureList() == null) {
            gallery.setPictureList(new ArrayList<Picture>());
        }
        if (gallery.getUserHasGalleryList() == null) {
            gallery.setUserHasGalleryList(new ArrayList<UserHasGallery>());
        }
        if (gallery.getDiscountGalleryForUserList() == null) {
            gallery.setDiscountGalleryForUserList(new ArrayList<DiscountGalleryForUser>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            List<GalleryCost> attachedGalleryCostList = new ArrayList<GalleryCost>();
            for (GalleryCost galleryCostListGalleryCostToAttach : gallery.getGalleryCostList()) {
                galleryCostListGalleryCostToAttach = em.getReference(galleryCostListGalleryCostToAttach.getClass(), galleryCostListGalleryCostToAttach.getId());
                attachedGalleryCostList.add(galleryCostListGalleryCostToAttach);
            }
            gallery.setGalleryCostList(attachedGalleryCostList);
            List<Picture> attachedPictureList = new ArrayList<Picture>();
            for (Picture pictureListPictureToAttach : gallery.getPictureList()) {
                pictureListPictureToAttach = em.getReference(pictureListPictureToAttach.getClass(), pictureListPictureToAttach.getId());
                attachedPictureList.add(pictureListPictureToAttach);
            }
            gallery.setPictureList(attachedPictureList);
            List<UserHasGallery> attachedUserHasGalleryList = new ArrayList<UserHasGallery>();
            for (UserHasGallery userHasGalleryListUserHasGalleryToAttach : gallery.getUserHasGalleryList()) {
                userHasGalleryListUserHasGalleryToAttach = em.getReference(userHasGalleryListUserHasGalleryToAttach.getClass(), userHasGalleryListUserHasGalleryToAttach.getId());
                attachedUserHasGalleryList.add(userHasGalleryListUserHasGalleryToAttach);
            }
            gallery.setUserHasGalleryList(attachedUserHasGalleryList);
            List<DiscountGalleryForUser> attachedDiscountGalleryForUserList = new ArrayList<DiscountGalleryForUser>();
            for (DiscountGalleryForUser discountGalleryForUserListDiscountGalleryForUserToAttach : gallery.getDiscountGalleryForUserList()) {
                discountGalleryForUserListDiscountGalleryForUserToAttach = em.getReference(discountGalleryForUserListDiscountGalleryForUserToAttach.getClass(), discountGalleryForUserListDiscountGalleryForUserToAttach.getId());
                attachedDiscountGalleryForUserList.add(discountGalleryForUserListDiscountGalleryForUserToAttach);
            }
            gallery.setDiscountGalleryForUserList(attachedDiscountGalleryForUserList);
            em.persist(gallery);
            for (GalleryCost galleryCostListGalleryCost : gallery.getGalleryCostList()) {
                galleryCostListGalleryCost.getGalleryList().add(gallery);
                galleryCostListGalleryCost = em.merge(galleryCostListGalleryCost);
            }
            for (Picture pictureListPicture : gallery.getPictureList()) {
                Gallery oldGalleryIdOfPictureListPicture = pictureListPicture.getGalleryId();
                pictureListPicture.setGalleryId(gallery);
                pictureListPicture = em.merge(pictureListPicture);
                if (oldGalleryIdOfPictureListPicture != null) {
                    oldGalleryIdOfPictureListPicture.getPictureList().remove(pictureListPicture);
                    oldGalleryIdOfPictureListPicture = em.merge(oldGalleryIdOfPictureListPicture);
                }
            }
            for (UserHasGallery userHasGalleryListUserHasGallery : gallery.getUserHasGalleryList()) {
                Gallery oldGalleryIdOfUserHasGalleryListUserHasGallery = userHasGalleryListUserHasGallery.getGalleryId();
                userHasGalleryListUserHasGallery.setGalleryId(gallery);
                userHasGalleryListUserHasGallery = em.merge(userHasGalleryListUserHasGallery);
                if (oldGalleryIdOfUserHasGalleryListUserHasGallery != null) {
                    oldGalleryIdOfUserHasGalleryListUserHasGallery.getUserHasGalleryList().remove(userHasGalleryListUserHasGallery);
                    oldGalleryIdOfUserHasGalleryListUserHasGallery = em.merge(oldGalleryIdOfUserHasGalleryListUserHasGallery);
                }
            }
            for (DiscountGalleryForUser discountGalleryForUserListDiscountGalleryForUser : gallery.getDiscountGalleryForUserList()) {
                Gallery oldGalleryIdOfDiscountGalleryForUserListDiscountGalleryForUser = discountGalleryForUserListDiscountGalleryForUser.getGalleryId();
                discountGalleryForUserListDiscountGalleryForUser.setGalleryId(gallery);
                discountGalleryForUserListDiscountGalleryForUser = em.merge(discountGalleryForUserListDiscountGalleryForUser);
                if (oldGalleryIdOfDiscountGalleryForUserListDiscountGalleryForUser != null) {
                    oldGalleryIdOfDiscountGalleryForUserListDiscountGalleryForUser.getDiscountGalleryForUserList().remove(discountGalleryForUserListDiscountGalleryForUser);
                    oldGalleryIdOfDiscountGalleryForUserListDiscountGalleryForUser = em.merge(oldGalleryIdOfDiscountGalleryForUserListDiscountGalleryForUser);
                }
            }
            return gallery;
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
    public void edit(Gallery gallery) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Gallery persistentGallery = em.find(Gallery.class, gallery.getId());
            List<GalleryCost> galleryCostListOld = persistentGallery.getGalleryCostList();
            List<GalleryCost> galleryCostListNew = gallery.getGalleryCostList();
            List<Picture> pictureListOld = persistentGallery.getPictureList();
            List<Picture> pictureListNew = gallery.getPictureList();
            List<UserHasGallery> userHasGalleryListOld = persistentGallery.getUserHasGalleryList();
            List<UserHasGallery> userHasGalleryListNew = gallery.getUserHasGalleryList();
            List<DiscountGalleryForUser> discountGalleryForUserListOld = persistentGallery.getDiscountGalleryForUserList();
            List<DiscountGalleryForUser> discountGalleryForUserListNew = gallery.getDiscountGalleryForUserList();
            List<String> illegalOrphanMessages = null;
            for (Picture pictureListOldPicture : pictureListOld) {
                if (!pictureListNew.contains(pictureListOldPicture)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Picture " + pictureListOldPicture + " since its galleryId field is not nullable.");
                }
            }
            for (UserHasGallery userHasGalleryListOldUserHasGallery : userHasGalleryListOld) {
                if (!userHasGalleryListNew.contains(userHasGalleryListOldUserHasGallery)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UserHasGallery " + userHasGalleryListOldUserHasGallery + " since its galleryId field is not nullable.");
                }
            }
            for (DiscountGalleryForUser discountGalleryForUserListOldDiscountGalleryForUser : discountGalleryForUserListOld) {
                if (!discountGalleryForUserListNew.contains(discountGalleryForUserListOldDiscountGalleryForUser)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DiscountGalleryForUser " + discountGalleryForUserListOldDiscountGalleryForUser + " since its galleryId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<GalleryCost> attachedGalleryCostListNew = new ArrayList<GalleryCost>();
            for (GalleryCost galleryCostListNewGalleryCostToAttach : galleryCostListNew) {
                galleryCostListNewGalleryCostToAttach = em.getReference(galleryCostListNewGalleryCostToAttach.getClass(), galleryCostListNewGalleryCostToAttach.getId());
                attachedGalleryCostListNew.add(galleryCostListNewGalleryCostToAttach);
            }
            galleryCostListNew = attachedGalleryCostListNew;
            gallery.setGalleryCostList(galleryCostListNew);
            List<Picture> attachedPictureListNew = new ArrayList<Picture>();
            for (Picture pictureListNewPictureToAttach : pictureListNew) {
                pictureListNewPictureToAttach = em.getReference(pictureListNewPictureToAttach.getClass(), pictureListNewPictureToAttach.getId());
                attachedPictureListNew.add(pictureListNewPictureToAttach);
            }
            pictureListNew = attachedPictureListNew;
            gallery.setPictureList(pictureListNew);
            List<UserHasGallery> attachedUserHasGalleryListNew = new ArrayList<UserHasGallery>();
            for (UserHasGallery userHasGalleryListNewUserHasGalleryToAttach : userHasGalleryListNew) {
                userHasGalleryListNewUserHasGalleryToAttach = em.getReference(userHasGalleryListNewUserHasGalleryToAttach.getClass(), userHasGalleryListNewUserHasGalleryToAttach.getId());
                attachedUserHasGalleryListNew.add(userHasGalleryListNewUserHasGalleryToAttach);
            }
            userHasGalleryListNew = attachedUserHasGalleryListNew;
            gallery.setUserHasGalleryList(userHasGalleryListNew);
            List<DiscountGalleryForUser> attachedDiscountGalleryForUserListNew = new ArrayList<DiscountGalleryForUser>();
            for (DiscountGalleryForUser discountGalleryForUserListNewDiscountGalleryForUserToAttach : discountGalleryForUserListNew) {
                discountGalleryForUserListNewDiscountGalleryForUserToAttach = em.getReference(discountGalleryForUserListNewDiscountGalleryForUserToAttach.getClass(), discountGalleryForUserListNewDiscountGalleryForUserToAttach.getId());
                attachedDiscountGalleryForUserListNew.add(discountGalleryForUserListNewDiscountGalleryForUserToAttach);
            }
            discountGalleryForUserListNew = attachedDiscountGalleryForUserListNew;
            gallery.setDiscountGalleryForUserList(discountGalleryForUserListNew);
            gallery = em.merge(gallery);
            for (GalleryCost galleryCostListOldGalleryCost : galleryCostListOld) {
                if (!galleryCostListNew.contains(galleryCostListOldGalleryCost)) {
                    galleryCostListOldGalleryCost.getGalleryList().remove(gallery);
                    galleryCostListOldGalleryCost = em.merge(galleryCostListOldGalleryCost);
                }
            }
            for (GalleryCost galleryCostListNewGalleryCost : galleryCostListNew) {
                if (!galleryCostListOld.contains(galleryCostListNewGalleryCost)) {
                    galleryCostListNewGalleryCost.getGalleryList().add(gallery);
                    galleryCostListNewGalleryCost = em.merge(galleryCostListNewGalleryCost);
                }
            }
            for (Picture pictureListNewPicture : pictureListNew) {
                if (!pictureListOld.contains(pictureListNewPicture)) {
                    Gallery oldGalleryIdOfPictureListNewPicture = pictureListNewPicture.getGalleryId();
                    pictureListNewPicture.setGalleryId(gallery);
                    pictureListNewPicture = em.merge(pictureListNewPicture);
                    if (oldGalleryIdOfPictureListNewPicture != null && !oldGalleryIdOfPictureListNewPicture.equals(gallery)) {
                        oldGalleryIdOfPictureListNewPicture.getPictureList().remove(pictureListNewPicture);
                        oldGalleryIdOfPictureListNewPicture = em.merge(oldGalleryIdOfPictureListNewPicture);
                    }
                }
            }
            for (UserHasGallery userHasGalleryListNewUserHasGallery : userHasGalleryListNew) {
                if (!userHasGalleryListOld.contains(userHasGalleryListNewUserHasGallery)) {
                    Gallery oldGalleryIdOfUserHasGalleryListNewUserHasGallery = userHasGalleryListNewUserHasGallery.getGalleryId();
                    userHasGalleryListNewUserHasGallery.setGalleryId(gallery);
                    userHasGalleryListNewUserHasGallery = em.merge(userHasGalleryListNewUserHasGallery);
                    if (oldGalleryIdOfUserHasGalleryListNewUserHasGallery != null && !oldGalleryIdOfUserHasGalleryListNewUserHasGallery.equals(gallery)) {
                        oldGalleryIdOfUserHasGalleryListNewUserHasGallery.getUserHasGalleryList().remove(userHasGalleryListNewUserHasGallery);
                        oldGalleryIdOfUserHasGalleryListNewUserHasGallery = em.merge(oldGalleryIdOfUserHasGalleryListNewUserHasGallery);
                    }
                }
            }
            for (DiscountGalleryForUser discountGalleryForUserListNewDiscountGalleryForUser : discountGalleryForUserListNew) {
                if (!discountGalleryForUserListOld.contains(discountGalleryForUserListNewDiscountGalleryForUser)) {
                    Gallery oldGalleryIdOfDiscountGalleryForUserListNewDiscountGalleryForUser = discountGalleryForUserListNewDiscountGalleryForUser.getGalleryId();
                    discountGalleryForUserListNewDiscountGalleryForUser.setGalleryId(gallery);
                    discountGalleryForUserListNewDiscountGalleryForUser = em.merge(discountGalleryForUserListNewDiscountGalleryForUser);
                    if (oldGalleryIdOfDiscountGalleryForUserListNewDiscountGalleryForUser != null && !oldGalleryIdOfDiscountGalleryForUserListNewDiscountGalleryForUser.equals(gallery)) {
                        oldGalleryIdOfDiscountGalleryForUserListNewDiscountGalleryForUser.getDiscountGalleryForUserList().remove(discountGalleryForUserListNewDiscountGalleryForUser);
                        oldGalleryIdOfDiscountGalleryForUserListNewDiscountGalleryForUser = em.merge(oldGalleryIdOfDiscountGalleryForUserListNewDiscountGalleryForUser);
                    }
                }
            }
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gallery.getId();
                if (findGallery(id) == null) {
                    throw new NonexistentEntityException("The gallery with id " + id + " no longer exists.");
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
    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Gallery gallery;
            try {
                gallery = em.getReference(Gallery.class, id);
                gallery.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gallery with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Picture> pictureListOrphanCheck = gallery.getPictureList();
            for (Picture pictureListOrphanCheckPicture : pictureListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Gallery (" + gallery + ") cannot be destroyed since the Picture " + pictureListOrphanCheckPicture + " in its pictureList field has a non-nullable galleryId field.");
            }
            List<UserHasGallery> userHasGalleryListOrphanCheck = gallery.getUserHasGalleryList();
            for (UserHasGallery userHasGalleryListOrphanCheckUserHasGallery : userHasGalleryListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Gallery (" + gallery + ") cannot be destroyed since the UserHasGallery " + userHasGalleryListOrphanCheckUserHasGallery + " in its userHasGalleryList field has a non-nullable galleryId field.");
            }
            List<DiscountGalleryForUser> discountGalleryForUserListOrphanCheck = gallery.getDiscountGalleryForUserList();
            for (DiscountGalleryForUser discountGalleryForUserListOrphanCheckDiscountGalleryForUser : discountGalleryForUserListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Gallery (" + gallery + ") cannot be destroyed since the DiscountGalleryForUser " + discountGalleryForUserListOrphanCheckDiscountGalleryForUser + " in its discountGalleryForUserList field has a non-nullable galleryId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<GalleryCost> galleryCostList = gallery.getGalleryCostList();
            for (GalleryCost galleryCostListGalleryCost : galleryCostList) {
                galleryCostListGalleryCost.getGalleryList().remove(gallery);
                galleryCostListGalleryCost = em.merge(galleryCostListGalleryCost);
            }
            em.remove(gallery);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.flush();
            }
        }
    }

    @Override
    public List<Gallery> findGalleryEntities() {
        return findGalleryEntities(true, -1, -1);
    }

    @Override
    public List<Gallery> findGalleryEntities(int maxResults, int firstResult) {
        return findGalleryEntities(false, maxResults, firstResult);
    }

    private List<Gallery> findGalleryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Gallery.class));
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
    public Gallery findGallery(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Gallery.class, id);
        } finally {
            em.flush();
        }
    }

    @Override
    public int getGalleryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Gallery> rt = cq.from(Gallery.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.flush();
        }
    }

}
