/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic;

import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.controller.interfaces.GalleryCostJpaControllerIntf;
import ir.shenakht.paint.domain.GalleryCost;
import ir.shenakht.paint.logic.interfaces.GalleryCostLogicIntf;
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
public class GalleryCostLogic implements GalleryCostLogicIntf {

    @EJB
    private GalleryCostJpaControllerIntf gcj;

    @Override
    public GalleryCost createGalleryCost(GalleryCost galleryCost) {
        try {
            galleryCost = gcj.create(galleryCost);
            return galleryCost;
        } catch (Exception ex) {
            Logger.getLogger(GalleryCostLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean updateGalleryCost(GalleryCost galleryCost) {
        GalleryCost galleryCostOld = findGalleryCostById(galleryCost.getId());
        if (galleryCostOld != null) {
            try {
                if (galleryCost.getExtraField() != null) {
                    galleryCostOld.setExtraField(galleryCost.getExtraField());
                }
                if (galleryCost.getPrice() != null) {
                    galleryCostOld.setPrice(galleryCost.getPrice());
                }
                if (galleryCost.getTermOfValidity() != null) {
                    galleryCostOld.setTermOfValidity(galleryCost.getTermOfValidity());
                }
                if (galleryCost.getTitle() != null) {
                    galleryCostOld.setTitle(galleryCost.getTitle());
                }
                if (galleryCost.getType() != null) {
                    galleryCostOld.setType(galleryCost.getType());
                }
                gcj.edit(galleryCostOld);
                return true;
            } catch (RollbackFailureException ex) {
                Logger.getLogger(GalleryCostLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(GalleryCostLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public boolean deleteGalleryCost(Integer id) {
        GalleryCost galleryCost = findGalleryCostById(id);
        if (galleryCost != null) {
            try {
                gcj.destroy(id);
                return true;
            } catch (RollbackFailureException ex) {
                Logger.getLogger(GalleryCostLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(GalleryCostLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public GalleryCost findGalleryCostById(Integer id) {
        return gcj.findGalleryCost(id);
    }

    @Override
    public List<GalleryCost> findAllGalleryCost() {
        List<GalleryCost> galleryCosts = gcj.getEntityManager()
                .createNamedQuery("GalleryCost.findAll").getResultList();
        return galleryCosts.isEmpty() ? null : galleryCosts;
    }

}
