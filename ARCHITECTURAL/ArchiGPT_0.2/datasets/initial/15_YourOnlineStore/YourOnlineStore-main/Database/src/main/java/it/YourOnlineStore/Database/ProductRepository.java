package it.YourOnlineStore.Database;

import org.springframework.data.repository.CrudRepository;

public interface
ProductRepository extends CrudRepository<Product, Integer> {
}
