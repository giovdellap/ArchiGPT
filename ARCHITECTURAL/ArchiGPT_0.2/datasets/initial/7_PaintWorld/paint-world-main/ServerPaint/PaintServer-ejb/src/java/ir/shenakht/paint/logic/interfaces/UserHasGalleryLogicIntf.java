/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic.interfaces;

import ir.shenakht.paint.domain.Gallery;
import ir.shenakht.paint.domain.User;
import ir.shenakht.paint.domain.UserHasGallery;
import javax.ejb.Local;

/**
 *
 * @author hossien
 */
@Local
public interface UserHasGalleryLogicIntf {

    UserHasGallery createUserHasGallery(User user, Gallery gallery, UserHasGallery userHasGallery);

    boolean updateUserHasGallery(UserHasGallery userHasGallery);

    boolean deleteUserHasGallery(Integer id);

    UserHasGallery findUserHasGalleryById(Integer id);
}
