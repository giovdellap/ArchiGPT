/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller;

import ir.shenakht.paint.controller.exceptions.IllegalOrphanException;
import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.controller.interfaces.ParticipantsJpaControllerIntf;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ir.shenakht.paint.domain.City;
import ir.shenakht.paint.domain.Racing;
import ir.shenakht.paint.domain.User;
import ir.shenakht.paint.domain.Judgment;
import ir.shenakht.paint.domain.Participants;
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
public class ParticipantsJpaController implements ParticipantsJpaControllerIntf {

    public ParticipantsJpaController() {
    }

    @PersistenceContext(unitName = "PSE", type = PersistenceContextType.TRANSACTION)
    private EntityManager emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public Participants create(Participants participants) throws RollbackFailureException, Exception {
        if (participants.getJudgmentList() == null) {
            participants.setJudgmentList(new ArrayList<Judgment>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            City cityId = participants.getCityId();
            if (cityId != null) {
                cityId = em.getReference(cityId.getClass(), cityId.getId());
                participants.setCityId(cityId);
            }
            Racing racingId = participants.getRacingId();
            if (racingId != null) {
                racingId = em.getReference(racingId.getClass(), racingId.getId());
                participants.setRacingId(racingId);
            }
            User userId = participants.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getId());
                participants.setUserId(userId);
            }
            List<Judgment> attachedJudgmentList = new ArrayList<Judgment>();
            for (Judgment judgmentListJudgmentToAttach : participants.getJudgmentList()) {
                judgmentListJudgmentToAttach = em.getReference(judgmentListJudgmentToAttach.getClass(), judgmentListJudgmentToAttach.getId());
                attachedJudgmentList.add(judgmentListJudgmentToAttach);
            }
            participants.setJudgmentList(attachedJudgmentList);
            em.persist(participants);
            if (cityId != null) {
                cityId.getParticipantsList().add(participants);
                cityId = em.merge(cityId);
            }
            if (racingId != null) {
                racingId.getParticipantsList().add(participants);
                racingId = em.merge(racingId);
            }
            if (userId != null) {
                userId.getParticipantsList().add(participants);
                userId = em.merge(userId);
            }
            for (Judgment judgmentListJudgment : participants.getJudgmentList()) {
                Participants oldParticipantsIdOfJudgmentListJudgment = judgmentListJudgment.getParticipantsId();
                judgmentListJudgment.setParticipantsId(participants);
                judgmentListJudgment = em.merge(judgmentListJudgment);
                if (oldParticipantsIdOfJudgmentListJudgment != null) {
                    oldParticipantsIdOfJudgmentListJudgment.getJudgmentList().remove(judgmentListJudgment);
                    oldParticipantsIdOfJudgmentListJudgment = em.merge(oldParticipantsIdOfJudgmentListJudgment);
                }
            }
            return participants;
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
    public void edit(Participants participants) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
          EntityManager em = null;
        try {
            em = getEntityManager();
            Participants persistentParticipants = em.find(Participants.class, participants.getId());
            City cityIdOld = persistentParticipants.getCityId();
            City cityIdNew = participants.getCityId();
            Racing racingIdOld = persistentParticipants.getRacingId();
            Racing racingIdNew = participants.getRacingId();
            User userIdOld = persistentParticipants.getUserId();
            User userIdNew = participants.getUserId();
            List<Judgment> judgmentListOld = persistentParticipants.getJudgmentList();
            List<Judgment> judgmentListNew = participants.getJudgmentList();
            List<String> illegalOrphanMessages = null;
            for (Judgment judgmentListOldJudgment : judgmentListOld) {
                if (!judgmentListNew.contains(judgmentListOldJudgment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Judgment " + judgmentListOldJudgment + " since its participantsId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cityIdNew != null) {
                cityIdNew = em.getReference(cityIdNew.getClass(), cityIdNew.getId());
                participants.setCityId(cityIdNew);
            }
            if (racingIdNew != null) {
                racingIdNew = em.getReference(racingIdNew.getClass(), racingIdNew.getId());
                participants.setRacingId(racingIdNew);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getId());
                participants.setUserId(userIdNew);
            }
            List<Judgment> attachedJudgmentListNew = new ArrayList<Judgment>();
            for (Judgment judgmentListNewJudgmentToAttach : judgmentListNew) {
                judgmentListNewJudgmentToAttach = em.getReference(judgmentListNewJudgmentToAttach.getClass(), judgmentListNewJudgmentToAttach.getId());
                attachedJudgmentListNew.add(judgmentListNewJudgmentToAttach);
            }
            judgmentListNew = attachedJudgmentListNew;
            participants.setJudgmentList(judgmentListNew);
            participants = em.merge(participants);
            if (cityIdOld != null && !cityIdOld.equals(cityIdNew)) {
                cityIdOld.getParticipantsList().remove(participants);
                cityIdOld = em.merge(cityIdOld);
            }
            if (cityIdNew != null && !cityIdNew.equals(cityIdOld)) {
                cityIdNew.getParticipantsList().add(participants);
                cityIdNew = em.merge(cityIdNew);
            }
            if (racingIdOld != null && !racingIdOld.equals(racingIdNew)) {
                racingIdOld.getParticipantsList().remove(participants);
                racingIdOld = em.merge(racingIdOld);
            }
            if (racingIdNew != null && !racingIdNew.equals(racingIdOld)) {
                racingIdNew.getParticipantsList().add(participants);
                racingIdNew = em.merge(racingIdNew);
            }
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getParticipantsList().remove(participants);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getParticipantsList().add(participants);
                userIdNew = em.merge(userIdNew);
            }
            for (Judgment judgmentListNewJudgment : judgmentListNew) {
                if (!judgmentListOld.contains(judgmentListNewJudgment)) {
                    Participants oldParticipantsIdOfJudgmentListNewJudgment = judgmentListNewJudgment.getParticipantsId();
                    judgmentListNewJudgment.setParticipantsId(participants);
                    judgmentListNewJudgment = em.merge(judgmentListNewJudgment);
                    if (oldParticipantsIdOfJudgmentListNewJudgment != null && !oldParticipantsIdOfJudgmentListNewJudgment.equals(participants)) {
                        oldParticipantsIdOfJudgmentListNewJudgment.getJudgmentList().remove(judgmentListNewJudgment);
                        oldParticipantsIdOfJudgmentListNewJudgment = em.merge(oldParticipantsIdOfJudgmentListNewJudgment);
                    }
                }
            }
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = participants.getId();
                if (findParticipants(id) == null) {
                    throw new NonexistentEntityException("The participants with id " + id + " no longer exists.");
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
            Participants participants;
            try {
                participants = em.getReference(Participants.class, id);
                participants.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The participants with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Judgment> judgmentListOrphanCheck = participants.getJudgmentList();
            for (Judgment judgmentListOrphanCheckJudgment : judgmentListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Participants (" + participants + ") cannot be destroyed since the Judgment " + judgmentListOrphanCheckJudgment + " in its judgmentList field has a non-nullable participantsId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            City cityId = participants.getCityId();
            if (cityId != null) {
                cityId.getParticipantsList().remove(participants);
                cityId = em.merge(cityId);
            }
            Racing racingId = participants.getRacingId();
            if (racingId != null) {
                racingId.getParticipantsList().remove(participants);
                racingId = em.merge(racingId);
            }
            User userId = participants.getUserId();
            if (userId != null) {
                userId.getParticipantsList().remove(participants);
                userId = em.merge(userId);
            }
            em.remove(participants);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.flush();
            }
        }
    }

    @Override
    public List<Participants> findParticipantsEntities() {
        return findParticipantsEntities(true, -1, -1);
    }

    @Override
    public List<Participants> findParticipantsEntities(int maxResults, int firstResult) {
        return findParticipantsEntities(false, maxResults, firstResult);
    }

    private List<Participants> findParticipantsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Participants.class));
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
    public Participants findParticipants(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Participants.class, id);
        } finally {
            em.flush();
        }
    }

    @Override
    public int getParticipantsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Participants> rt = cq.from(Participants.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.flush();
        }
    }

}
