/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller;

import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.controller.interfaces.ScaleQuestionForRacingJpaControllerIntf;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ir.shenakht.paint.domain.Question;
import ir.shenakht.paint.domain.Racing;
import ir.shenakht.paint.domain.ScaleQuestionForRacing;
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
public class ScaleQuestionForRacingJpaController implements ScaleQuestionForRacingJpaControllerIntf {

    public ScaleQuestionForRacingJpaController() {
    }

    @PersistenceContext(unitName = "PSE", type = PersistenceContextType.TRANSACTION)
    private EntityManager emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public ScaleQuestionForRacing create(ScaleQuestionForRacing scaleQuestionForRacing) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Question questionId = scaleQuestionForRacing.getQuestionId();
            if (questionId != null) {
                questionId = em.getReference(questionId.getClass(), questionId.getId());
                scaleQuestionForRacing.setQuestionId(questionId);
            }
            Racing racingId = scaleQuestionForRacing.getRacingId();
            if (racingId != null) {
                racingId = em.getReference(racingId.getClass(), racingId.getId());
                scaleQuestionForRacing.setRacingId(racingId);
            }
            em.persist(scaleQuestionForRacing);
            if (questionId != null) {
                questionId.getScaleQuestionForRacingList().add(scaleQuestionForRacing);
                questionId = em.merge(questionId);
            }
            if (racingId != null) {
                racingId.getScaleQuestionForRacingList().add(scaleQuestionForRacing);
                racingId = em.merge(racingId);
            }
            return scaleQuestionForRacing;
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
    public void edit(ScaleQuestionForRacing scaleQuestionForRacing) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            ScaleQuestionForRacing persistentScaleQuestionForRacing = em.find(ScaleQuestionForRacing.class, scaleQuestionForRacing.getId());
            Question questionIdOld = persistentScaleQuestionForRacing.getQuestionId();
            Question questionIdNew = scaleQuestionForRacing.getQuestionId();
            Racing racingIdOld = persistentScaleQuestionForRacing.getRacingId();
            Racing racingIdNew = scaleQuestionForRacing.getRacingId();
            if (questionIdNew != null) {
                questionIdNew = em.getReference(questionIdNew.getClass(), questionIdNew.getId());
                scaleQuestionForRacing.setQuestionId(questionIdNew);
            }
            if (racingIdNew != null) {
                racingIdNew = em.getReference(racingIdNew.getClass(), racingIdNew.getId());
                scaleQuestionForRacing.setRacingId(racingIdNew);
            }
            scaleQuestionForRacing = em.merge(scaleQuestionForRacing);
            if (questionIdOld != null && !questionIdOld.equals(questionIdNew)) {
                questionIdOld.getScaleQuestionForRacingList().remove(scaleQuestionForRacing);
                questionIdOld = em.merge(questionIdOld);
            }
            if (questionIdNew != null && !questionIdNew.equals(questionIdOld)) {
                questionIdNew.getScaleQuestionForRacingList().add(scaleQuestionForRacing);
                questionIdNew = em.merge(questionIdNew);
            }
            if (racingIdOld != null && !racingIdOld.equals(racingIdNew)) {
                racingIdOld.getScaleQuestionForRacingList().remove(scaleQuestionForRacing);
                racingIdOld = em.merge(racingIdOld);
            }
            if (racingIdNew != null && !racingIdNew.equals(racingIdOld)) {
                racingIdNew.getScaleQuestionForRacingList().add(scaleQuestionForRacing);
                racingIdNew = em.merge(racingIdNew);
            }
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = scaleQuestionForRacing.getId();
                if (findScaleQuestionForRacing(id) == null) {
                    throw new NonexistentEntityException("The scaleQuestionForRacing with id " + id + " no longer exists.");
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
            ScaleQuestionForRacing scaleQuestionForRacing;
            try {
                scaleQuestionForRacing = em.getReference(ScaleQuestionForRacing.class, id);
                scaleQuestionForRacing.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The scaleQuestionForRacing with id " + id + " no longer exists.", enfe);
            }
            Question questionId = scaleQuestionForRacing.getQuestionId();
            if (questionId != null) {
                questionId.getScaleQuestionForRacingList().remove(scaleQuestionForRacing);
                questionId = em.merge(questionId);
            }
            Racing racingId = scaleQuestionForRacing.getRacingId();
            if (racingId != null) {
                racingId.getScaleQuestionForRacingList().remove(scaleQuestionForRacing);
                racingId = em.merge(racingId);
            }
            em.remove(scaleQuestionForRacing);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.flush();
            }
        }
    }

    @Override
    public List<ScaleQuestionForRacing> findScaleQuestionForRacingEntities() {
        return findScaleQuestionForRacingEntities(true, -1, -1);
    }

    @Override
    public List<ScaleQuestionForRacing> findScaleQuestionForRacingEntities(int maxResults, int firstResult) {
        return findScaleQuestionForRacingEntities(false, maxResults, firstResult);
    }

    private List<ScaleQuestionForRacing> findScaleQuestionForRacingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ScaleQuestionForRacing.class));
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
    public ScaleQuestionForRacing findScaleQuestionForRacing(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ScaleQuestionForRacing.class, id);
        } finally {
            em.flush();
        }
    }

    @Override
    public int getScaleQuestionForRacingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ScaleQuestionForRacing> rt = cq.from(ScaleQuestionForRacing.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.flush();
        }
    }

}
