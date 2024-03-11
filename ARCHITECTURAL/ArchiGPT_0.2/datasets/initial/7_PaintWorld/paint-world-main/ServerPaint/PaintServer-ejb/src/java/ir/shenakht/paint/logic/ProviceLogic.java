/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic;

import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.controller.interfaces.ProviceJpaControllerIntf;
import ir.shenakht.paint.domain.Provice;
import ir.shenakht.paint.logic.interfaces.ProviceLogicIntf;
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
public class ProviceLogic implements ProviceLogicIntf {

    @EJB
    private ProviceJpaControllerIntf pj;

    @Override
    public Provice createProvice(Provice provice) {
        if (provice != null) {
            try {
                provice = pj.create(provice);
                return provice;
            } catch (RollbackFailureException ex) {
                Logger.getLogger(ProviceLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ProviceLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public boolean updateProcvice(Provice provice) {
        Provice proviceOld = findProvice(provice.getId());
        if (proviceOld != null) {
            try {
                if (provice.getName() != null) {
                    proviceOld.setName(provice.getName());
                }
                pj.edit(proviceOld);
                return true;
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ProviceLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RollbackFailureException ex) {
                Logger.getLogger(ProviceLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ProviceLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public boolean deleteProvice(Integer id) {
        Provice provice = findProvice(id);
        if (provice != null) {
            try {
                pj.destroy(id);
                return true;
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ProviceLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RollbackFailureException ex) {
                Logger.getLogger(ProviceLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ProviceLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public Provice findProvice(Integer id) {
        return pj.findProvice(id);
    }

    @Override
    public List<Provice> findAllProvice() {
        List<Provice> provices = pj.getEntityManager()
                .createNamedQuery("Provice.findAll").getResultList();
        return provices.isEmpty() ? null : provices;
    }

}
