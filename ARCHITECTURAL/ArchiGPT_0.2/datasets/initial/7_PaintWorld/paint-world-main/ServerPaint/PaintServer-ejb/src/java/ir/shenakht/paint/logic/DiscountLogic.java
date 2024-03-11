/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic;

import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.controller.interfaces.DiscountJpaControllerIntf;
import ir.shenakht.paint.domain.Discount;
import ir.shenakht.paint.logic.interfaces.DiscountLogicIntf;
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
public class DiscountLogic implements DiscountLogicIntf {

    @EJB
    private DiscountJpaControllerIntf dj;

    @Override
    public Discount createDiscount(Discount discount) {
        try {
            discount = dj.create(discount);
            return discount;
        } catch (Exception ex) {
            Logger.getLogger(DiscountLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean updateDiscount(Discount discount) {
        Discount discountOld = findDiscountById(discount.getId());
        if (discountOld != null) {
            try {
                if (discount.getDescription() != null) {
                    discountOld.setDescription(discount.getDescription());
                }
                if (discount.getDiscount() != null) {
                    discountOld.setDiscount(discount.getDiscount());
                }
                if (discount.getEndDiscount() != null) {
                    discountOld.setEndDiscount(discount.getEndDiscount());
                }
                if (discount.getExtraField() != null) {
                    discountOld.setExtraField(discount.getExtraField());
                }
                if (discount.getRepeat() != null) {
                    discountOld.setRepeat(discount.getRepeat());
                }
                if (discount.getStartDiscount() != null) {
                    discountOld.setStartDiscount(discount.getStartDiscount());
                }
                if (discount.getTitle() != null) {
                    discountOld.setTitle(discount.getTitle());
                }
                if (discount.getType() != null) {
                    discountOld.setType(discount.getType());
                }
                dj.edit(discountOld);
                return true;
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(DiscountLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RollbackFailureException ex) {
                Logger.getLogger(DiscountLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(DiscountLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public boolean deleteDiscount(Integer id) {
        Discount discount = findDiscountById(id);
        if (discount != null) {
            try {
                dj.destroy(id);
                return true;
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(DiscountLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RollbackFailureException ex) {
                Logger.getLogger(DiscountLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(DiscountLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public Discount findDiscountById(Integer id) {
        return dj.findDiscount(id);
    }

    @Override
    public List<Discount> findAllDiscount() {
        List<Discount> discounts = dj.getEntityManager()
                .createNamedQuery("Discount.findAll").getResultList();
        return discounts.isEmpty() ? null : discounts;
    }

}
