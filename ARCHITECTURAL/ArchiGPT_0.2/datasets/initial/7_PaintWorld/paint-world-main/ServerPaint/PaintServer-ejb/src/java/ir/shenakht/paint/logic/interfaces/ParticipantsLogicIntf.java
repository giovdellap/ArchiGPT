/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic.interfaces;

import ir.shenakht.paint.domain.Participants;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hossien
 */
@Local
public interface ParticipantsLogicIntf {

    Participants createParticipants(String userCode, Integer racingID, Participants participants);

    boolean updateParticipants(Participants participants);

    boolean deleteParticipants(Integer id);

    Participants findParitcipants(Integer id);

    List<Participants> findListParticipantsWithUserCode(String userCode);

    List<Participants> findListParticipantsWithRacing(Integer racingId);

    List<Participants> findListParticipantsWithConditionType(Integer conditionType);

    List<Participants> findListPaticipantsOnToday();

    List<Participants> findAllPaticipants();

}
