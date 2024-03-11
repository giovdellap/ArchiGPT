/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic;

import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.controller.interfaces.GalleryJpaControllerIntf;
import ir.shenakht.paint.domain.Gallery;
import ir.shenakht.paint.logic.interfaces.GalleryLogicIntf;
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
public class GalleryLogic implements GalleryLogicIntf {

    @EJB
    private GalleryJpaControllerIntf gj;

    @Override
    public Gallery createGallery(Gallery gallery) {
        try {
            gallery = gj.create(gallery);
            return gallery;
        } catch (Exception ex) {
            Logger.getLogger(GalleryLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gallery;
    }

    @Override
    public boolean updateGallery(Gallery gallery) {
        Gallery galleryOld = findGalleryById(gallery.getId());
        if (galleryOld != null) {
            try {
                if (gallery.getDescription() != null) {
                    galleryOld.setDescription(gallery.getDescription());
                }
                if (gallery.getExtraField() != null) {
                    galleryOld.setExtraField(gallery.getExtraField());
                }
                if (gallery.getTitle() != null) {
                    galleryOld.setTitle(gallery.getTitle());
                }
                if (gallery.getType() != null) {
                    galleryOld.setType(gallery.getType());
                }
                if (gallery.getGalleryCostList() != null) {
                    galleryOld.getGalleryCostList().clear();
                    galleryOld.getGalleryCostList().addAll(gallery.getGalleryCostList());
                }
                gj.edit(galleryOld);
                return true;
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(GalleryLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RollbackFailureException ex) {
                Logger.getLogger(GalleryLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(GalleryLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public boolean deleteGallery(Integer id) {
        Gallery gallery = findGalleryById(id);
        if (gallery != null) {
            try {
                gj.destroy(id);
                return true;
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(GalleryLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RollbackFailureException ex) {
                Logger.getLogger(GalleryLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(GalleryLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public Gallery findGalleryById(Integer id) {
        return gj.findGallery(id);
    }

    @Override
    public List<Gallery> findAllGallery() {
        List<Gallery> gallerys = gj.getEntityManager()
                .createNamedQuery("Gallery.findAll").getResultList();
        return gallerys.isEmpty() ? null : gallerys;
    }

}
