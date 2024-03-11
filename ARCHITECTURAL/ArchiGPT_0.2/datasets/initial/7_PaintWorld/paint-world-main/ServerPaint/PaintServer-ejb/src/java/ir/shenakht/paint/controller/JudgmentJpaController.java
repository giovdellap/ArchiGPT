/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller;

import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.controller.interfaces.JudgmentJpaControllerIntf;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ir.shenakht.paint.domain.JudgeUser;
import ir.shenakht.paint.domain.Judgment;
import ir.shenakht.paint.domain.Participants;
import ir.shenakht.paint.domain.Question;
import ir.shenakht.paint.domain.Racing;
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
public class JudgmentJpaController implements JudgmentJpaControllerIntf {

    public JudgmentJpaController() {
    }

    @PersistenceContext(unitName = "PSE", type = PersistenceContextType.TRANSACTION)
    private EntityManager emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public Judgment create(Judgment judgment) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            JudgeUser judgeUserId = judgment.getJudgeUserId();
            if (judgeUserId != null) {
                judgeUserId = em.getReference(judgeUserId.getClass(), judgeUserId.getId());
                judgment.setJudgeUserId(judgeUserId);
            }
            Participants participantsId = judgment.getParticipantsId();
            if (participantsId != null) {
                participantsId = em.getReference(participantsId.getClass(), participantsId.getId());
                judgment.setParticipantsId(participantsId);
            }
            Question questionId = judgment.getQuestionId();
            if (questionId != null) {
                questionId = em.getReference(questionId.getClass(), questionId.getId());
                judgment.setQuestionId(questionId);
            }
            Racing racingId = judgment.getRacingId();
            if (racingId != null) {
                racingId = em.getReference(racingId.getClass(), racingId.getId());
                judgment.setRacingId(racingId);
            }
            em.persist(judgment);
            if (judgeUserId != null) {
                judgeUserId.getJudgmentList().add(judgment);
                judgeUserId = em.merge(judgeUserId);
            }
            if (participantsId != null) {
                participantsId.getJudgmentList().add(judgment);
                participantsId = em.merge(participantsId);
            }
            if (questionId != null) {
                questionId.getJudgmentList().add(judgment);
                questionId = em.merge(questionId);
            }
            if (racingId != null) {
                racingId.getJudgmentList().add(judgment);
                racingId = em.merge(racingId);
            }
            return judgment;
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
    public void edit(Judgment judgment) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Judgment persistentJudgment = em.find(Judgment.class, judgment.getId());
            JudgeUser judgeUserIdOld = persistentJudgment.getJudgeUserId();
            JudgeUser judgeUserIdNew = judgment.getJudgeUserId();
            Participants participantsIdOld = persistentJudgment.getParticipantsId();
            Participants participantsIdNew = judgment.getParticipantsId();
            Question questionIdOld = persistentJudgment.getQuestionId();
            Question questionIdNew = judgment.getQuestionId();
            Racing racingIdOld = persistentJudgment.getRacingId();
            Racing racingIdNew = judgment.getRacingId();
            if (judgeUserIdNew != null) {
                judgeUserIdNew = em.getReference(judgeUserIdNew.getClass(), judgeUserIdNew.getId());
                judgment.setJudgeUserId(judgeUserIdNew);
            }
            if (participantsIdNew != null) {
                participantsIdNew = em.getReference(participantsIdNew.getClass(), participantsIdNew.getId());
                judgment.setParticipantsId(participantsIdNew);
            }
            if (questionIdNew != null) {
                questionIdNew = em.getReference(questionIdNew.getClass(), questionIdNew.getId());
                judgment.setQuestionId(questionIdNew);
            }
            if (racingIdNew != null) {
                racingIdNew = em.getReference(racingIdNew.getClass(), racingIdNew.getId());
                judgment.setRacingId(racingIdNew);
            }
            judgment = em.merge(judgment);
            if (judgeUserIdOld != null && !judgeUserIdOld.equals(judgeUserIdNew)) {
                judgeUserIdOld.getJudgmentList().remove(judgment);
                judgeUserIdOld = em.merge(judgeUserIdOld);
            }
            if (judgeUserIdNew != null && !judgeUserIdNew.equals(judgeUserIdOld)) {
                judgeUserIdNew.getJudgmentList().add(judgment);
                judgeUserIdNew = em.merge(judgeUserIdNew);
            }
            if (participantsIdOld != null && !participantsIdOld.equals(participantsIdNew)) {
                participantsIdOld.getJudgmentList().remove(judgment);
                participantsIdOld = em.merge(participantsIdOld);
            }
            if (participantsIdNew != null && !participantsIdNew.equals(participantsIdOld)) {
                participantsIdNew.getJudgmentList().add(judgment);
                participantsIdNew = em.merge(participantsIdNew);
            }
            if (questionIdOld != null && !questionIdOld.equals(questionIdNew)) {
                questionIdOld.getJudgmentList().remove(judgment);
                questionIdOld = em.merge(questionIdOld);
            }
            if (questionIdNew != null && !questionIdNew.equals(questionIdOld)) {
                questionIdNew.getJudgmentList().add(judgment);
                questionIdNew = em.merge(questionIdNew);
            }
            if (racingIdOld != null && !racingIdOld.equals(racingIdNew)) {
                racingIdOld.getJudgmentList().remove(judgment);
                racingIdOld = em.merge(racingIdOld);
            }
            if (racingIdNew != null && !racingIdNew.equals(racingIdOld)) {
                racingIdNew.getJudgmentList().add(judgment);
                racingIdNew = em.merge(racingIdNew);
            }
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = judgment.getId();
                if (findJudgment(id) == null) {
                    throw new NonexistentEntityException("The judgment with id " + id + " no longer exists.");
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
            Judgment judgment;
            try {
                judgment = em.getReference(Judgment.class, id);
                judgment.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The judgment with id " + id + " no longer exists.", enfe);
            }
            JudgeUser judgeUserId = judgment.getJudgeUserId();
            if (judgeUserId != null) {
                judgeUserId.getJudgmentList().remove(judgment);
                judgeUserId = em.merge(judgeUserId);
            }
            Participants participantsId = judgment.getParticipantsId();
            if (participantsId != null) {
                participantsId.getJudgmentList().remove(judgment);
                participantsId = em.merge(participantsId);
            }
            Question questionId = judgment.getQuestionId();
            if (questionId != null) {
                questionId.getJudgmentList().remove(judgment);
                questionId = em.merge(questionId);
            }
            Racing racingId = judgment.getRacingId();
            if (racingId != null) {
                racingId.getJudgmentList().remove(judgment);
                racingId = em.merge(racingId);
            }
            em.remove(judgment);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.flush();
            }
        }
    }

    @Override
    public List<Judgment> findJudgmentEntities() {
        return findJudgmentEntities(true, -1, -1);
    }

    @Override
    public List<Judgment> findJudgmentEntities(int maxResults, int firstResult) {
        return findJudgmentEntities(false, maxResults, firstResult);
    }

    private List<Judgment> findJudgmentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Judgment.class));
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
    public Judgment findJudgment(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Judgment.class, id);
        } finally {
            em.flush();
        }
    }

    @Override
    public int getJudgmentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Judgment> rt = cq.from(Judgment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.flush();
        }
    }

}
