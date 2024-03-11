package it.yourstore.store.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.turkraft.springfilter.boot.Filter;

import io.swagger.v3.oas.annotations.Operation;
import it.yourstore.store.domain.OrderItem;
import it.yourstore.store.dto.EditOrderItemDto;
import it.yourstore.store.dto.ViewOrderItemDto;

import it.yourstore.store.exception.ResourceAlreadyFoundException;
import it.yourstore.store.exception.ResourceNotFoundException;
import it.yourstore.store.mapper.OrderItemMappers;
import it.yourstore.store.service.OrderItemService;
import it.yourstore.store.service.OrdineService;
import it.yourstore.store.domain.Ordine;
import it.yourstore.store.domain.Product;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/order-item", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderItemController {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(OrderItemController.class);

	/// ENTITY SERVICE
	private final OrderItemService orderItemService;
	
	private final OrdineService ordineService;
	
	private final OrderItemMappers orderItemMappers;
	// CHILD SERVICES

	// CHILD MAPPER

	// API
	/**
	 * {@code GET /order-item} : Get all order-item.
	 * 
	 * @param pageable
	 * @return Page of all OrderItem.
	 */
	@GetMapping
	@Transactional(readOnly = true)
	@Operation(summary = "Get all OrderItem")
	public ResponseEntity<Page<ViewOrderItemDto>> findAll(Pageable pageable) {
		Page<ViewOrderItemDto> collModel = orderItemMappers.map(orderItemService.findAll(pageable));
		return toResponseEntityPaged(collModel, null);
	}

	/**
	 * {@code POST  /order-item} : Create a new OrderItem.
	 *
	 * @param requestBody the OrderItem to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new OrderItem, or with status {@code 400 (Bad Request)} if
	 *         the requestBody is invalid.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping
	@Transactional
	@Operation(summary = "Create a new OrderItem")
	public ResponseEntity<ViewOrderItemDto> insert(@RequestBody @Valid EditOrderItemDto requestBody) {
		OrderItem entity = orderItemMappers.map(requestBody);
		if (orderItemService.findByObjectKey(entity.getObjectKey()).isPresent()) {
			throw new ResourceAlreadyFoundException(OrderItem.class.getSimpleName(), entity.getObjectKey());
		} else {
			entity = orderItemService.insert(entity);
			ordineService.updateOrdineCost(entity);
			ViewOrderItemDto dto = orderItemMappers.map(entity);
			return ResponseEntity.ok(dto);
		}
	}

	/**
	 * {@code GET  /order-item/:objectKey} : Get the order-item with given
	 * objectKey.
	 *
	 * @param objectKey the objectKey of the order-item to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the order-item, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/{objectKey:.+}")
	@Transactional(readOnly = true)
	@Operation(summary = "Get the OrderItem with given objectKey")
	public ResponseEntity<ViewOrderItemDto> read(@PathVariable String objectKey) {
		Optional<OrderItem> opt = Optional.of(orderItemService.findByObjectKey(objectKey)
				.orElseThrow(() -> new ResourceNotFoundException(OrderItem.class.getSimpleName(), objectKey)));
		return toResponseEntity(opt, null, HttpStatus.OK);
	}

	/**
	 * {@code PUT  /order-item} : Updates an existing OrderItem.
	 *
	 * @param requestBody the OrderItem to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated OrderItem, or with status {@code 400 (Bad Request)} if
	 *         the requestBody is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the OrderItem couldn't be
	 *         updated.
	 */
	@PutMapping
	@Transactional
	@Operation(summary = "Update an existing OrderItem")
	public ResponseEntity<ViewOrderItemDto> update(@RequestBody @Valid EditOrderItemDto requestBody) {
		OrderItem entity = orderItemMappers.map(requestBody);
		entity = orderItemService.bulkUpdate(entity);
		ViewOrderItemDto dto = orderItemMappers.map(entity);
		return ResponseEntity.ok(dto);
	}

	/**
	 * {@code DELETE  /order-item/:objectKey} : Delete the order-item with given
	 * objectKey.
	 *
	 * @param objectKey the objectKey of the OrderItem to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/{objectKey:.+}")
	@Transactional
	@Operation(summary = "Delete the OrderItem with given objectKey")
	public ResponseEntity<ViewOrderItemDto> delete(@PathVariable String objectKey) {
		Optional<OrderItem> opt = Optional.of(orderItemService.delete(objectKey)
				.orElseThrow(() -> new ResourceNotFoundException(OrderItem.class.getSimpleName(), objectKey)));
		return toResponseEntity(opt, null, HttpStatus.OK);
	}

	/**
	 * {@code GET  /order-item/search?filter=:query} : Get the order-item filtered
	 * by given query.
	 *
	 * @param query the query to execute filtering the OrderItem to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the order-item.
	 */
	@GetMapping("/search")
	@Transactional(readOnly = true)
	@Operation(summary = "Get the OrderItem filtered by given query")
	public ResponseEntity<Page<ViewOrderItemDto>> search(@Filter Specification<OrderItem> specification,
			Pageable page) {
		Page<ViewOrderItemDto> collModel = orderItemMappers.map(orderItemService.search(specification, page));
		return toResponseEntityPaged(collModel, null);
	}

	/**
	 * GET /findByTheOrdine/ordineObjectKey: Search all OrderItem for the given
	 * Ordine (parent)
	 * 
	 * @param ordineObjectKey of Ordine
	 * @param pageable
	 * @return Page of OrderItem for the given Ordine (parent)
	 */
	@GetMapping("/findByTheOrdine/{ordineObjectKey:.+}")
	@Transactional(readOnly = true)
	@Operation(summary = "Get all OrderItem for the given Ordine (parent)")
	public ResponseEntity<Page<ViewOrderItemDto>> findByTheOrdine(@PathVariable String ordineObjectKey,
			Pageable pageable) {
		Ordine key = new Ordine(ordineObjectKey);
		Page<ViewOrderItemDto> collModel = orderItemMappers.map(orderItemService.findByTheOrdine(key, pageable));
		return toResponseEntityPaged(collModel, null);
	}

	/**
	 * GET /findByTheProduct/productObjectKey: Search all OrderItem for the given
	 * Product (parent)
	 * 
	 * @param productObjectKey of Product
	 * @param pageable
	 * @return Page of OrderItem for the given Product (parent)
	 */
	@GetMapping("/findByTheProduct/{productObjectKey:.+}")
	@Transactional(readOnly = true)
	@Operation(summary = "Get all OrderItem for the given Product (parent)")
	public ResponseEntity<Page<ViewOrderItemDto>> findByTheProduct(@PathVariable String productObjectKey,
			Pageable pageable) {
		Product key = new Product(productObjectKey);
		Page<ViewOrderItemDto> collModel = orderItemMappers.map(orderItemService.findByTheProduct(key, pageable));
		return toResponseEntityPaged(collModel, null);
	}

	private ResponseEntity<ViewOrderItemDto> toResponseEntity(Optional<OrderItem> maybeResponse, HttpHeaders header,
			HttpStatus status) {
		return maybeResponse.map(response -> new ResponseEntity<>(orderItemMappers.map(response), header, status))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	private <T> ResponseEntity<Page<T>> toResponseEntityPaged(Page<T> collModel, HttpHeaders header) {
		return ResponseEntity.ok().headers(header).body(collModel);
	}
}
