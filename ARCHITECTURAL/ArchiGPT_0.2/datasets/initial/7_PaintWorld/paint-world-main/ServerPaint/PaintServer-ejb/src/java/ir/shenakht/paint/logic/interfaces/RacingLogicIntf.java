/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic.interfaces;

import ir.shenakht.paint.domain.Racing;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hossien
 */
@Local
public interface RacingLogicIntf {

    Racing createRacing(Racing racing);

    boolean updateRacing(Racing racing);

    boolean deleteRacing(Integer id);

    Racing findRaingWithId(Integer id);
    
    List<Racing> findAllRacing();

}
