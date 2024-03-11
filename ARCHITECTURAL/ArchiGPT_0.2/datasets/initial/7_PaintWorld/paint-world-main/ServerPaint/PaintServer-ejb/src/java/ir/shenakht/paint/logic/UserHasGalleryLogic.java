/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic;

import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.controller.interfaces.UserHasGalleryJpaControllerIntf;
import ir.shenakht.paint.domain.Gallery;
import ir.shenakht.paint.domain.User;
import ir.shenakht.paint.domain.UserHasGallery;
import ir.shenakht.paint.logic.interfaces.GalleryLogicIntf;
import ir.shenakht.paint.logic.interfaces.UserHasGalleryLogicIntf;
import ir.shenakht.paint.logic.interfaces.UserLogicIntf;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author hossien
 */
@Stateless
public class UserHasGalleryLogic implements UserHasGalleryLogicIntf {

    @EJB
    private GalleryLogicIntf gl;

    @EJB
    private UserLogicIntf ul;

    @EJB
    private UserHasGalleryJpaControllerIntf ugj;

    @Override
    public UserHasGallery createUserHasGallery(User user, Gallery gallery, UserHasGallery userHasGallery) {
        user = ul.findUserById(user.getId());
        gallery = gl.findGalleryById(gallery.getId());
        if (user != null && gallery != null) {
            try {
                userHasGallery.setGalleryId(gallery);
                userHasGallery.setUserId(user);
                userHasGallery = ugj.create(userHasGallery);
                return userHasGallery;
            } catch (Exception ex) {
                Logger.getLogger(UserHasGalleryLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public boolean updateUserHasGallery(UserHasGallery userHasGallery) {
        try {
            UserHasGallery userHasGalleryOld = findUserHasGalleryById(userHasGallery.getId());
            if (userHasGallery.getGalleryId() != null) {
                userHasGalleryOld.setGalleryId(userHasGallery.getGalleryId());
            }
            if (userHasGallery.getUserId() != null) {
                userHasGalleryOld.setUserId(userHasGallery.getUserId());
            }
            if (userHasGallery.getType() != null) {
                userHasGalleryOld.setType(userHasGallery.getType());
            }
            ugj.edit(userHasGalleryOld);
            return true;
        } catch (RollbackFailureException ex) {
            Logger.getLogger(UserHasGalleryLogic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserHasGalleryLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteUserHasGallery(Integer id) {
        UserHasGallery uhg = findUserHasGalleryById(id);
        if (uhg != null) {
            try {
                ugj.destroy(id);
                return true;
            } catch (RollbackFailureException ex) {
                Logger.getLogger(UserHasGalleryLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(UserHasGalleryLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public UserHasGallery findUserHasGalleryById(Integer id) {
        return ugj.findUserHasGallery(id);
    }

}
