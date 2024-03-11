/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic;

import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.controller.interfaces.DiscountGalleryForUserJpaControllerIntf;
import ir.shenakht.paint.domain.Discount;
import ir.shenakht.paint.domain.DiscountGalleryForUser;
import ir.shenakht.paint.domain.Gallery;
import ir.shenakht.paint.domain.User;
import ir.shenakht.paint.logic.interfaces.DiscountGalleryForUserLogicIntf;
import ir.shenakht.paint.logic.interfaces.DiscountLogicIntf;
import ir.shenakht.paint.logic.interfaces.GalleryLogicIntf;
import ir.shenakht.paint.logic.interfaces.UserLogicIntf;
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
public class DiscountGalleryForUserLogic implements DiscountGalleryForUserLogicIntf {

    @EJB
    private DiscountLogicIntf dl;

    @EJB
    private GalleryLogicIntf gl;

    @EJB
    private UserLogicIntf ul;

    @EJB
    private DiscountGalleryForUserJpaControllerIntf dguj;

    @Override
    public DiscountGalleryForUser createDiscountGalleryForUser(User user, Discount discount, Gallery gallery, DiscountGalleryForUser discountGalleryForUser) {
        user = ul.findUserById(user.getId());
        discount = dl.findDiscountById(discount.getId());
        gallery = gl.findGalleryById(gallery.getId());
        if (user != null && discount != null && gallery != null) {
            try {
                discountGalleryForUser.setUserId(user);
                discountGalleryForUser.setGalleryId(gallery);
                discountGalleryForUser.setDiscountId(discount);
                discountGalleryForUser = dguj.create(discountGalleryForUser);
                return discountGalleryForUser;
            } catch (Exception ex) {
                Logger.getLogger(DiscountGalleryForUserLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public boolean updateDiscountGalleryForUser(DiscountGalleryForUser discountGalleryForUser) {
        DiscountGalleryForUser discountGalleryForUserOld = findDiscoutnGalleryForUserById(discountGalleryForUser.getId());
        if (discountGalleryForUserOld != null) {
            try {
                if (discountGalleryForUser.getDiscountId() != null) {
                    Discount discount = dl.findDiscountById(discountGalleryForUser.getDiscountId().getId());
                    if (discount != null) {
                        discountGalleryForUserOld.setDiscountId(discount);
                    }
                }
                if (discountGalleryForUser.getGalleryId() != null) {
                    Gallery gallery = gl.findGalleryById(discountGalleryForUser.getGalleryId().getId());
                    if (gallery != null) {
                        discountGalleryForUserOld.setGalleryId(gallery);
                    }
                }
                if (discountGalleryForUser.getUserId() != null) {
                    User user = ul.findUserById(discountGalleryForUser.getUserId().getId());
                    if (user != null) {
                        discountGalleryForUserOld.setUserId(user);
                    }
                }
                if (discountGalleryForUser.getNumberUser() != null) {
                    discountGalleryForUserOld.setNumberUser(discountGalleryForUser.getNumberUser());
                }
                dguj.edit(discountGalleryForUserOld);
                return true;
            } catch (RollbackFailureException ex) {
                Logger.getLogger(DiscountGalleryForUserLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(DiscountGalleryForUserLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public boolean deleteDiscountGalleryForUser(Integer id) {
        DiscountGalleryForUser dgfu = findDiscoutnGalleryForUserById(id);
        if (dgfu != null) {
            try {
                dguj.destroy(id);
                return true;
            } catch (RollbackFailureException ex) {
                Logger.getLogger(DiscountGalleryForUserLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(DiscountGalleryForUserLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public DiscountGalleryForUser findDiscoutnGalleryForUserById(Integer id) {
        return dguj.findDiscountGalleryForUser(id);
    }

    @Override
    public List<DiscountGalleryForUser> findListDiscountGalleryForUserByUser(User user) {
        List<DiscountGalleryForUser> dgfus = dguj.getEntityManager()
                .createNamedQuery("DiscountGalleryForUser.findByUser")
                .setParameter("user", user).getResultList();
        return dgfus.isEmpty() ? null : dgfus;
    }

    @Override
    public List<DiscountGalleryForUser> findListDiscountGalleryForUserByGallery(Gallery gallery) {
        List<DiscountGalleryForUser> dgfus = dguj.getEntityManager()
                .createNamedQuery("DiscountGalleryForUser.findByGallery")
                .setParameter("gallery", gallery).getResultList();
        return dgfus.isEmpty() ? null : dgfus;
    }

    @Override
    public List<DiscountGalleryForUser> findListDiscountGalleryForUserByUserAndGalleryAndDiscount(User user, Discount discount, Gallery gallery) {
        List<DiscountGalleryForUser> dgfus = dguj.getEntityManager()
                .createNamedQuery("DiscountGalleryForUser.findByUser&Gallery&Discount")
                .setParameter("discount", discount)
                .setParameter("gallery", gallery)
                .setParameter("user", user).getResultList();
        return dgfus.isEmpty() ? null : dgfus;
    }

    @Override
    public List<DiscountGalleryForUser> findListDiscountGalleryForUserByUserAndGallery(User user, Gallery gallery) {
        List<DiscountGalleryForUser> dgfus = dguj.getEntityManager()
                .createNamedQuery("DiscountGalleryForUser.findByUser&Gallery")
                .setParameter("gallery", gallery)
                .setParameter("user", user).getResultList();
        return dgfus.isEmpty() ? null : dgfus;
    }

}
