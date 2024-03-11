/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller;

import ir.shenakht.paint.controller.exceptions.IllegalOrphanException;
import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.controller.interfaces.QuestionJpaControllerIntf;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ir.shenakht.paint.domain.Judgment;
import ir.shenakht.paint.domain.Question;
import java.util.ArrayList;
import java.util.List;
import ir.shenakht.paint.domain.ScaleQuestionForRacing;
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
public class QuestionJpaController implements QuestionJpaControllerIntf {

    public QuestionJpaController() {
    }

    @PersistenceContext(unitName = "PSE", type = PersistenceContextType.TRANSACTION)
    private EntityManager emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public Question create(Question question) throws RollbackFailureException, Exception {
        if (question.getJudgmentList() == null) {
            question.setJudgmentList(new ArrayList<Judgment>());
        }
        if (question.getScaleQuestionForRacingList() == null) {
            question.setScaleQuestionForRacingList(new ArrayList<ScaleQuestionForRacing>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            List<Judgment> attachedJudgmentList = new ArrayList<Judgment>();
            for (Judgment judgmentListJudgmentToAttach : question.getJudgmentList()) {
                judgmentListJudgmentToAttach = em.getReference(judgmentListJudgmentToAttach.getClass(), judgmentListJudgmentToAttach.getId());
                attachedJudgmentList.add(judgmentListJudgmentToAttach);
            }
            question.setJudgmentList(attachedJudgmentList);
            List<ScaleQuestionForRacing> attachedScaleQuestionForRacingList = new ArrayList<ScaleQuestionForRacing>();
            for (ScaleQuestionForRacing scaleQuestionForRacingListScaleQuestionForRacingToAttach : question.getScaleQuestionForRacingList()) {
                scaleQuestionForRacingListScaleQuestionForRacingToAttach = em.getReference(scaleQuestionForRacingListScaleQuestionForRacingToAttach.getClass(), scaleQuestionForRacingListScaleQuestionForRacingToAttach.getId());
                attachedScaleQuestionForRacingList.add(scaleQuestionForRacingListScaleQuestionForRacingToAttach);
            }
            question.setScaleQuestionForRacingList(attachedScaleQuestionForRacingList);
            em.persist(question);
            for (Judgment judgmentListJudgment : question.getJudgmentList()) {
                Question oldQuestionIdOfJudgmentListJudgment = judgmentListJudgment.getQuestionId();
                judgmentListJudgment.setQuestionId(question);
                judgmentListJudgment = em.merge(judgmentListJudgment);
                if (oldQuestionIdOfJudgmentListJudgment != null) {
                    oldQuestionIdOfJudgmentListJudgment.getJudgmentList().remove(judgmentListJudgment);
                    oldQuestionIdOfJudgmentListJudgment = em.merge(oldQuestionIdOfJudgmentListJudgment);
                }
            }
            for (ScaleQuestionForRacing scaleQuestionForRacingListScaleQuestionForRacing : question.getScaleQuestionForRacingList()) {
                Question oldQuestionIdOfScaleQuestionForRacingListScaleQuestionForRacing = scaleQuestionForRacingListScaleQuestionForRacing.getQuestionId();
                scaleQuestionForRacingListScaleQuestionForRacing.setQuestionId(question);
                scaleQuestionForRacingListScaleQuestionForRacing = em.merge(scaleQuestionForRacingListScaleQuestionForRacing);
                if (oldQuestionIdOfScaleQuestionForRacingListScaleQuestionForRacing != null) {
                    oldQuestionIdOfScaleQuestionForRacingListScaleQuestionForRacing.getScaleQuestionForRacingList().remove(scaleQuestionForRacingListScaleQuestionForRacing);
                    oldQuestionIdOfScaleQuestionForRacingListScaleQuestionForRacing = em.merge(oldQuestionIdOfScaleQuestionForRacingListScaleQuestionForRacing);
                }
            }
            return question;
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
    public void edit(Question question) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Question persistentQuestion = em.find(Question.class, question.getId());
            List<Judgment> judgmentListOld = persistentQuestion.getJudgmentList();
            List<Judgment> judgmentListNew = question.getJudgmentList();
            List<ScaleQuestionForRacing> scaleQuestionForRacingListOld = persistentQuestion.getScaleQuestionForRacingList();
            List<ScaleQuestionForRacing> scaleQuestionForRacingListNew = question.getScaleQuestionForRacingList();
            List<String> illegalOrphanMessages = null;
            for (Judgment judgmentListOldJudgment : judgmentListOld) {
                if (!judgmentListNew.contains(judgmentListOldJudgment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Judgment " + judgmentListOldJudgment + " since its questionId field is not nullable.");
                }
            }
            for (ScaleQuestionForRacing scaleQuestionForRacingListOldScaleQuestionForRacing : scaleQuestionForRacingListOld) {
                if (!scaleQuestionForRacingListNew.contains(scaleQuestionForRacingListOldScaleQuestionForRacing)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ScaleQuestionForRacing " + scaleQuestionForRacingListOldScaleQuestionForRacing + " since its questionId field is not nullable.");
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
            question.setJudgmentList(judgmentListNew);
            List<ScaleQuestionForRacing> attachedScaleQuestionForRacingListNew = new ArrayList<ScaleQuestionForRacing>();
            for (ScaleQuestionForRacing scaleQuestionForRacingListNewScaleQuestionForRacingToAttach : scaleQuestionForRacingListNew) {
                scaleQuestionForRacingListNewScaleQuestionForRacingToAttach = em.getReference(scaleQuestionForRacingListNewScaleQuestionForRacingToAttach.getClass(), scaleQuestionForRacingListNewScaleQuestionForRacingToAttach.getId());
                attachedScaleQuestionForRacingListNew.add(scaleQuestionForRacingListNewScaleQuestionForRacingToAttach);
            }
            scaleQuestionForRacingListNew = attachedScaleQuestionForRacingListNew;
            question.setScaleQuestionForRacingList(scaleQuestionForRacingListNew);
            question = em.merge(question);
            for (Judgment judgmentListNewJudgment : judgmentListNew) {
                if (!judgmentListOld.contains(judgmentListNewJudgment)) {
                    Question oldQuestionIdOfJudgmentListNewJudgment = judgmentListNewJudgment.getQuestionId();
                    judgmentListNewJudgment.setQuestionId(question);
                    judgmentListNewJudgment = em.merge(judgmentListNewJudgment);
                    if (oldQuestionIdOfJudgmentListNewJudgment != null && !oldQuestionIdOfJudgmentListNewJudgment.equals(question)) {
                        oldQuestionIdOfJudgmentListNewJudgment.getJudgmentList().remove(judgmentListNewJudgment);
                        oldQuestionIdOfJudgmentListNewJudgment = em.merge(oldQuestionIdOfJudgmentListNewJudgment);
                    }
                }
            }
            for (ScaleQuestionForRacing scaleQuestionForRacingListNewScaleQuestionForRacing : scaleQuestionForRacingListNew) {
                if (!scaleQuestionForRacingListOld.contains(scaleQuestionForRacingListNewScaleQuestionForRacing)) {
                    Question oldQuestionIdOfScaleQuestionForRacingListNewScaleQuestionForRacing = scaleQuestionForRacingListNewScaleQuestionForRacing.getQuestionId();
                    scaleQuestionForRacingListNewScaleQuestionForRacing.setQuestionId(question);
                    scaleQuestionForRacingListNewScaleQuestionForRacing = em.merge(scaleQuestionForRacingListNewScaleQuestionForRacing);
                    if (oldQuestionIdOfScaleQuestionForRacingListNewScaleQuestionForRacing != null && !oldQuestionIdOfScaleQuestionForRacingListNewScaleQuestionForRacing.equals(question)) {
                        oldQuestionIdOfScaleQuestionForRacingListNewScaleQuestionForRacing.getScaleQuestionForRacingList().remove(scaleQuestionForRacingListNewScaleQuestionForRacing);
                        oldQuestionIdOfScaleQuestionForRacingListNewScaleQuestionForRacing = em.merge(oldQuestionIdOfScaleQuestionForRacingListNewScaleQuestionForRacing);
                    }
                }
            }
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = question.getId();
                if (findQuestion(id) == null) {
                    throw new NonexistentEntityException("The question with id " + id + " no longer exists.");
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
            Question question;
            try {
                question = em.getReference(Question.class, id);
                question.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The question with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Judgment> judgmentListOrphanCheck = question.getJudgmentList();
            for (Judgment judgmentListOrphanCheckJudgment : judgmentListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Question (" + question + ") cannot be destroyed since the Judgment " + judgmentListOrphanCheckJudgment + " in its judgmentList field has a non-nullable questionId field.");
            }
            List<ScaleQuestionForRacing> scaleQuestionForRacingListOrphanCheck = question.getScaleQuestionForRacingList();
            for (ScaleQuestionForRacing scaleQuestionForRacingListOrphanCheckScaleQuestionForRacing : scaleQuestionForRacingListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Question (" + question + ") cannot be destroyed since the ScaleQuestionForRacing " + scaleQuestionForRacingListOrphanCheckScaleQuestionForRacing + " in its scaleQuestionForRacingList field has a non-nullable questionId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(question);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.flush();
            }
        }
    }

    @Override
    public List<Question> findQuestionEntities() {
        return findQuestionEntities(true, -1, -1);
    }

    @Override
    public List<Question> findQuestionEntities(int maxResults, int firstResult) {
        return findQuestionEntities(false, maxResults, firstResult);
    }

    private List<Question> findQuestionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Question.class));
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
    public Question findQuestion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Question.class, id);
        } finally {
            em.flush();
        }
    }

    @Override
    public int getQuestionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Question> rt = cq.from(Question.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.flush();
        }
    }

}
