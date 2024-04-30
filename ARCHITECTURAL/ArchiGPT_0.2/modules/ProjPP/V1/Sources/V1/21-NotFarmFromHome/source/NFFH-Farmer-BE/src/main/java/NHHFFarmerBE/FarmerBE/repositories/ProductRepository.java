package NHHFFarmerBE.FarmerBE.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import NHHFFarmerBE.FarmerBE.entities.Product;


@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    List<Product> findBySeller(String seller);



}
