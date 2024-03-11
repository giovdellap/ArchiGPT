package it.yourstore.store.service;

import it.yourstore.store.domain.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ProductService extends GenericEntityService<Product, Integer> {

	// PARENT-SPECIFIC SERVICES

	Product bulkUpdate(Product product);

	Product update(Product product);

	Optional<Product> delete(String objectKey);

	Page<Product> search(Specification<Product> specification, Pageable pageable);

}
