/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic.interfaces;

import ir.shenakht.paint.domain.Discount;
import ir.shenakht.paint.domain.DiscountGalleryForUser;
import ir.shenakht.paint.domain.Gallery;
import ir.shenakht.paint.domain.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hossien
 */
@Local
public interface DiscountGalleryForUserLogicIntf {

    DiscountGalleryForUser createDiscountGalleryForUser(User user, Discount discount, Gallery gallery, DiscountGalleryForUser discountGalleryForUser);

    boolean updateDiscountGalleryForUser(DiscountGalleryForUser discountGalleryForUser);

    boolean deleteDiscountGalleryForUser(Integer id);

    DiscountGalleryForUser findDiscoutnGalleryForUserById(Integer id);

    List<DiscountGalleryForUser> findListDiscountGalleryForUserByUser(User user);

    List<DiscountGalleryForUser> findListDiscountGalleryForUserByGallery(Gallery gallery);

    List<DiscountGalleryForUser> findListDiscountGalleryForUserByUserAndGalleryAndDiscount(User user, Discount discount, Gallery gallery);

    List<DiscountGalleryForUser> findListDiscountGalleryForUserByUserAndGallery(User user, Gallery galley);
}
