/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller;

import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.controller.interfaces.CityJpaControllerIntf;
import ir.shenakht.paint.domain.City;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ir.shenakht.paint.domain.Provice;
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
public class CityJpaController implements CityJpaControllerIntf {

    public CityJpaController() {
    }

    @PersistenceContext(unitName = "PSE", type = PersistenceContextType.TRANSACTION)
    private EntityManager emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public City create(City city) throws RollbackFailureException, Exception {
        if (city.getParticipantsList() == null) {
            city.setParticipantsList(new ArrayList<Participants>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            Provice proviceId = city.getProviceId();
            if (proviceId != null) {
                proviceId = em.getReference(proviceId.getClass(), proviceId.getId());
                city.setProviceId(proviceId);
            }
            List<Participants> attachedParticipantsList = new ArrayList<Participants>();
            for (Participants participantsListParticipantsToAttach : city.getParticipantsList()) {
                participantsListParticipantsToAttach = em.getReference(participantsListParticipantsToAttach.getClass(), participantsListParticipantsToAttach.getId());
                attachedParticipantsList.add(participantsListParticipantsToAttach);
            }
            city.setParticipantsList(attachedParticipantsList);
            em.persist(city);
            if (proviceId != null) {
                proviceId.getCityList().add(city);
                proviceId = em.merge(proviceId);
            }
            for (Participants participantsListParticipants : city.getParticipantsList()) {
                City oldCityIdOfParticipantsListParticipants = participantsListParticipants.getCityId();
                participantsListParticipants.setCityId(city);
                participantsListParticipants = em.merge(participantsListParticipants);
                if (oldCityIdOfParticipantsListParticipants != null) {
                    oldCityIdOfParticipantsListParticipants.getParticipantsList().remove(participantsListParticipants);
                    oldCityIdOfParticipantsListParticipants = em.merge(oldCityIdOfParticipantsListParticipants);
                }
            }
            return city;
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
    public void edit(City city) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            City persistentCity = em.find(City.class, city.getId());
            Provice proviceIdOld = persistentCity.getProviceId();
            Provice proviceIdNew = city.getProviceId();
            List<Participants> participantsListOld = persistentCity.getParticipantsList();
            List<Participants> participantsListNew = city.getParticipantsList();
            if (proviceIdNew != null) {
                proviceIdNew = em.getReference(proviceIdNew.getClass(), proviceIdNew.getId());
                city.setProviceId(proviceIdNew);
            }
            List<Participants> attachedParticipantsListNew = new ArrayList<Participants>();
            for (Participants participantsListNewParticipantsToAttach : participantsListNew) {
                participantsListNewParticipantsToAttach = em.getReference(participantsListNewParticipantsToAttach.getClass(), participantsListNewParticipantsToAttach.getId());
                attachedParticipantsListNew.add(participantsListNewParticipantsToAttach);
            }
            participantsListNew = attachedParticipantsListNew;
            city.setParticipantsList(participantsListNew);
            city = em.merge(city);
            if (proviceIdOld != null && !proviceIdOld.equals(proviceIdNew)) {
                proviceIdOld.getCityList().remove(city);
                proviceIdOld = em.merge(proviceIdOld);
            }
            if (proviceIdNew != null && !proviceIdNew.equals(proviceIdOld)) {
                proviceIdNew.getCityList().add(city);
                proviceIdNew = em.merge(proviceIdNew);
            }
            for (Participants participantsListOldParticipants : participantsListOld) {
                if (!participantsListNew.contains(participantsListOldParticipants)) {
                    participantsListOldParticipants.setCityId(null);
                    participantsListOldParticipants = em.merge(participantsListOldParticipants);
                }
            }
            for (Participants participantsListNewParticipants : participantsListNew) {
                if (!participantsListOld.contains(participantsListNewParticipants)) {
                    City oldCityIdOfParticipantsListNewParticipants = participantsListNewParticipants.getCityId();
                    participantsListNewParticipants.setCityId(city);
                    participantsListNewParticipants = em.merge(participantsListNewParticipants);
                    if (oldCityIdOfParticipantsListNewParticipants != null && !oldCityIdOfParticipantsListNewParticipants.equals(city)) {
                        oldCityIdOfParticipantsListNewParticipants.getParticipantsList().remove(participantsListNewParticipants);
                        oldCityIdOfParticipantsListNewParticipants = em.merge(oldCityIdOfParticipantsListNewParticipants);
                    }
                }
            }
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = city.getId();
                if (findCity(id) == null) {
                    throw new NonexistentEntityException("The city with id " + id + " no longer exists.");
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
            City city;
            try {
                city = em.getReference(City.class, id);
                city.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The city with id " + id + " no longer exists.", enfe);
            }
            Provice proviceId = city.getProviceId();
            if (proviceId != null) {
                proviceId.getCityList().remove(city);
                proviceId = em.merge(proviceId);
            }
            List<Participants> participantsList = city.getParticipantsList();
            for (Participants participantsListParticipants : participantsList) {
                participantsListParticipants.setCityId(null);
                participantsListParticipants = em.merge(participantsListParticipants);
            }
            em.remove(city);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.flush();
            }
        }
    }

    @Override
    public List<City> findCityEntities() {
        return findCityEntities(true, -1, -1);
    }

    @Override
    public List<City> findCityEntities(int maxResults, int firstResult) {
        return findCityEntities(false, maxResults, firstResult);
    }

    private List<City> findCityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(City.class));
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
    public City findCity(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(City.class, id);
        } finally {
            em.flush();
        }
    }

    @Override
    public int getCityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<City> rt = cq.from(City.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.flush();
        }
    }

}
