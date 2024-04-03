package NHHFFarmerBE.FarmerBE.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import NHHFFarmerBE.FarmerBE.entities.Farmer;


@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Integer> {

    List<Farmer> findByArea(String Area);

    Farmer findByemail(String email);

    Optional<Farmer> findByUsername(String username);

}


