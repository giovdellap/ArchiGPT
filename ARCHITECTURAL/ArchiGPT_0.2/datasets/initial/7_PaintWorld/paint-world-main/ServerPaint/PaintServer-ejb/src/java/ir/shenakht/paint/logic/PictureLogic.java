/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic;

import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.controller.interfaces.PictureJpacControllerIntf;
import ir.shenakht.paint.domain.Gallery;
import ir.shenakht.paint.domain.Picture;
import ir.shenakht.paint.logic.interfaces.GalleryLogicIntf;
import ir.shenakht.paint.logic.interfaces.PictureLogicIntf;
import java.sql.Timestamp;
import java.time.Instant;
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
public class PictureLogic implements PictureLogicIntf {

    @EJB
    private GalleryLogicIntf gl;

    @EJB
    private PictureJpacControllerIntf pij;

    @Override
    public Picture createPicture(Integer galleryId, Picture picture) {
        Gallery gallery = gl.findGalleryById(galleryId);
        if (gallery != null) {
            try {
                picture.setGalleryId(gallery);
                picture.setCreateAt(Timestamp.from(Instant.now()));
                picture.setUpdateAt(Timestamp.from(Instant.now()));
                picture = pij.create(picture);
                return picture;
            } catch (Exception ex) {
                Logger.getLogger(PictureLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public boolean updatePicture(Integer galleryId, Picture picture) {
        Picture pictureOld = findPicture(galleryId, picture.getId());
        if (pictureOld != null) {
            try {
                if (picture.getUrlThumbnail() != null) {
                    pictureOld.setUrlThumbnail(picture.getUrlThumbnail());
                }
                if (picture.getTitle() != null) {
                    pictureOld.setTitle(picture.getTitle());
                }
                if (picture.getUrlMain() != null) {
                    pictureOld.setUrlMain(picture.getUrlMain());
                }
                pictureOld.setUpdateAt(Timestamp.from(Instant.now()));
                pij.edit(pictureOld);
                return true;
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(PictureLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RollbackFailureException ex) {
                Logger.getLogger(PictureLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(PictureLogic.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return false;
    }

    @Override
    public boolean deletePicture(Integer galleryId, Integer pictureId) {
        Picture picture = findPicture(galleryId, pictureId);
        if (picture != null) {
            try {
                pij.destroy(picture.getId());
                return true;
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(PictureLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RollbackFailureException ex) {
                Logger.getLogger(PictureLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(PictureLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public Picture findPicture(Integer galleryId, Integer pictureId) {
        Gallery gallery = gl.findGalleryById(galleryId);
        if (gallery != null) {
            List<Picture> pictures = pij.getEntityManager()
                    .createNamedQuery("Picture.findById&Gallery")
                    .setParameter("id", pictureId)
                    .setParameter("gallery", gallery).getResultList();
            return pictures.isEmpty() ? null : pictures.get(0);
        }
        return null;
    }

    @Override
    public List<Picture> findAllPictureForGallery(Integer galleryId) {
        Gallery gallery = gl.findGalleryById(galleryId);
        if (gallery != null) {
            List<Picture> pictures = pij.getEntityManager()
                    .createNamedQuery("Picture.findByGallery")
                    .setParameter("gallery", gallery).getResultList();
            return pictures.isEmpty() ? null : pictures;
        }
        return null;
    }

}
