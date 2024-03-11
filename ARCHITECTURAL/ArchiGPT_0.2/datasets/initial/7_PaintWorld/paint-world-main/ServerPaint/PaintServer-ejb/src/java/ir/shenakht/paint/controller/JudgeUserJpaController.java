/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller;

import ir.shenakht.paint.controller.exceptions.IllegalOrphanException;
import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.controller.interfaces.JudgeUserJpaControllerIntf;
import ir.shenakht.paint.domain.JudgeUser;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ir.shenakht.paint.domain.Judgment;
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
public class JudgeUserJpaController implements JudgeUserJpaControllerIntf {

    public JudgeUserJpaController() {
    }

    @PersistenceContext(unitName = "PSE", type = PersistenceContextType.TRANSACTION)
    private EntityManager emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public JudgeUser create(JudgeUser judgeUser) throws RollbackFailureException, Exception {
        if (judgeUser.getJudgmentList() == null) {
            judgeUser.setJudgmentList(new ArrayList<Judgment>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            List<Judgment> attachedJudgmentList = new ArrayList<Judgment>();
            for (Judgment judgmentListJudgmentToAttach : judgeUser.getJudgmentList()) {
                judgmentListJudgmentToAttach = em.getReference(judgmentListJudgmentToAttach.getClass(), judgmentListJudgmentToAttach.getId());
                attachedJudgmentList.add(judgmentListJudgmentToAttach);
            }
            judgeUser.setJudgmentList(attachedJudgmentList);
            em.persist(judgeUser);
            for (Judgment judgmentListJudgment : judgeUser.getJudgmentList()) {
                JudgeUser oldJudgeUserIdOfJudgmentListJudgment = judgmentListJudgment.getJudgeUserId();
                judgmentListJudgment.setJudgeUserId(judgeUser);
                judgmentListJudgment = em.merge(judgmentListJudgment);
                if (oldJudgeUserIdOfJudgmentListJudgment != null) {
                    oldJudgeUserIdOfJudgmentListJudgment.getJudgmentList().remove(judgmentListJudgment);
                    oldJudgeUserIdOfJudgmentListJudgment = em.merge(oldJudgeUserIdOfJudgmentListJudgment);
                }
            }
            return judgeUser;
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
    public void edit(JudgeUser judgeUser) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            JudgeUser persistentJudgeUser = em.find(JudgeUser.class, judgeUser.getId());
            List<Judgment> judgmentListOld = persistentJudgeUser.getJudgmentList();
            List<Judgment> judgmentListNew = judgeUser.getJudgmentList();
            List<String> illegalOrphanMessages = null;
            for (Judgment judgmentListOldJudgment : judgmentListOld) {
                if (!judgmentListNew.contains(judgmentListOldJudgment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Judgment " + judgmentListOldJudgment + " since its judgeUserId field is not nullable.");
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
            judgeUser.setJudgmentList(judgmentListNew);
            judgeUser = em.merge(judgeUser);
            for (Judgment judgmentListNewJudgment : judgmentListNew) {
                if (!judgmentListOld.contains(judgmentListNewJudgment)) {
                    JudgeUser oldJudgeUserIdOfJudgmentListNewJudgment = judgmentListNewJudgment.getJudgeUserId();
                    judgmentListNewJudgment.setJudgeUserId(judgeUser);
                    judgmentListNewJudgment = em.merge(judgmentListNewJudgment);
                    if (oldJudgeUserIdOfJudgmentListNewJudgment != null && !oldJudgeUserIdOfJudgmentListNewJudgment.equals(judgeUser)) {
                        oldJudgeUserIdOfJudgmentListNewJudgment.getJudgmentList().remove(judgmentListNewJudgment);
                        oldJudgeUserIdOfJudgmentListNewJudgment = em.merge(oldJudgeUserIdOfJudgmentListNewJudgment);
                    }
                }
            }
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = judgeUser.getId();
                if (findJudgeUser(id) == null) {
                    throw new NonexistentEntityException("The judgeUser with id " + id + " no longer exists.");
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
            JudgeUser judgeUser;
            try {
                judgeUser = em.getReference(JudgeUser.class, id);
                judgeUser.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The judgeUser with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Judgment> judgmentListOrphanCheck = judgeUser.getJudgmentList();
            for (Judgment judgmentListOrphanCheckJudgment : judgmentListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This JudgeUser (" + judgeUser + ") cannot be destroyed since the Judgment " + judgmentListOrphanCheckJudgment + " in its judgmentList field has a non-nullable judgeUserId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(judgeUser);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.flush();
            }
        }
    }

    @Override
    public List<JudgeUser> findJudgeUserEntities() {
        return findJudgeUserEntities(true, -1, -1);
    }

    @Override
    public List<JudgeUser> findJudgeUserEntities(int maxResults, int firstResult) {
        return findJudgeUserEntities(false, maxResults, firstResult);
    }

    private List<JudgeUser> findJudgeUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(JudgeUser.class));
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
    public JudgeUser findJudgeUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(JudgeUser.class, id);
        } finally {
            em.flush();
        }
    }

    @Override
    public int getJudgeUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<JudgeUser> rt = cq.from(JudgeUser.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.flush();
        }
    }

}
