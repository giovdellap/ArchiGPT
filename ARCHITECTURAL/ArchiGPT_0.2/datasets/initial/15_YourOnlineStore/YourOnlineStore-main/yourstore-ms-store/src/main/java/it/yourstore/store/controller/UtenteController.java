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
import it.yourstore.store.domain.Utente;
import it.yourstore.store.dto.EditUtenteDto;
import it.yourstore.store.dto.ViewUtenteDto;
import it.yourstore.store.dto.ViewOrdineDto;
import it.yourstore.store.exception.ResourceAlreadyFoundException;
import it.yourstore.store.exception.ResourceNotFoundException;
import it.yourstore.store.mapper.UtenteMappers;
import it.yourstore.store.mapper.OrdineMappers;
import it.yourstore.store.service.UtenteService;
import it.yourstore.store.service.OrdineService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/utente", produces = MediaType.APPLICATION_JSON_VALUE)
public class UtenteController {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(UtenteController.class);

	/// ENTITY SERVICE
	private final UtenteService utenteService;

	private final UtenteMappers utenteMappers;
	// CHILD SERVICES
	private final OrdineService ordineService;
	// CHILD MAPPER
	private final OrdineMappers ordineMappers;

	// API
	/**
	 * {@code GET /utente} : Get all utente.
	 * 
	 * @param pageable
	 * @return Page of all Utente.
	 */
	@GetMapping
	@Transactional(readOnly = true)
	@Operation(summary = "Get all Utente")
	public ResponseEntity<Page<ViewUtenteDto>> findAll(Pageable pageable) {
		Page<ViewUtenteDto> collModel = utenteMappers.map(utenteService.findAll(pageable));
		return toResponseEntityPaged(collModel, null);
	}

	/**
	 * {@code POST  /utente} : Create a new Utente.
	 *
	 * @param requestBody the Utente to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new Utente, or with status {@code 400 (Bad Request)} if the
	 *         requestBody is invalid.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping
	@Transactional
	@Operation(summary = "Create a new Utente")
	public ResponseEntity<ViewUtenteDto> insert(@RequestBody @Valid EditUtenteDto requestBody) {
		Utente entity = utenteMappers.map(requestBody);
		if (utenteService.findByObjectKey(entity.getObjectKey()).isPresent()) {
			throw new ResourceAlreadyFoundException(Utente.class.getSimpleName(), entity.getObjectKey());
		} else {
			entity = utenteService.insert(entity);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(entity.getUtenteId()).toUri();
			ViewUtenteDto dto = utenteMappers.map(entity);
			return ResponseEntity.created(location).body(dto);
		}
	}

	/**
	 * {@code GET  /utente/:objectKey} : Get the utente with given objectKey.
	 *
	 * @param objectKey the objectKey of the utente to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the utente, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/{objectKey:.+}")
	@Transactional(readOnly = true)
	@Operation(summary = "Get the Utente with given objectKey")
	public ResponseEntity<ViewUtenteDto> read(@PathVariable String objectKey) {
		Optional<Utente> opt = Optional.of(utenteService.findByObjectKey(objectKey)
				.orElseThrow(() -> new ResourceNotFoundException(Utente.class.getSimpleName(), objectKey)));
		return toResponseEntity(opt, null, HttpStatus.OK);
	}

	/**
	 * {@code PUT  /utente} : Updates an existing Utente.
	 *
	 * @param requestBody the Utente to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated Utente, or with status {@code 400 (Bad Request)} if the
	 *         requestBody is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the Utente couldn't be
	 *         updated.
	 */
	@PutMapping
	@Transactional
	@Operation(summary = "Update an existing Utente")
	public ResponseEntity<ViewUtenteDto> update(@RequestBody @Valid EditUtenteDto requestBody) {
		Utente entity = utenteMappers.map(requestBody);
		entity = utenteService.bulkUpdate(entity);
		ViewUtenteDto dto = utenteMappers.map(entity);
		return ResponseEntity.ok(dto);
	}

	/**
	 * {@code DELETE  /utente/:objectKey} : Delete the utente with given objectKey.
	 *
	 * @param objectKey the objectKey of the Utente to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/{objectKey:.+}")
	@Transactional
	@Operation(summary = "Delete the Utente with given objectKey")
	public ResponseEntity<ViewUtenteDto> delete(@PathVariable String objectKey) {
		Optional<Utente> opt = Optional.of(utenteService.delete(objectKey)
				.orElseThrow(() -> new ResourceNotFoundException(Utente.class.getSimpleName(), objectKey)));
		return toResponseEntity(opt, null, HttpStatus.OK);
	}

	/**
	 * {@code GET  /utente/search?filter=:query} : Get the utente filtered by given
	 * query.
	 *
	 * @param query the query to execute filtering the Utente to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the utente.
	 */
	@GetMapping("/search")
	@Transactional(readOnly = true)
	@Operation(summary = "Get the Utente filtered by given query")
	public ResponseEntity<Page<ViewUtenteDto>> search(@Filter Specification<Utente> specification, Pageable page) {
		Page<ViewUtenteDto> collModel = utenteMappers.map(utenteService.search(specification, page));
		return toResponseEntityPaged(collModel, null);
	}

	/**
	 * GET /{objectKey:.+}/ordine: Get all Ordine (childs) for the given Utente by
	 * objectKey
	 * 
	 * @param objectKey ObjectKey of Utente
	 * @param pageable
	 * @return Page of all Ordine for the given Utente
	 */
	@GetMapping("/{objectKey:.+}/ordine")
	@Transactional(readOnly = true)
	@Operation(summary = "Get all Ordine (childs) for the given Utente by objectKey")
	public ResponseEntity<Page<ViewOrdineDto>> getTheOrdineByObjectKey(@PathVariable String objectKey,
			Pageable pageable) {
		Utente utente = new Utente();
		utente.setObjectKey(objectKey);
		Page<ViewOrdineDto> collModel = ordineMappers.map(ordineService.findByTheUtente(utente, pageable));
		return toResponseEntityPaged(collModel, null);
	}

	private ResponseEntity<ViewUtenteDto> toResponseEntity(Optional<Utente> maybeResponse, HttpHeaders header,
			HttpStatus status) {
		return maybeResponse.map(response -> new ResponseEntity<>(utenteMappers.map(response), header, status))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	private <T> ResponseEntity<Page<T>> toResponseEntityPaged(Page<T> collModel, HttpHeaders header) {
		return ResponseEntity.ok().headers(header).body(collModel);
	}
}
