/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic;

import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.controller.interfaces.ParticipantsJpaControllerIntf;
import ir.shenakht.paint.domain.City;
import ir.shenakht.paint.domain.Participants;
import ir.shenakht.paint.domain.Racing;
import ir.shenakht.paint.domain.User;
import ir.shenakht.paint.enums.ConditionParticipants;
import ir.shenakht.paint.logic.interfaces.CityLogicIntf;
import ir.shenakht.paint.logic.interfaces.ParticipantsLogicIntf;
import ir.shenakht.paint.logic.interfaces.RacingLogicIntf;
import ir.shenakht.paint.logic.interfaces.UserLogicIntf;
import ir.shenakht.paint.util.ConstantValues;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import sun.security.krb5.internal.crypto.Des3;

/**
 *
 * @author hossien
 */
@Stateless
public class ParticipantsLogic implements ParticipantsLogicIntf {

    @EJB
    private ParticipantsJpaControllerIntf pj;

    @EJB
    private UserLogicIntf ul;

    @EJB
    private CityLogicIntf cl;

    @EJB
    private RacingLogicIntf rl;

    @Override
    public Participants createParticipants(String userCode, Integer racingID, Participants participants) {
        Racing racing = rl.findRaingWithId(racingID);
        User user = ul.findUserWithUserCode(userCode);
        if (user != null && racing != null) {
            try {
                participants.setUserId(user);
                participants.setRacingId(racing);
                if (participants.getCityId() != null) {
                    City city = cl.findCity(participants.getCityId().getId());
                    participants.setCityId(city);
                }
                participants.setConditionType(ConditionParticipants.UNVERIFIED.ordinal());
                participants.setIsSelective(false);
                participants.setStatus("دریافت شد");
                participants = pj.create(participants);
                return participants;
            } catch (Exception ex) {
                Logger.getLogger(ParticipantsLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public boolean updateParticipants(Participants participants) {
        try {
            Participants participantsOld = findParitcipants(participants.getId());
            if (participantsOld != null) {
                if (participants.getIsSelective() != null) {
                    participantsOld.setIsSelective(participants.getIsSelective());
                }
                if (participants.getAddress() != null) {
                    participantsOld.setAddress(participants.getAddress());
                }
                if (participants.getAge() != null) {
                    participantsOld.setAge(participants.getAge());
                }
                if (participants.getCityId() != null) {
                    participantsOld.setCityId(participants.getCityId());
                }
                if (participants.getFirstName() != null) {
                    participantsOld.setFirstName(participants.getFirstName());
                }
                if (participants.getLastName() != null) {
                    participantsOld.setLastName(participants.getLastName());
                }
                if (participants.getRacingId() != null) {
                    participantsOld.setRacingId(participants.getRacingId());
                }
                if (participants.getTotalScore() != null) {
                    participantsOld.setTotalScore(participants.getTotalScore());
                }
                if (participants.getUrl() != null) {
                    participantsOld.setUrl(participants.getUrl());
                }
                if (participants.getConditionType() != null) {
                    participantsOld.setConditionType(participants.getConditionType());
                }
                if (participants.getDisplayTime() != null) {
                    participantsOld.setDisplayTime(participants.getDisplayTime());
                }
                if (participants.getJudgmentList() != null) {
                    participantsOld.getJudgmentList().clear();
                    participantsOld.getJudgmentList().addAll(participants.getJudgmentList());
                }
                pj.edit(participantsOld);
                return true;
            }
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ParticipantsLogic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(ParticipantsLogic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ParticipantsLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteParticipants(Integer id) {
        Participants participants = findParitcipants(id);
        if (participants != null) {
            try {
                pj.destroy(id);
                return true;
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ParticipantsLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RollbackFailureException ex) {
                Logger.getLogger(ParticipantsLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ParticipantsLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public Participants findParitcipants(Integer id) {
        return pj.findParticipants(id);
    }

    @Override
    public List<Participants> findListParticipantsWithUserCode(String userCode) {
        User user = ul.findUserWithUserCode(userCode);
        List<Participants> participantses = pj.getEntityManager()
                .createNamedQuery("Participants.findByUser")
                .setParameter("user", user).getResultList();
        return participantses.isEmpty() ? null : participantses;
    }

    @Override
    public List<Participants> findListParticipantsWithRacing(Integer racingId) {
        Racing racing = rl.findRaingWithId(racingId);
        List<Participants> participantses = pj.getEntityManager()
                .createNamedQuery("Participants.findByRacing")
                .setParameter("racing", racing).getResultList();
        return participantses.isEmpty() ? null : participantses;
    }

    @Override
    public List<Participants> findAllPaticipants() {
        List<Participants> participantses = pj.getEntityManager()
                .createNamedQuery("Participants.findAll").getResultList();
        return participantses.isEmpty() ? null : participantses;
    }

    @Override
    public List<Participants> findListParticipantsWithConditionType(Integer conditionType) {
        List<Participants> participantses = pj.getEntityManager()
                .createNamedQuery("Participants.findByConditionType")
                .setParameter("conditionType", conditionType).getResultList();
        return participantses.isEmpty() ? null : participantses;
    }

    @Override
    public List<Participants> findListPaticipantsOnToday() {
        Date date = ConstantValues.ConstantFunction.getCurrentDate();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        List<Participants> participantses = pj.getEntityManager()
                .createNamedQuery("Participants.findByCreateAtBiggerNow")
                .setParameter("time", date).getResultList();
        return participantses.isEmpty() ? null : participantses;
    }

}
