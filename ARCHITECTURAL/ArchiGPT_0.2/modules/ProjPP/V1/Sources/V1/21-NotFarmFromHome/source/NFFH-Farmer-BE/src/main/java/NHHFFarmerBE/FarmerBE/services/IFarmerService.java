package NHHFFarmerBE.FarmerBE.services;

import NHHFFarmerBE.FarmerBE.entities.Farmer;
import java.util.*;


public interface IFarmerService {
    Farmer create(Farmer farmer);

    List<Farmer> findAll();
    
    void delete(int id);

    Optional<Farmer> findById(int id); 

    List<Farmer> findByArea(String Area);

    Farmer findByEmail(String email);

    Optional<Farmer> findByUsername(String username);
}
