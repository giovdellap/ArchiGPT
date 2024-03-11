/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller;

import ir.shenakht.paint.controller.exceptions.IllegalOrphanException;
import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.controller.interfaces.RacingJpaControllerIntf;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ir.shenakht.paint.domain.Judgment;
import java.util.ArrayList;
import java.util.List;
import ir.shenakht.paint.domain.ScaleQuestionForRacing;
import ir.shenakht.paint.domain.Participants;
import ir.shenakht.paint.domain.Racing;
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
public class RacingJpaController implements RacingJpaControllerIntf {

    public RacingJpaController() {
    }

    @PersistenceContext(unitName = "PSE", type = PersistenceContextType.TRANSACTION)
    private EntityManager emf = null;

    public EntityManager getEntityManager() {
        return emf;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public Racing create(Racing racing) throws RollbackFailureException, Exception {
        if (racing.getJudgmentList() == null) {
            racing.setJudgmentList(new ArrayList<Judgment>());
        }
        if (racing.getScaleQuestionForRacingList() == null) {
            racing.setScaleQuestionForRacingList(new ArrayList<ScaleQuestionForRacing>());
        }
        if (racing.getParticipantsList() == null) {
            racing.setParticipantsList(new ArrayList<Participants>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            List<Judgment> attachedJudgmentList = new ArrayList<Judgment>();
            for (Judgment judgmentListJudgmentToAttach : racing.getJudgmentList()) {
                judgmentListJudgmentToAttach = em.getReference(judgmentListJudgmentToAttach.getClass(), judgmentListJudgmentToAttach.getId());
                attachedJudgmentList.add(judgmentListJudgmentToAttach);
            }
            racing.setJudgmentList(attachedJudgmentList);
            List<ScaleQuestionForRacing> attachedScaleQuestionForRacingList = new ArrayList<ScaleQuestionForRacing>();
            for (ScaleQuestionForRacing scaleQuestionForRacingListScaleQuestionForRacingToAttach : racing.getScaleQuestionForRacingList()) {
                scaleQuestionForRacingListScaleQuestionForRacingToAttach = em.getReference(scaleQuestionForRacingListScaleQuestionForRacingToAttach.getClass(), scaleQuestionForRacingListScaleQuestionForRacingToAttach.getId());
                attachedScaleQuestionForRacingList.add(scaleQuestionForRacingListScaleQuestionForRacingToAttach);
            }
            racing.setScaleQuestionForRacingList(attachedScaleQuestionForRacingList);
            List<Participants> attachedParticipantsList = new ArrayList<Participants>();
            for (Participants participantsListParticipantsToAttach : racing.getParticipantsList()) {
                participantsListParticipantsToAttach = em.getReference(participantsListParticipantsToAttach.getClass(), participantsListParticipantsToAttach.getId());
                attachedParticipantsList.add(participantsListParticipantsToAttach);
            }
            racing.setParticipantsList(attachedParticipantsList);
            em.persist(racing);
            for (Judgment judgmentListJudgment : racing.getJudgmentList()) {
                Racing oldRacingIdOfJudgmentListJudgment = judgmentListJudgment.getRacingId();
                judgmentListJudgment.setRacingId(racing);
                judgmentListJudgment = em.merge(judgmentListJudgment);
                if (oldRacingIdOfJudgmentListJudgment != null) {
                    oldRacingIdOfJudgmentListJudgment.getJudgmentList().remove(judgmentListJudgment);
                    oldRacingIdOfJudgmentListJudgment = em.merge(oldRacingIdOfJudgmentListJudgment);
                }
            }
            for (ScaleQuestionForRacing scaleQuestionForRacingListScaleQuestionForRacing : racing.getScaleQuestionForRacingList()) {
                Racing oldRacingIdOfScaleQuestionForRacingListScaleQuestionForRacing = scaleQuestionForRacingListScaleQuestionForRacing.getRacingId();
                scaleQuestionForRacingListScaleQuestionForRacing.setRacingId(racing);
                scaleQuestionForRacingListScaleQuestionForRacing = em.merge(scaleQuestionForRacingListScaleQuestionForRacing);
                if (oldRacingIdOfScaleQuestionForRacingListScaleQuestionForRacing != null) {
                    oldRacingIdOfScaleQuestionForRacingListScaleQuestionForRacing.getScaleQuestionForRacingList().remove(scaleQuestionForRacingListScaleQuestionForRacing);
                    oldRacingIdOfScaleQuestionForRacingListScaleQuestionForRacing = em.merge(oldRacingIdOfScaleQuestionForRacingListScaleQuestionForRacing);
                }
            }
            for (Participants participantsListParticipants : racing.getParticipantsList()) {
                Racing oldRacingIdOfParticipantsListParticipants = participantsListParticipants.getRacingId();
                participantsListParticipants.setRacingId(racing);
                participantsListParticipants = em.merge(participantsListParticipants);
                if (oldRacingIdOfParticipantsListParticipants != null) {
                    oldRacingIdOfParticipantsListParticipants.getParticipantsList().remove(participantsListParticipants);
                    oldRacingIdOfParticipantsListParticipants = em.merge(oldRacingIdOfParticipantsListParticipants);
                }
            }
            return racing;
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
    public void edit(Racing racing) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Racing persistentRacing = em.find(Racing.class, racing.getId());
            List<Judgment> judgmentListOld = persistentRacing.getJudgmentList();
            List<Judgment> judgmentListNew = racing.getJudgmentList();
            List<ScaleQuestionForRacing> scaleQuestionForRacingListOld = persistentRacing.getScaleQuestionForRacingList();
            List<ScaleQuestionForRacing> scaleQuestionForRacingListNew = racing.getScaleQuestionForRacingList();
            List<Participants> participantsListOld = persistentRacing.getParticipantsList();
            List<Participants> participantsListNew = racing.getParticipantsList();
            List<String> illegalOrphanMessages = null;
            for (Judgment judgmentListOldJudgment : judgmentListOld) {
                if (!judgmentListNew.contains(judgmentListOldJudgment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Judgment " + judgmentListOldJudgment + " since its racingId field is not nullable.");
                }
            }
            for (ScaleQuestionForRacing scaleQuestionForRacingListOldScaleQuestionForRacing : scaleQuestionForRacingListOld) {
                if (!scaleQuestionForRacingListNew.contains(scaleQuestionForRacingListOldScaleQuestionForRacing)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ScaleQuestionForRacing " + scaleQuestionForRacingListOldScaleQuestionForRacing + " since its racingId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Judgment> attachedJudgmentListNew = new ArrayList<Judgment>();
            for (Judgment judgmentListNewJudgmentToAttach : judgmentListNew) {
                judgmentListNewJudgmentToAttach = em.getReference(judgmentListNewJudgmentToAttach.getClass(), judgmentListNewJudgmentToAttach.getId());
                attachedJudgmentListNew.add(judgmentListNewJudgmentToAttach);
            }
            judgmentListNew = attachedJudgmentListNew;
            racing.setJudgmentList(judgmentListNew);
            List<ScaleQuestionForRacing> attachedScaleQuestionForRacingListNew = new ArrayList<ScaleQuestionForRacing>();
            for (ScaleQuestionForRacing scaleQuestionForRacingListNewScaleQuestionForRacingToAttach : scaleQuestionForRacingListNew) {
                scaleQuestionForRacingListNewScaleQuestionForRacingToAttach = em.getReference(scaleQuestionForRacingListNewScaleQuestionForRacingToAttach.getClass(), scaleQuestionForRacingListNewScaleQuestionForRacingToAttach.getId());
                attachedScaleQuestionForRacingListNew.add(scaleQuestionForRacingListNewScaleQuestionForRacingToAttach);
            }
            scaleQuestionForRacingListNew = attachedScaleQuestionForRacingListNew;
            racing.setScaleQuestionForRacingList(scaleQuestionForRacingListNew);
            List<Participants> attachedParticipantsListNew = new ArrayList<Participants>();
            for (Participants participantsListNewParticipantsToAttach : participantsListNew) {
                participantsListNewParticipantsToAttach = em.getReference(participantsListNewParticipantsToAttach.getClass(), participantsListNewParticipantsToAttach.getId());
                attachedParticipantsListNew.add(participantsListNewParticipantsToAttach);
            }
            participantsListNew = attachedParticipantsListNew;
            racing.setParticipantsList(participantsListNew);
            racing = em.merge(racing);
            for (Judgment judgmentListNewJudgment : judgmentListNew) {
                if (!judgmentListOld.contains(judgmentListNewJudgment)) {
                    Racing oldRacingIdOfJudgmentListNewJudgment = judgmentListNewJudgment.getRacingId();
                    judgmentListNewJudgment.setRacingId(racing);
                    judgmentListNewJudgment = em.merge(judgmentListNewJudgment);
                    if (oldRacingIdOfJudgmentListNewJudgment != null && !oldRacingIdOfJudgmentListNewJudgment.equals(racing)) {
                        oldRacingIdOfJudgmentListNewJudgment.getJudgmentList().remove(judgmentListNewJudgment);
                        oldRacingIdOfJudgmentListNewJudgment = em.merge(oldRacingIdOfJudgmentListNewJudgment);
                    }
                }
            }
            for (ScaleQuestionForRacing scaleQuestionForRacingListNewScaleQuestionForRacing : scaleQuestionForRacingListNew) {
                if (!scaleQuestionForRacingListOld.contains(scaleQuestionForRacingListNewScaleQuestionForRacing)) {
                    Racing oldRacingIdOfScaleQuestionForRacingListNewScaleQuestionForRacing = scaleQuestionForRacingListNewScaleQuestionForRacing.getRacingId();
                    scaleQuestionForRacingListNewScaleQuestionForRacing.setRacingId(racing);
                    scaleQuestionForRacingListNewScaleQuestionForRacing = em.merge(scaleQuestionForRacingListNewScaleQuestionForRacing);
                    if (oldRacingIdOfScaleQuestionForRacingListNewScaleQuestionForRacing != null && !oldRacingIdOfScaleQuestionForRacingListNewScaleQuestionForRacing.equals(racing)) {
                        oldRacingIdOfScaleQuestionForRacingListNewScaleQuestionForRacing.getScaleQuestionForRacingList().remove(scaleQuestionForRacingListNewScaleQuestionForRacing);
                        oldRacingIdOfScaleQuestionForRacingListNewScaleQuestionForRacing = em.merge(oldRacingIdOfScaleQuestionForRacingListNewScaleQuestionForRacing);
                    }
                }
            }
            for (Participants participantsListOldParticipants : participantsListOld) {
                if (!participantsListNew.contains(participantsListOldParticipants)) {
                    participantsListOldParticipants.setRacingId(null);
                    participantsListOldParticipants = em.merge(participantsListOldParticipants);
                }
            }
            for (Participants participantsListNewParticipants : participantsListNew) {
                if (!participantsListOld.contains(participantsListNewParticipants)) {
                    Racing oldRacingIdOfParticipantsListNewParticipants = participantsListNewParticipants.getRacingId();
                    participantsListNewParticipants.setRacingId(racing);
                    participantsListNewParticipants = em.merge(participantsListNewParticipants);
                    if (oldRacingIdOfParticipantsListNewParticipants != null && !oldRacingIdOfParticipantsListNewParticipants.equals(racing)) {
                        oldRacingIdOfParticipantsListNewParticipants.getParticipantsList().remove(participantsListNewParticipants);
                        oldRacingIdOfParticipantsListNewParticipants = em.merge(oldRacingIdOfParticipantsListNewParticipants);
                    }
                }
            }
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = racing.getId();
                if (findRacing(id) == null) {
                    throw new NonexistentEntityException("The racing with id " + id + " no longer exists.");
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
            Racing racing;
            try {
                racing = em.getReference(Racing.class, id);
                racing.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The racing with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Judgment> judgmentListOrphanCheck = racing.getJudgmentList();
            for (Judgment judgmentListOrphanCheckJudgment : judgmentListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Racing (" + racing + ") cannot be destroyed since the Judgment " + judgmentListOrphanCheckJudgment + " in its judgmentList field has a non-nullable racingId field.");
            }
            List<ScaleQuestionForRacing> scaleQuestionForRacingListOrphanCheck = racing.getScaleQuestionForRacingList();
            for (ScaleQuestionForRacing scaleQuestionForRacingListOrphanCheckScaleQuestionForRacing : scaleQuestionForRacingListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Racing (" + racing + ") cannot be destroyed since the ScaleQuestionForRacing " + scaleQuestionForRacingListOrphanCheckScaleQuestionForRacing + " in its scaleQuestionForRacingList field has a non-nullable racingId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Participants> participantsList = racing.getParticipantsList();
            for (Participants participantsListParticipants : participantsList) {
                participantsListParticipants.setRacingId(null);
                participantsListParticipants = em.merge(participantsListParticipants);
            }
            em.remove(racing);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.flush();
            }
        }
    }

    @Override
    public List<Racing> findRacingEntities() {
        return findRacingEntities(true, -1, -1);
    }

    @Override
    public List<Racing> findRacingEntities(int maxResults, int firstResult) {
        return findRacingEntities(false, maxResults, firstResult);
    }

    private List<Racing> findRacingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Racing.class));
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
    public Racing findRacing(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Racing.class, id);
        } finally {
            em.flush();
        }
    }

    @Override
    public int getRacingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Racing> rt = cq.from(Racing.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.flush();
        }
    }

}
