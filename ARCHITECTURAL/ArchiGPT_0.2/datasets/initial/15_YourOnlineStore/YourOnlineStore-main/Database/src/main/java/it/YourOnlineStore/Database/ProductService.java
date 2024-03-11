package it.YourOnlineStore.Database;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public interface ProductService  {

	boolean exists(Integer id);

	Optional<Product> findById(Integer id);
	
	void buy(Integer id, Integer amount);
}
