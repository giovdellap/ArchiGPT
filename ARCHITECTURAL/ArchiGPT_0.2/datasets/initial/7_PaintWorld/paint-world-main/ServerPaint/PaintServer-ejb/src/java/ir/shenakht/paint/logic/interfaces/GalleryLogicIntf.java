/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic.interfaces;

import ir.shenakht.paint.domain.Gallery;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hossien
 */
@Local
public interface GalleryLogicIntf {

    Gallery createGallery(Gallery gallery);

    boolean updateGallery(Gallery gallery);

    boolean deleteGallery(Integer id);

    Gallery findGalleryById(Integer id);

    List<Gallery> findAllGallery();
}
