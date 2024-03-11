/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic.interfaces;

import ir.shenakht.paint.domain.Provice;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hossien
 */
@Local
public interface ProviceLogicIntf {

    Provice createProvice(Provice provice);

    boolean updateProcvice(Provice provice);

    boolean deleteProvice(Integer id);

    Provice findProvice(Integer id);

    List<Provice> findAllProvice();
}
