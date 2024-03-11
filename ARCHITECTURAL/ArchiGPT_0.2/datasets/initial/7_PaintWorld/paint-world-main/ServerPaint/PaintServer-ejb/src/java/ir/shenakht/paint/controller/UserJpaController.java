/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller;

import ir.shenakht.paint.controller.exceptions.IllegalOrphanException;
import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.controller.interfaces.UserJpaControllerIntf;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ir.shenakht.paint.domain.UserHasGallery;
import java.util.ArrayList;
import java.util.List;
import ir.shenakht.paint.domain.DiscountGalleryForUser;
import ir.shenakht.paint.domain.Participants;
import ir.shenakht.paint.domain.PaymentIrancel;
import ir.shenakht.paint.domain.User;
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
public class UserJpaController implements UserJpaControllerIntf {

    public UserJpaController() {
    }

    @PersistenceContext(unitName = "PSE", type = PersistenceContextType.TRANSACTION)
    private EntityManager emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public User create(User user) throws RollbackFailureException, Exception {
        if (user.getPaymentIrancelList() == null) {
            user.setPaymentIrancelList(new ArrayList<PaymentIrancel>());
        }
        if (user.getUserHasGalleryList() == null) {
            user.setUserHasGalleryList(new ArrayList<UserHasGallery>());
        }
        if (user.getDiscountGalleryForUserList() == null) {
            user.setDiscountGalleryForUserList(new ArrayList<DiscountGalleryForUser>());
        }
        if (user.getParticipantsList() == null) {
            user.setParticipantsList(new ArrayList<Participants>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            List<PaymentIrancel> attachedPaymentIrancelList = new ArrayList<PaymentIrancel>();
            for (PaymentIrancel paymentIrancelListPaymentIrancelToAttach : user.getPaymentIrancelList()) {
                paymentIrancelListPaymentIrancelToAttach = em.getReference(paymentIrancelListPaymentIrancelToAttach.getClass(), paymentIrancelListPaymentIrancelToAttach.getId());
                attachedPaymentIrancelList.add(paymentIrancelListPaymentIrancelToAttach);
            }
            user.setPaymentIrancelList(attachedPaymentIrancelList);
            List<UserHasGallery> attachedUserHasGalleryList = new ArrayList<UserHasGallery>();
            for (UserHasGallery userHasGalleryListUserHasGalleryToAttach : user.getUserHasGalleryList()) {
                userHasGalleryListUserHasGalleryToAttach = em.getReference(userHasGalleryListUserHasGalleryToAttach.getClass(), userHasGalleryListUserHasGalleryToAttach.getId());
                attachedUserHasGalleryList.add(userHasGalleryListUserHasGalleryToAttach);
            }
            user.setUserHasGalleryList(attachedUserHasGalleryList);
            List<DiscountGalleryForUser> attachedDiscountGalleryForUserList = new ArrayList<DiscountGalleryForUser>();
            for (DiscountGalleryForUser discountGalleryForUserListDiscountGalleryForUserToAttach : user.getDiscountGalleryForUserList()) {
                discountGalleryForUserListDiscountGalleryForUserToAttach = em.getReference(discountGalleryForUserListDiscountGalleryForUserToAttach.getClass(), discountGalleryForUserListDiscountGalleryForUserToAttach.getId());
                attachedDiscountGalleryForUserList.add(discountGalleryForUserListDiscountGalleryForUserToAttach);
            }
            user.setDiscountGalleryForUserList(attachedDiscountGalleryForUserList);
            List<Participants> attachedParticipantsList = new ArrayList<Participants>();
            for (Participants participantsListParticipantsToAttach : user.getParticipantsList()) {
                participantsListParticipantsToAttach = em.getReference(participantsListParticipantsToAttach.getClass(), participantsListParticipantsToAttach.getId());
                attachedParticipantsList.add(participantsListParticipantsToAttach);
            }
            user.setParticipantsList(attachedParticipantsList);
            em.persist(user);
            for (PaymentIrancel paymentIrancelListPaymentIrancel : user.getPaymentIrancelList()) {
                User oldUserIdOfPaymentIrancelListPaymentIrancel = paymentIrancelListPaymentIrancel.getUserId();
                paymentIrancelListPaymentIrancel.setUserId(user);
                paymentIrancelListPaymentIrancel = em.merge(paymentIrancelListPaymentIrancel);
                if (oldUserIdOfPaymentIrancelListPaymentIrancel != null) {
                    oldUserIdOfPaymentIrancelListPaymentIrancel.getPaymentIrancelList().remove(paymentIrancelListPaymentIrancel);
                    oldUserIdOfPaymentIrancelListPaymentIrancel = em.merge(oldUserIdOfPaymentIrancelListPaymentIrancel);
                }
            }
            for (UserHasGallery userHasGalleryListUserHasGallery : user.getUserHasGalleryList()) {
                User oldUserIdOfUserHasGalleryListUserHasGallery = userHasGalleryListUserHasGallery.getUserId();
                userHasGalleryListUserHasGallery.setUserId(user);
                userHasGalleryListUserHasGallery = em.merge(userHasGalleryListUserHasGallery);
                if (oldUserIdOfUserHasGalleryListUserHasGallery != null) {
                    oldUserIdOfUserHasGalleryListUserHasGallery.getUserHasGalleryList().remove(userHasGalleryListUserHasGallery);
                    oldUserIdOfUserHasGalleryListUserHasGallery = em.merge(oldUserIdOfUserHasGalleryListUserHasGallery);
                }
            }
            for (DiscountGalleryForUser discountGalleryForUserListDiscountGalleryForUser : user.getDiscountGalleryForUserList()) {
                User oldUserIdOfDiscountGalleryForUserListDiscountGalleryForUser = discountGalleryForUserListDiscountGalleryForUser.getUserId();
                discountGalleryForUserListDiscountGalleryForUser.setUserId(user);
                discountGalleryForUserListDiscountGalleryForUser = em.merge(discountGalleryForUserListDiscountGalleryForUser);
                if (oldUserIdOfDiscountGalleryForUserListDiscountGalleryForUser != null) {
                    oldUserIdOfDiscountGalleryForUserListDiscountGalleryForUser.getDiscountGalleryForUserList().remove(discountGalleryForUserListDiscountGalleryForUser);
                    oldUserIdOfDiscountGalleryForUserListDiscountGalleryForUser = em.merge(oldUserIdOfDiscountGalleryForUserListDiscountGalleryForUser);
                }
            }
            for (Participants participantsListParticipants : user.getParticipantsList()) {
                User oldUserIdOfParticipantsListParticipants = participantsListParticipants.getUserId();
                participantsListParticipants.setUserId(user);
                participantsListParticipants = em.merge(participantsListParticipants);
                if (oldUserIdOfParticipantsListParticipants != null) {
                    oldUserIdOfParticipantsListParticipants.getParticipantsList().remove(participantsListParticipants);
                    oldUserIdOfParticipantsListParticipants = em.merge(oldUserIdOfParticipantsListParticipants);
                }
            }
            return user;
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
    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            User persistentUser = em.find(User.class, user.getId());
            List<PaymentIrancel> paymentIrancelListOld = persistentUser.getPaymentIrancelList();
            List<PaymentIrancel> paymentIrancelListNew = user.getPaymentIrancelList();
            List<UserHasGallery> userHasGalleryListOld = persistentUser.getUserHasGalleryList();
            List<UserHasGallery> userHasGalleryListNew = user.getUserHasGalleryList();
            List<DiscountGalleryForUser> discountGalleryForUserListOld = persistentUser.getDiscountGalleryForUserList();
            List<DiscountGalleryForUser> discountGalleryForUserListNew = user.getDiscountGalleryForUserList();
            List<Participants> participantsListOld = persistentUser.getParticipantsList();
            List<Participants> participantsListNew = user.getParticipantsList();
            List<String> illegalOrphanMessages = null;
            for (PaymentIrancel paymentIrancelListOldPaymentIrancel : paymentIrancelListOld) {
                if (!paymentIrancelListNew.contains(paymentIrancelListOldPaymentIrancel)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PaymentIrancel " + paymentIrancelListOldPaymentIrancel + " since its userId field is not nullable.");
                }
            }
            for (UserHasGallery userHasGalleryListOldUserHasGallery : userHasGalleryListOld) {
                if (!userHasGalleryListNew.contains(userHasGalleryListOldUserHasGallery)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UserHasGallery " + userHasGalleryListOldUserHasGallery + " since its userId field is not nullable.");
                }
            }
            for (DiscountGalleryForUser discountGalleryForUserListOldDiscountGalleryForUser : discountGalleryForUserListOld) {
                if (!discountGalleryForUserListNew.contains(discountGalleryForUserListOldDiscountGalleryForUser)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DiscountGalleryForUser " + discountGalleryForUserListOldDiscountGalleryForUser + " since its userId field is not nullable.");
                }
            }
            for (Participants participantsListOldParticipants : participantsListOld) {
                if (!participantsListNew.contains(participantsListOldParticipants)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Participants " + participantsListOldParticipants + " since its userId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<PaymentIrancel> attachedPaymentIrancelListNew = new ArrayList<PaymentIrancel>();
            for (PaymentIrancel paymentIrancelListNewPaymentIrancelToAttach : paymentIrancelListNew) {
                paymentIrancelListNewPaymentIrancelToAttach = em.getReference(paymentIrancelListNewPaymentIrancelToAttach.getClass(), paymentIrancelListNewPaymentIrancelToAttach.getId());
                attachedPaymentIrancelListNew.add(paymentIrancelListNewPaymentIrancelToAttach);
            }
            paymentIrancelListNew = attachedPaymentIrancelListNew;
            user.setPaymentIrancelList(paymentIrancelListNew);
            List<UserHasGallery> attachedUserHasGalleryListNew = new ArrayList<UserHasGallery>();
            for (UserHasGallery userHasGalleryListNewUserHasGalleryToAttach : userHasGalleryListNew) {
                userHasGalleryListNewUserHasGalleryToAttach = em.getReference(userHasGalleryListNewUserHasGalleryToAttach.getClass(), userHasGalleryListNewUserHasGalleryToAttach.getId());
                attachedUserHasGalleryListNew.add(userHasGalleryListNewUserHasGalleryToAttach);
            }
            userHasGalleryListNew = attachedUserHasGalleryListNew;
            user.setUserHasGalleryList(userHasGalleryListNew);
            List<DiscountGalleryForUser> attachedDiscountGalleryForUserListNew = new ArrayList<DiscountGalleryForUser>();
            for (DiscountGalleryForUser discountGalleryForUserListNewDiscountGalleryForUserToAttach : discountGalleryForUserListNew) {
                discountGalleryForUserListNewDiscountGalleryForUserToAttach = em.getReference(discountGalleryForUserListNewDiscountGalleryForUserToAttach.getClass(), discountGalleryForUserListNewDiscountGalleryForUserToAttach.getId());
                attachedDiscountGalleryForUserListNew.add(discountGalleryForUserListNewDiscountGalleryForUserToAttach);
            }
            discountGalleryForUserListNew = attachedDiscountGalleryForUserListNew;
            user.setDiscountGalleryForUserList(discountGalleryForUserListNew);
            List<Participants> attachedParticipantsListNew = new ArrayList<Participants>();
            for (Participants participantsListNewParticipantsToAttach : participantsListNew) {
                participantsListNewParticipantsToAttach = em.getReference(participantsListNewParticipantsToAttach.getClass(), participantsListNewParticipantsToAttach.getId());
                attachedParticipantsListNew.add(participantsListNewParticipantsToAttach);
            }
            participantsListNew = attachedParticipantsListNew;
            user.setParticipantsList(participantsListNew);
            user = em.merge(user);
            for (PaymentIrancel paymentIrancelListNewPaymentIrancel : paymentIrancelListNew) {
                if (!paymentIrancelListOld.contains(paymentIrancelListNewPaymentIrancel)) {
                    User oldUserIdOfPaymentIrancelListNewPaymentIrancel = paymentIrancelListNewPaymentIrancel.getUserId();
                    paymentIrancelListNewPaymentIrancel.setUserId(user);
                    paymentIrancelListNewPaymentIrancel = em.merge(paymentIrancelListNewPaymentIrancel);
                    if (oldUserIdOfPaymentIrancelListNewPaymentIrancel != null && !oldUserIdOfPaymentIrancelListNewPaymentIrancel.equals(user)) {
                        oldUserIdOfPaymentIrancelListNewPaymentIrancel.getPaymentIrancelList().remove(paymentIrancelListNewPaymentIrancel);
                        oldUserIdOfPaymentIrancelListNewPaymentIrancel = em.merge(oldUserIdOfPaymentIrancelListNewPaymentIrancel);
                    }
                }
            }
            for (UserHasGallery userHasGalleryListNewUserHasGallery : userHasGalleryListNew) {
                if (!userHasGalleryListOld.contains(userHasGalleryListNewUserHasGallery)) {
                    User oldUserIdOfUserHasGalleryListNewUserHasGallery = userHasGalleryListNewUserHasGallery.getUserId();
                    userHasGalleryListNewUserHasGallery.setUserId(user);
                    userHasGalleryListNewUserHasGallery = em.merge(userHasGalleryListNewUserHasGallery);
                    if (oldUserIdOfUserHasGalleryListNewUserHasGallery != null && !oldUserIdOfUserHasGalleryListNewUserHasGallery.equals(user)) {
                        oldUserIdOfUserHasGalleryListNewUserHasGallery.getUserHasGalleryList().remove(userHasGalleryListNewUserHasGallery);
                        oldUserIdOfUserHasGalleryListNewUserHasGallery = em.merge(oldUserIdOfUserHasGalleryListNewUserHasGallery);
                    }
                }
            }
            for (DiscountGalleryForUser discountGalleryForUserListNewDiscountGalleryForUser : discountGalleryForUserListNew) {
                if (!discountGalleryForUserListOld.contains(discountGalleryForUserListNewDiscountGalleryForUser)) {
                    User oldUserIdOfDiscountGalleryForUserListNewDiscountGalleryForUser = discountGalleryForUserListNewDiscountGalleryForUser.getUserId();
                    discountGalleryForUserListNewDiscountGalleryForUser.setUserId(user);
                    discountGalleryForUserListNewDiscountGalleryForUser = em.merge(discountGalleryForUserListNewDiscountGalleryForUser);
                    if (oldUserIdOfDiscountGalleryForUserListNewDiscountGalleryForUser != null && !oldUserIdOfDiscountGalleryForUserListNewDiscountGalleryForUser.equals(user)) {
                        oldUserIdOfDiscountGalleryForUserListNewDiscountGalleryForUser.getDiscountGalleryForUserList().remove(discountGalleryForUserListNewDiscountGalleryForUser);
                        oldUserIdOfDiscountGalleryForUserListNewDiscountGalleryForUser = em.merge(oldUserIdOfDiscountGalleryForUserListNewDiscountGalleryForUser);
                    }
                }
            }
            for (Participants participantsListNewParticipants : participantsListNew) {
                if (!participantsListOld.contains(participantsListNewParticipants)) {
                    User oldUserIdOfParticipantsListNewParticipants = participantsListNewParticipants.getUserId();
                    participantsListNewParticipants.setUserId(user);
                    participantsListNewParticipants = em.merge(participantsListNewParticipants);
                    if (oldUserIdOfParticipantsListNewParticipants != null && !oldUserIdOfParticipantsListNewParticipants.equals(user)) {
                        oldUserIdOfParticipantsListNewParticipants.getParticipantsList().remove(participantsListNewParticipants);
                        oldUserIdOfParticipantsListNewParticipants = em.merge(oldUserIdOfParticipantsListNewParticipants);
                    }
                }
            }
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = user.getId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
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
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PaymentIrancel> paymentIrancelListOrphanCheck = user.getPaymentIrancelList();
            for (PaymentIrancel paymentIrancelListOrphanCheckPaymentIrancel : paymentIrancelListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the PaymentIrancel " + paymentIrancelListOrphanCheckPaymentIrancel + " in its paymentIrancelList field has a non-nullable userId field.");
            }
            List<UserHasGallery> userHasGalleryListOrphanCheck = user.getUserHasGalleryList();
            for (UserHasGallery userHasGalleryListOrphanCheckUserHasGallery : userHasGalleryListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the UserHasGallery " + userHasGalleryListOrphanCheckUserHasGallery + " in its userHasGalleryList field has a non-nullable userId field.");
            }
            List<DiscountGalleryForUser> discountGalleryForUserListOrphanCheck = user.getDiscountGalleryForUserList();
            for (DiscountGalleryForUser discountGalleryForUserListOrphanCheckDiscountGalleryForUser : discountGalleryForUserListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the DiscountGalleryForUser " + discountGalleryForUserListOrphanCheckDiscountGalleryForUser + " in its discountGalleryForUserList field has a non-nullable userId field.");
            }
            List<Participants> participantsListOrphanCheck = user.getParticipantsList();
            for (Participants participantsListOrphanCheckParticipants : participantsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Participants " + participantsListOrphanCheckParticipants + " in its participantsList field has a non-nullable userId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(user);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.flush();
            }
        }
    }

    @Override
    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    @Override
    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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
    public User findUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.flush();
        }
    }

    @Override
    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.flush();
        }
    }

}
