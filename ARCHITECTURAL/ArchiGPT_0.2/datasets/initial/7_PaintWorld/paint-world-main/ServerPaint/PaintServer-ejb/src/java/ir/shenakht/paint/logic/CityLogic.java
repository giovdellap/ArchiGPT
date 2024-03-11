/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic;

import ir.shenakht.paint.controller.exceptions.NonexistentEntityException;
import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.controller.interfaces.CityJpaControllerIntf;
import ir.shenakht.paint.domain.City;
import ir.shenakht.paint.domain.Provice;
import ir.shenakht.paint.logic.interfaces.CityLogicIntf;
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
public class CityLogic implements CityLogicIntf {

    @EJB
    private CityJpaControllerIntf cj;

    @EJB
    private ProviceLogicIntf pl;

    @Override
    public City createCity(Provice provice, City city) {
        provice = pl.findProvice(provice.getId());
        if (provice != null) {
            try {
                city.setProviceId(provice);
                city = cj.create(city);
                return city;
            } catch (Exception ex) {
                Logger.getLogger(CityLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public boolean updateCity(City city) {
        City cityOld = findCity(city.getId());
        if (cityOld != null) {
            try {
                if (city.getName() != null) {
                    cityOld.setName(city.getName());
                }
                if (city.getProviceId() != null) {
                    Provice p = pl.findProvice(city.getProviceId().getId());
                    if (p != null) {
                        cityOld.setProviceId(p);
                    }
                }
                cj.edit(cityOld);
                return true;
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(CityLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RollbackFailureException ex) {
                Logger.getLogger(CityLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(CityLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public boolean deleteCity(Integer id) {
        City city = findCity(id);
        if (city != null) {
            try {
                cj.destroy(id);
                return true;
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(CityLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RollbackFailureException ex) {
                Logger.getLogger(CityLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(CityLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public City findCity(Integer id) {
        return cj.findCity(id);
    }

    @Override
    public List<City> findAllCity() {
        List<City> citys = cj.getEntityManager()
                .createNamedQuery("City.findAll").getResultList();
        return citys.isEmpty() ? null : citys;
    }

    @Override
    public List<City> findListCityByProvice(Provice provice) {
        List<City> citys = cj.getEntityManager()
                .createNamedQuery("City.findByProvice")
                .setParameter("provice", provice).getResultList();
        return citys.isEmpty() ? null : citys;
    }

}
