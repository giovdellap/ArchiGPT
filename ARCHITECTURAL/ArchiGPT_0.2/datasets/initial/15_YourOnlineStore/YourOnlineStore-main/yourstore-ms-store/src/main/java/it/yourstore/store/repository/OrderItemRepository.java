package it.yourstore.store.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import it.yourstore.store.domain.OrderItem;
import it.yourstore.store.domain.OrderItemKey;

import it.yourstore.store.domain.Ordine;
import it.yourstore.store.domain.Product;

@Repository
public interface OrderItemRepository
		extends JpaRepository<OrderItem, OrderItemKey>, JpaSpecificationExecutor<OrderItem> {

	@EntityGraph(attributePaths = { "theOrdine", "theProduct" }, type = EntityGraphType.FETCH)
	Optional<OrderItem> findByTheOrderItemKey(OrderItemKey id);

	@EntityGraph(attributePaths = { "theOrdine", "theProduct" }, type = EntityGraphType.FETCH)
	Page<OrderItem> findByTheOrdine(Ordine parentEntity, Pageable pageable);

	@EntityGraph(attributePaths = { "theOrdine", "theProduct" }, type = EntityGraphType.FETCH)
	Page<OrderItem> findByTheProduct(Product parentEntity, Pageable pageable);

	@Query("DELETE FROM OrderItem WHERE theOrderItemKey IN ?1")
	void deleteByIdIn(Collection<OrderItemKey> ids);
}
