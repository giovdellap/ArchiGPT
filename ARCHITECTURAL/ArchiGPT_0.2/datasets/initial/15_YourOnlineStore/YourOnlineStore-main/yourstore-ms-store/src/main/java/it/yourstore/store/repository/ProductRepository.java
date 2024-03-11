package it.yourstore.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import it.yourstore.store.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

	Optional<Product> findByProductId(Integer id);

	@Query("DELETE FROM Product WHERE productId IN ?1")
	void deleteByIdIn(Collection<Integer> ids);
}
