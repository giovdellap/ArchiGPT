package it.yourstore.store.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import it.yourstore.store.domain.OrderItem;
import it.yourstore.store.domain.OrderItemKey;
import it.yourstore.store.domain.Ordine;
import it.yourstore.store.domain.Product;
import it.yourstore.store.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderItemServiceImpl implements OrderItemService {

	private final OrderItemRepository orderItemRepository;
	// CHILD SERVICES

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(OrderItemServiceImpl.class);

	@Override
	public Page<OrderItem> findAll(Pageable pageable) {
		return orderItemRepository.findAll(pageable);
	}

	@Override
	public List<OrderItem> findAll() {
		return orderItemRepository.findAll();
	}

	@Override
	public Optional<OrderItem> findByObjectKey(String objectKey) {
		OrderItem orderItem = new OrderItem(objectKey);
		return orderItemRepository.findByTheOrderItemKey(orderItem.getTheOrderItemKey());
	}

	@Override
	public boolean exists(OrderItemKey id) {
		return orderItemRepository.existsById(id);
	}

	@Override
	public OrderItem insert(@Valid OrderItem entity) {
		return orderItemRepository.save(entity);
	}

	@Override
	public OrderItem update(@Valid OrderItem entity) {
		return orderItemRepository.save(entity);
	}

	@Override
	public Optional<OrderItem> delete(String objectKey) {
		return findByObjectKey(objectKey).map(orderItem -> {
			orderItemRepository.delete(orderItem);
			return Optional.of(orderItem);
		}).orElseGet(Optional::empty);
	}

	@Override
	public Page<OrderItem> search(Specification<OrderItem> specification, Pageable pageable) {
		return orderItemRepository.findAll(specification, pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.yourstore.store.service.GenericEntityService#deleteById(java.lang.
	 * Object)
	 */
	@Override
	public void deleteById(OrderItemKey id) {
		orderItemRepository.deleteById(id);
	}

	@Override
	public OrderItem bulkUpdate(OrderItem orderItem) {
		OrderItem update = this.update(orderItem);
		return update;
	}

	@Override
	public Page<OrderItem> findByTheOrdine(Ordine parentEntity, Pageable pageable) {
		return orderItemRepository.findByTheOrdine(parentEntity, pageable);
	}

	@Override
	public Page<OrderItem> findByTheProduct(Product parentEntity, Pageable pageable) {
		return orderItemRepository.findByTheProduct(parentEntity, pageable);
	}

	@Override
	public Page<Ordine> findTheOrdineByTheProduct(Product product, Pageable pageable) {
		Page<OrderItem> orderItemPage = orderItemRepository.findByTheProduct(product, pageable);
		List<Ordine> content = orderItemPage.getContent().stream().map(OrderItem::getTheOrdine)
				.collect(Collectors.toList());
		Page<Ordine> result = new PageImpl<Ordine>(content, pageable, orderItemPage.getTotalElements());
		return result;
	}

	@Override
	public Page<Product> findTheProductByTheOrdine(Ordine ordine, Pageable pageable) {
		Page<OrderItem> orderItemPage = orderItemRepository.findByTheOrdine(ordine, pageable);
		List<Product> content = orderItemPage.getContent().stream().map(OrderItem::getTheProduct)
				.collect(Collectors.toList());
		Page<Product> result = new PageImpl<Product>(content, pageable, orderItemPage.getTotalElements());
		return result;
	}

}
