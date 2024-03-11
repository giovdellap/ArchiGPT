package it.yourstore.store.service;

import java.util.List;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.stereotype.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.yourstore.store.domain.Utente;

import it.yourstore.store.repository.UtenteRepository;

import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

import it.yourstore.store.domain.Ordine;
import it.yourstore.store.domain.GenericEntity;

@RequiredArgsConstructor
@Service
public class UtenteServiceImpl implements UtenteService {

	private final UtenteRepository utenteRepository;
	// CHILD SERVICES
	private final OrdineService ordineService;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(UtenteServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.yourstore.store.service.GenericEntityService#findAll(org.
	 * springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Utente> findAll(Pageable pageable) {
		return utenteRepository.findAll(pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.yourstore.store.service.GenericEntityService#findAll()
	 */
	@Override
	public List<Utente> findAll() {
		return utenteRepository.findAll();
	}

	@Override
	public Optional<Utente> findByObjectKey(String objectKey) {
		Utente utente = new Utente(objectKey);
		return utenteRepository.findByUtenteId(utente.getUtenteId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.yourstore.store.service.GenericEntityService#exists(java.lang.
	 * Object)
	 */
	@Override
	public boolean exists(Integer id) {
		return utenteRepository.existsById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.yourstore.store.service.GenericEntityService#insert(java.
	 * lang.Object)
	 */
	@Override
	public Utente insert(@Valid Utente entity) {
		return utenteRepository.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.yourstore.store.service.GenericEntityService#update(java.
	 * lang.Object)
	 */
	@Override
	public Utente update(@Valid Utente entity) {
		return utenteRepository.save(entity);
	}

	@Override
	public Optional<Utente> delete(String objectKey) {
		return findByObjectKey(objectKey).map(utente -> {
			utenteRepository.delete(utente);
			return Optional.of(utente);
		}).orElseGet(Optional::empty);
	}

	@Override
	public Page<Utente> search(Specification<Utente> specification, Pageable pageable) {
		return utenteRepository.findAll(specification, pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.yourstore.store.service.GenericEntityService#deleteById(java.lang.
	 * Object)
	 */
	@Override
	public void deleteById(Integer id) {
		utenteRepository.deleteById(id);
	}

	@Override
	public Utente bulkUpdate(Utente utente) {
		if (utente.getTheOrdine() != null) {
			List<Ordine> updateTheOrdine = utente.getTheOrdine().stream().filter(child -> !child.isDeletedEntityState())
					.collect(Collectors.toList());
			List<Ordine> deleteTheOrdine = utente.getTheOrdine().stream().filter(GenericEntity::isDeletedEntityState)
					.collect(Collectors.toList());
			utente.setTheOrdine(updateTheOrdine);
			deleteTheOrdine.forEach(child -> ordineService.deleteById(child.getOrdineId()));
		}
		Utente update = this.update(utente);
		return update;
	}

}
