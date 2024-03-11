/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic.interfaces;

import ir.shenakht.paint.domain.Discount;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hossien
 */
@Local
public interface DiscountLogicIntf {

    Discount createDiscount(Discount discount);

    boolean updateDiscount(Discount discount);

    boolean deleteDiscount(Integer id);

    Discount findDiscountById(Integer id);

    List<Discount> findAllDiscount();
}
