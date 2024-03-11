package it.yourstore.store.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
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
import it.yourstore.store.domain.Ordine;
import it.yourstore.store.domain.Utente;
import it.yourstore.store.dto.EditOrdineDto;
import it.yourstore.store.dto.ViewOrderItemDto;
import it.yourstore.store.dto.ViewOrdineDto;
import it.yourstore.store.exception.DisponibilityException;
import it.yourstore.store.exception.ResourceAlreadyFoundException;
import it.yourstore.store.exception.ResourceNotFoundException;
import it.yourstore.store.mapper.OrderItemMappers;
import it.yourstore.store.mapper.OrdineMappers;
import it.yourstore.store.service.OrderItemService;
import it.yourstore.store.service.OrdineService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/ordine", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrdineController {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(OrdineController.class);

	/// ENTITY SERVICE
	private final OrdineService ordineService;

	private final OrdineMappers ordineMappers;
	// CHILD SERVICES
	private final OrderItemService orderItemService;
	// CHILD MAPPER
	private final OrderItemMappers orderItemMappers;

	// API
	/**
	 * {@code GET /ordine} : Get all ordine.
	 * 
	 * @param pageable
	 * @return Page of all Ordine.
	 */
	@GetMapping
	@Transactional(readOnly = true)
	@Operation(summary = "Get all Ordine")
	public ResponseEntity<Page<ViewOrdineDto>> findAll(Pageable pageable) {
		Page<ViewOrdineDto> collModel = ordineMappers.map(ordineService.findAll(pageable));
		return toResponseEntityPaged(collModel, null);
	}

	/**
	 * {@code POST  /ordine} : Create a new Ordine.
	 *
	 * @param requestBody the Ordine to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new Ordine, or with status {@code 400 (Bad Request)} if the
	 *         requestBody is invalid.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping
	@Transactional
	@Operation(summary = "Create a new Ordine")
	public ResponseEntity<ViewOrdineDto> insert(@RequestBody @Valid EditOrdineDto requestBody) {
		Ordine entity = ordineMappers.map(requestBody);
		if (ordineService.findByObjectKey(entity.getObjectKey()).isPresent()) {
			throw new ResourceAlreadyFoundException(Ordine.class.getSimpleName(), entity.getObjectKey());
		} else {
			entity = ordineService.insert(entity);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(entity.getOrdineId()).toUri();
			ViewOrdineDto dto = ordineMappers.map(entity);
			return ResponseEntity.created(location).body(dto);
		}
	}

	@GetMapping("/buy/{objectKey}/{date}/{totalCost}")
	@Transactional
	@Operation(summary = "Buy an Ordine")
	public ResponseEntity<ViewOrdineDto> buy(@PathVariable String objectKey, @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, @PathVariable Float totalCost) {
		LOGGER.info(objectKey);
		LOGGER.info(date);
		Optional<Ordine> entityOptional = ordineService.findByObjectKey(objectKey);
		Ordine entity = entityOptional.get();
		if ((!entityOptional.isPresent()) || entity.getDate() != null) {
			throw new ResourceNotFoundException(Ordine.class.getSimpleName(), entity.getObjectKey());
		} else {
			try {
				ordineService.checkDisponibility(entity);
			} catch (DisponibilityException e) {
				throw e;
			}
			entity.setTotalCost(totalCost);
			entity = ordineService.buy(entity);
			ViewOrdineDto dto = ordineMappers.map(entity);
			return ResponseEntity.ok(dto);
		}

	}

	/**
	 * {@code GET  /ordine/:objectKey} : Get the ordine with given objectKey.
	 *
	 * @param objectKey the objectKey of the ordine to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the ordine, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/{objectKey:.+}")
	@Transactional(readOnly = true)
	@Operation(summary = "Get the Ordine with given objectKey")
	public ResponseEntity<ViewOrdineDto> read(@PathVariable String objectKey) {
		Optional<Ordine> opt = Optional.of(ordineService.findByObjectKey(objectKey)
				.orElseThrow(() -> new ResourceNotFoundException(Ordine.class.getSimpleName(), objectKey)));
		return toResponseEntity(opt, null, HttpStatus.OK);
	}

	/**
	 * {@code PUT  /ordine} : Updates an existing Ordine.
	 *
	 * @param requestBody the Ordine to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated Ordine, or with status {@code 400 (Bad Request)} if the
	 *         requestBody is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the Ordine couldn't be
	 *         updated.
	 */
	@PutMapping
	@Transactional
	@Operation(summary = "Update an existing Ordine")
	public ResponseEntity<ViewOrdineDto> update(@RequestBody @Valid EditOrdineDto requestBody) {
		Ordine entity = ordineMappers.map(requestBody);
		entity = ordineService.bulkUpdate(entity);
		ViewOrdineDto dto = ordineMappers.map(entity);
		return ResponseEntity.ok(dto);
	}

	/**
	 * {@code DELETE  /ordine/:objectKey} : Delete the ordine with given objectKey.
	 *
	 * @param objectKey the objectKey of the Ordine to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/{objectKey:.+}")
	@Transactional
	@Operation(summary = "Delete the Ordine with given objectKey")
	public ResponseEntity<ViewOrdineDto> delete(@PathVariable String objectKey) {
		Optional<Ordine> opt = Optional.of(ordineService.delete(objectKey)
				.orElseThrow(() -> new ResourceNotFoundException(Ordine.class.getSimpleName(), objectKey)));
		return toResponseEntity(opt, null, HttpStatus.OK);
	}

	/**
	 * {@code GET  /ordine/search?filter=:query} : Get the ordine filtered by given
	 * query.
	 *
	 * @param query the query to execute filtering the Ordine to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the ordine.
	 */
	@GetMapping("/search")
	@Transactional(readOnly = true)
	@Operation(summary = "Get the Ordine filtered by given query")
	public ResponseEntity<Page<ViewOrdineDto>> search(@Filter Specification<Ordine> specification, Pageable page) {
		Page<ViewOrdineDto> collModel = ordineMappers.map(ordineService.search(specification, page));
		return toResponseEntityPaged(collModel, null);
	}

	/**
	 * GET /{objectKey:.+}/order-item: Get all OrderItem (childs) for the given
	 * Ordine by objectKey
	 * 
	 * @param objectKey ObjectKey of Ordine
	 * @param pageable
	 * @return Page of all OrderItem for the given Ordine
	 */
	@GetMapping("/{objectKey:.+}/order-item")
	@Transactional(readOnly = true)
	@Operation(summary = "Get all OrderItem (childs) for the given Ordine by objectKey")
	public ResponseEntity<Page<ViewOrderItemDto>> getTheOrderItemByObjectKey(@PathVariable String objectKey,
			Pageable pageable) {
		Ordine ordine = new Ordine();
		ordine.setObjectKey(objectKey);
		Page<ViewOrderItemDto> collModel = orderItemMappers.map(orderItemService.findByTheOrdine(ordine, pageable));
		return toResponseEntityPaged(collModel, null);
	}

	/**
	 * GET /findByTheUtente/utenteObjectKey: Search all Ordine for the given Utente
	 * (parent)
	 * 
	 * @param utenteObjectKey of Utente
	 * @param pageable
	 * @return Page of Ordine for the given Utente (parent)
	 */
	@GetMapping("/findByTheUtente/{utenteObjectKey:.+}")
	@Transactional(readOnly = true)
	@Operation(summary = "Get all Ordine for the given Utente (parent)")
	public ResponseEntity<Page<ViewOrdineDto>> findByTheUtente(@PathVariable String utenteObjectKey,
			Pageable pageable) {
		Utente key = new Utente(utenteObjectKey);
		Page<ViewOrdineDto> collModel = ordineMappers.map(ordineService.findByTheUtente(key, pageable));
		return toResponseEntityPaged(collModel, null);
	}

	@GetMapping("/current-ordine/{utenteId:.+}")
	public ViewOrdineDto currentOrdine(@PathVariable String utenteId) {
		Ordine currentOrdine = ordineService.findCurrentOrdineByTheUtente(utenteId);
		return ordineMappers.map(currentOrdine);
	}

	private ResponseEntity<ViewOrdineDto> toResponseEntity(Optional<Ordine> maybeResponse, HttpHeaders header,
			HttpStatus status) {
		return maybeResponse.map(response -> new ResponseEntity<>(ordineMappers.map(response), header, status))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	private <T> ResponseEntity<Page<T>> toResponseEntityPaged(Page<T> collModel, HttpHeaders header) {
		return ResponseEntity.ok().headers(header).body(collModel);
	}
}
