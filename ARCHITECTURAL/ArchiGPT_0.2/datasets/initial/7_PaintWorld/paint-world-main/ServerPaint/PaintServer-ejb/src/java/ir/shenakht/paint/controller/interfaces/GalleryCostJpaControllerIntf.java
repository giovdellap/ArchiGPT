/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.controller.interfaces;

import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.domain.GalleryCost;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 *
 * @author hossien
 */
@Local
public interface GalleryCostJpaControllerIntf extends Serializable {

    GalleryCost create(GalleryCost galleryCost) throws RollbackFailureException, Exception;

    void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception;

    void edit(GalleryCost galleryCost) throws NonexistentEntityException, RollbackFailureException, Exception;

    GalleryCost findGalleryCost(Integer id);

    List<GalleryCost> findGalleryCostEntities();

    List<GalleryCost> findGalleryCostEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getGalleryCostCount();

}
