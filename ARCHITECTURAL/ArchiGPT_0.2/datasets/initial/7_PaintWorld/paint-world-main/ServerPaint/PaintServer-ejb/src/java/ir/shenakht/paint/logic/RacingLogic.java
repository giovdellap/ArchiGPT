/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic;

import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.controller.interfaces.RacingJpaControllerIntf;
import ir.shenakht.paint.domain.Racing;
import ir.shenakht.paint.logic.interfaces.RacingLogicIntf;
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
public class RacingLogic implements RacingLogicIntf {
    
    @EJB
    private RacingJpaControllerIntf rj;
    
    @Override
    public Racing createRacing(Racing racing) {
        try {
            racing = rj.create(racing);
            return racing;
        } catch (RollbackFailureException ex) {
            Logger.getLogger(RacingLogic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RacingLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public boolean updateRacing(Racing racing) {
        Racing racingOld = findRaingWithId(racing.getId());
        if (racing != null) {
            try {
                if (racing.getDescription() != null) {
                    racingOld.setDescription(racing.getDescription());
                }
                if (racing.getEnd() != null) {
                    racingOld.setEnd(racing.getEnd());
                }
                if (racing.getExtraField() != null) {
                    racingOld.setExtraField(racing.getExtraField());
                }
                if (racing.getStart() != null) {
                    racingOld.setStart(racing.getStart());
                }
                if (racing.getTitle() != null) {
                    racingOld.setTitle(racing.getTitle());
                }
                if (racing.getUrl() != null) {
                    racingOld.setUrl(racing.getUrl());
                }
                rj.edit(racingOld);
                return true;
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(RacingLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RollbackFailureException ex) {
                Logger.getLogger(RacingLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(RacingLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    @Override
    public boolean deleteRacing(Integer id) {
        Racing racing = findRaingWithId(id);
        if (racing != null) {
            try {
                rj.destroy(id);
                return true;
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(RacingLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RollbackFailureException ex) {
                Logger.getLogger(RacingLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(RacingLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    @Override
    public Racing findRaingWithId(Integer id) {
        return rj.findRacing(id);
    }
    
    @Override
    public List<Racing> findAllRacing() {
        List<Racing> racings = rj.getEntityManager()
                .createNamedQuery("Racing.findAll").getResultList();
        return racings.isEmpty() ? null : racings;
    }
    
}
