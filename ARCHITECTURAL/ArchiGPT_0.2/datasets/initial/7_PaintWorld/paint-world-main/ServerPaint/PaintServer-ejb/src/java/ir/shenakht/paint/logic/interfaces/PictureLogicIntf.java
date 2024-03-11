/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic.interfaces;

import ir.shenakht.paint.domain.Picture;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hossien
 */
@Local
public interface PictureLogicIntf {

    Picture createPicture(Integer galleryId, Picture picture);

    boolean updatePicture(Integer galleryId, Picture picture);

    boolean deletePicture(Integer galleryId, Integer pictureId);

    Picture findPicture(Integer galleryId, Integer pictureId);

    List<Picture> findAllPictureForGallery(Integer galleryId);
}
