/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic.interfaces;

import ir.shenakht.paint.domain.City;
import ir.shenakht.paint.domain.Provice;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hossien
 */
@Local
public interface CityLogicIntf {

    City createCity(Provice provice, City city);

    boolean updateCity(City city);

    boolean deleteCity(Integer id);

    City findCity(Integer id);

    List<City> findAllCity();

    List<City> findListCityByProvice(Provice provice);
}
