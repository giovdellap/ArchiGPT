package NHHFFarmerBE.FarmerBE.services;

import java.util.*;

import NHHFFarmerBE.FarmerBE.entities.Area;

public interface IAreaService {

    Area create(Area area);

    List<Area> findAll();
    
    void delete(int id);
}
