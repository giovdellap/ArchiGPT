package NHHFFarmerBE.FarmerBE.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import NHHFFarmerBE.FarmerBE.entities.Area;


@Repository
public interface AreaRepository extends CrudRepository<Area, Integer> {

}


