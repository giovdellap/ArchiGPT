/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic;

import ir.shenakht.paint.controller.PaymentIrancelJpaControllerIntf;
import ir.shenakht.paint.domain.PaymentIrancel;
import ir.shenakht.paint.domain.User;
import ir.shenakht.paint.logic.interfaces.PaymentIrancelLogicIntf;
import ir.shenakht.paint.logic.interfaces.UserLogicIntf;
import ir.shenakht.paint.util.ConvertPhoneToRightFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author hossien
 */
@Stateless
public class PaymentIrancelLogic implements PaymentIrancelLogicIntf {

    @EJB
    private PaymentIrancelJpaControllerIntf pij;

    @EJB
    private UserLogicIntf ul;

    @Override
    public PaymentIrancel create(PaymentIrancel irancel) {
        if (irancel.getMsisdn() != null && irancel.getMsisdn().length() > 9) {
            irancel.setMsisdn(ConvertPhoneToRightFormat.makePhone(irancel.getMsisdn()));
        }
        User user = ul.findUserWithPhone(irancel.getMsisdn());
        if (user != null) {
            try {
                irancel.setUserId(user);
                irancel = pij.create(irancel);
                return irancel;
            } catch (Exception ex) {
                Logger.getLogger(PaymentIrancelLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }

    @Override
    public PaymentIrancel findLatest(String phone) {
        phone = ConvertPhoneToRightFormat.makePhone(phone);
        List<PaymentIrancel> paymentIrancels = pij.getEntityManager()
                .createNamedQuery("PaymentIrancel.findByMsisdn")
                .setParameter("msisdn", phone).getResultList();
        return paymentIrancels.isEmpty() ? null : paymentIrancels.get(0);
    }

}
