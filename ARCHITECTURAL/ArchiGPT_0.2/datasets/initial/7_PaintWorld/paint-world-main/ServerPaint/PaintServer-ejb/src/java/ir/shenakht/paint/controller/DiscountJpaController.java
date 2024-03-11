/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller;

import ir.shenakht.paint.controller.interfaces.DiscountJpaControllerIntf;
import ir.shenakht.paint.controller.exceptions.IllegalOrphanException;
import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.domain.Discount;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ir.shenakht.paint.domain.DiscountGalleryForUser;
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
public class DiscountJpaController implements DiscountJpaControllerIntf {

    public DiscountJpaController() {
    }

    @PersistenceContext(unitName = "PSE", type = PersistenceContextType.TRANSACTION)
    private EntityManager emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public Discount create(Discount discount) throws RollbackFailureException, Exception {
        if (discount.getDiscountGalleryForUserList() == null) {
            discount.setDiscountGalleryForUserList(new ArrayList<DiscountGalleryForUser>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            List<DiscountGalleryForUser> attachedDiscountGalleryForUserList = new ArrayList<DiscountGalleryForUser>();
            for (DiscountGalleryForUser discountGalleryForUserListDiscountGalleryForUserToAttach : discount.getDiscountGalleryForUserList()) {
                discountGalleryForUserListDiscountGalleryForUserToAttach = em.getReference(discountGalleryForUserListDiscountGalleryForUserToAttach.getClass(), discountGalleryForUserListDiscountGalleryForUserToAttach.getId());
                attachedDiscountGalleryForUserList.add(discountGalleryForUserListDiscountGalleryForUserToAttach);
            }
            discount.setDiscountGalleryForUserList(attachedDiscountGalleryForUserList);
            em.persist(discount);
            for (DiscountGalleryForUser discountGalleryForUserListDiscountGalleryForUser : discount.getDiscountGalleryForUserList()) {
                Discount oldDiscountIdOfDiscountGalleryForUserListDiscountGalleryForUser = discountGalleryForUserListDiscountGalleryForUser.getDiscountId();
                discountGalleryForUserListDiscountGalleryForUser.setDiscountId(discount);
                discountGalleryForUserListDiscountGalleryForUser = em.merge(discountGalleryForUserListDiscountGalleryForUser);
                if (oldDiscountIdOfDiscountGalleryForUserListDiscountGalleryForUser != null) {
                    oldDiscountIdOfDiscountGalleryForUserListDiscountGalleryForUser.getDiscountGalleryForUserList().remove(discountGalleryForUserListDiscountGalleryForUser);
                    oldDiscountIdOfDiscountGalleryForUserListDiscountGalleryForUser = em.merge(oldDiscountIdOfDiscountGalleryForUserListDiscountGalleryForUser);
                }
            }
            return discount;
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
    public void edit(Discount discount) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Discount persistentDiscount = em.find(Discount.class, discount.getId());
            List<DiscountGalleryForUser> discountGalleryForUserListOld = persistentDiscount.getDiscountGalleryForUserList();
            List<DiscountGalleryForUser> discountGalleryForUserListNew = discount.getDiscountGalleryForUserList();
            List<String> illegalOrphanMessages = null;
            for (DiscountGalleryForUser discountGalleryForUserListOldDiscountGalleryForUser : discountGalleryForUserListOld) {
                if (!discountGalleryForUserListNew.contains(discountGalleryForUserListOldDiscountGalleryForUser)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DiscountGalleryForUser " + discountGalleryForUserListOldDiscountGalleryForUser + " since its discountId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<DiscountGalleryForUser> attachedDiscountGalleryForUserListNew = new ArrayList<DiscountGalleryForUser>();
            for (DiscountGalleryForUser discountGalleryForUserListNewDiscountGalleryForUserToAttach : discountGalleryForUserListNew) {
                discountGalleryForUserListNewDiscountGalleryForUserToAttach = em.getReference(discountGalleryForUserListNewDiscountGalleryForUserToAttach.getClass(), discountGalleryForUserListNewDiscountGalleryForUserToAttach.getId());
                attachedDiscountGalleryForUserListNew.add(discountGalleryForUserListNewDiscountGalleryForUserToAttach);
            }
            discountGalleryForUserListNew = attachedDiscountGalleryForUserListNew;
            discount.setDiscountGalleryForUserList(discountGalleryForUserListNew);
            discount = em.merge(discount);
            for (DiscountGalleryForUser discountGalleryForUserListNewDiscountGalleryForUser : discountGalleryForUserListNew) {
                if (!discountGalleryForUserListOld.contains(discountGalleryForUserListNewDiscountGalleryForUser)) {
                    Discount oldDiscountIdOfDiscountGalleryForUserListNewDiscountGalleryForUser = discountGalleryForUserListNewDiscountGalleryForUser.getDiscountId();
                    discountGalleryForUserListNewDiscountGalleryForUser.setDiscountId(discount);
                    discountGalleryForUserListNewDiscountGalleryForUser = em.merge(discountGalleryForUserListNewDiscountGalleryForUser);
                    if (oldDiscountIdOfDiscountGalleryForUserListNewDiscountGalleryForUser != null && !oldDiscountIdOfDiscountGalleryForUserListNewDiscountGalleryForUser.equals(discount)) {
                        oldDiscountIdOfDiscountGalleryForUserListNewDiscountGalleryForUser.getDiscountGalleryForUserList().remove(discountGalleryForUserListNewDiscountGalleryForUser);
                        oldDiscountIdOfDiscountGalleryForUserListNewDiscountGalleryForUser = em.merge(oldDiscountIdOfDiscountGalleryForUserListNewDiscountGalleryForUser);
                    }
                }
            }
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = discount.getId();
                if (findDiscount(id) == null) {
                    throw new NonexistentEntityException("The discount with id " + id + " no longer exists.");
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
            Discount discount;
            try {
                discount = em.getReference(Discount.class, id);
                discount.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The discount with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DiscountGalleryForUser> discountGalleryForUserListOrphanCheck = discount.getDiscountGalleryForUserList();
            for (DiscountGalleryForUser discountGalleryForUserListOrphanCheckDiscountGalleryForUser : discountGalleryForUserListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Discount (" + discount + ") cannot be destroyed since the DiscountGalleryForUser " + discountGalleryForUserListOrphanCheckDiscountGalleryForUser + " in its discountGalleryForUserList field has a non-nullable discountId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(discount);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.flush();
            }
        }
    }

    @Override
    public List<Discount> findDiscountEntities() {
        return findDiscountEntities(true, -1, -1);
    }

    @Override
    public List<Discount> findDiscountEntities(int maxResults, int firstResult) {
        return findDiscountEntities(false, maxResults, firstResult);
    }

    private List<Discount> findDiscountEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Discount.class));
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
    public Discount findDiscount(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Discount.class, id);
        } finally {
            em.flush();
        }
    }

    @Override
    public int getDiscountCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Discount> rt = cq.from(Discount.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.flush();
        }
    }

}
