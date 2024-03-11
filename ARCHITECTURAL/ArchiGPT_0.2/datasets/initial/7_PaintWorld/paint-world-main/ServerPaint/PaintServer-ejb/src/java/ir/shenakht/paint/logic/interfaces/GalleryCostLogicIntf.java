/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic.interfaces;

import ir.shenakht.paint.domain.GalleryCost;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hossien
 */
@Local
public interface GalleryCostLogicIntf {

    GalleryCost createGalleryCost(GalleryCost galleryCost);

    boolean updateGalleryCost(GalleryCost galleryCost);

    boolean deleteGalleryCost(Integer id);

    GalleryCost findGalleryCostById(Integer id);

    List<GalleryCost> findAllGalleryCost();
}
