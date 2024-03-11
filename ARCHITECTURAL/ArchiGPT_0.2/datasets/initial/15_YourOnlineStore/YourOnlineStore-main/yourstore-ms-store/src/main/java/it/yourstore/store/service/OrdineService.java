package it.yourstore.store.service;

import it.yourstore.store.domain.OrderItem;
import it.yourstore.store.domain.Ordine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import it.yourstore.store.domain.Utente;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

import javax.validation.constraints.NotNull;

public interface OrdineService extends GenericEntityService<Ordine, Integer> {

	// PARENT-SPECIFIC SERVICES
	Page<Ordine> findByTheUtente(Utente parentEntity, Pageable pageable);

	Ordine bulkUpdate(Ordine ordine);

	Ordine update(Ordine ordine);

	Optional<Ordine> delete(String objectKey);

	Page<Ordine> search(Specification<Ordine> specification, Pageable pageable);

	Ordine buy(Ordine entity);
	
	Ordine findCurrentOrdineByTheUtente(String utenteId);

	void checkDisponibility(Ordine entity);

	void updateOrdineCost(OrderItem entity);

}
