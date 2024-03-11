package it.yourstore.store.service;

import it.yourstore.store.domain.OrderItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import it.yourstore.store.domain.OrderItemKey;

import it.yourstore.store.domain.Ordine;
import it.yourstore.store.domain.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface OrderItemService extends GenericEntityService<OrderItem, OrderItemKey> {

	// PARENT-SPECIFIC SERVICES
	Page<OrderItem> findByTheOrdine(Ordine parentEntity, Pageable pageable);

	Page<OrderItem> findByTheProduct(Product parentEntity, Pageable pageable);

	/**
	 * Return a list of Ordine from all OrderItem with given theOrdine
	 *
	 * @param product
	 * @param pageable
	 * @return list of Ordine from all OrderItem with given TheOrdine
	 */
	Page<Ordine> findTheOrdineByTheProduct(Product product, Pageable pageable);

	/**
	 * Return a list of Product from all OrderItem with given theProduct
	 *
	 * @param ordine
	 * @param pageable
	 * @return list of Product from all OrderItem with given TheProduct
	 */
	Page<Product> findTheProductByTheOrdine(Ordine ordine, Pageable pageable);

	OrderItem bulkUpdate(OrderItem orderItem);

	OrderItem update(OrderItem orderItem);

	Optional<OrderItem> delete(String objectKey);

	Page<OrderItem> search(Specification<OrderItem> specification, Pageable pageable);
}
