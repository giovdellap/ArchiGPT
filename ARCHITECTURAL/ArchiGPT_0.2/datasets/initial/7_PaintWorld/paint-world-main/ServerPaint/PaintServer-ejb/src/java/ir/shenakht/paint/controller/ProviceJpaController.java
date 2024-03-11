/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller;

import ir.shenakht.paint.controller.exceptions.IllegalOrphanException;
import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.controller.interfaces.ProviceJpaControllerIntf;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ir.shenakht.paint.domain.City;
import ir.shenakht.paint.domain.Provice;
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
public class ProviceJpaController implements ProviceJpaControllerIntf {

    public ProviceJpaController() {
    }

    @PersistenceContext(unitName = "PSE", type = PersistenceContextType.TRANSACTION)
    private EntityManager emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public Provice create(Provice provice) throws RollbackFailureException, Exception {
        if (provice.getCityList() == null) {
            provice.setCityList(new ArrayList<City>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            List<City> attachedCityList = new ArrayList<City>();
            for (City cityListCityToAttach : provice.getCityList()) {
                cityListCityToAttach = em.getReference(cityListCityToAttach.getClass(), cityListCityToAttach.getId());
                attachedCityList.add(cityListCityToAttach);
            }
            provice.setCityList(attachedCityList);
            em.persist(provice);
            for (City cityListCity : provice.getCityList()) {
                Provice oldProviceIdOfCityListCity = cityListCity.getProviceId();
                cityListCity.setProviceId(provice);
                cityListCity = em.merge(cityListCity);
                if (oldProviceIdOfCityListCity != null) {
                    oldProviceIdOfCityListCity.getCityList().remove(cityListCity);
                    oldProviceIdOfCityListCity = em.merge(oldProviceIdOfCityListCity);
                }
            }
            return provice;
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
    public void edit(Provice provice) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Provice persistentProvice = em.find(Provice.class, provice.getId());
            List<City> cityListOld = persistentProvice.getCityList();
            List<City> cityListNew = provice.getCityList();
            List<String> illegalOrphanMessages = null;
            for (City cityListOldCity : cityListOld) {
                if (!cityListNew.contains(cityListOldCity)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain City " + cityListOldCity + " since its proviceId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<City> attachedCityListNew = new ArrayList<City>();
            for (City cityListNewCityToAttach : cityListNew) {
                cityListNewCityToAttach = em.getReference(cityListNewCityToAttach.getClass(), cityListNewCityToAttach.getId());
                attachedCityListNew.add(cityListNewCityToAttach);
            }
            cityListNew = attachedCityListNew;
            provice.setCityList(cityListNew);
            provice = em.merge(provice);
            for (City cityListNewCity : cityListNew) {
                if (!cityListOld.contains(cityListNewCity)) {
                    Provice oldProviceIdOfCityListNewCity = cityListNewCity.getProviceId();
                    cityListNewCity.setProviceId(provice);
                    cityListNewCity = em.merge(cityListNewCity);
                    if (oldProviceIdOfCityListNewCity != null && !oldProviceIdOfCityListNewCity.equals(provice)) {
                        oldProviceIdOfCityListNewCity.getCityList().remove(cityListNewCity);
                        oldProviceIdOfCityListNewCity = em.merge(oldProviceIdOfCityListNewCity);
                    }
                }
            }
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = provice.getId();
                if (findProvice(id) == null) {
                    throw new NonexistentEntityException("The provice with id " + id + " no longer exists.");
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
            Provice provice;
            try {
                provice = em.getReference(Provice.class, id);
                provice.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The provice with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<City> cityListOrphanCheck = provice.getCityList();
            for (City cityListOrphanCheckCity : cityListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Provice (" + provice + ") cannot be destroyed since the City " + cityListOrphanCheckCity + " in its cityList field has a non-nullable proviceId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(provice);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.flush();
            }
        }
    }

    @Override
    public List<Provice> findProviceEntities() {
        return findProviceEntities(true, -1, -1);
    }

    @Override
    public List<Provice> findProviceEntities(int maxResults, int firstResult) {
        return findProviceEntities(false, maxResults, firstResult);
    }

    private List<Provice> findProviceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Provice.class));
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
    public Provice findProvice(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Provice.class, id);
        } finally {
            em.flush();
        }
    }

    @Override
    public int getProviceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Provice> rt = cq.from(Provice.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.flush();
        }
    }

}
