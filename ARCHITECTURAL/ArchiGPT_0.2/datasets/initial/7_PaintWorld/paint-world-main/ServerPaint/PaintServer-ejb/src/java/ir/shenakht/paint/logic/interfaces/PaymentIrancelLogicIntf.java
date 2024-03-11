/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic.interfaces;

import ir.shenakht.paint.domain.PaymentIrancel;
import javax.ejb.Local;

/**
 *
 * @author hossien
 */
@Local
public interface PaymentIrancelLogicIntf {

    PaymentIrancel create(PaymentIrancel irancel);

    PaymentIrancel findLatest(String phone);

}
