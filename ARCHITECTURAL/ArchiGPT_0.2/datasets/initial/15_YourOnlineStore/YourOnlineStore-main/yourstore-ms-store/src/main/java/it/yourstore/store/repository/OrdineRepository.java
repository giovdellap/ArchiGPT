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
import it.yourstore.store.domain.Ordine;

import it.yourstore.store.domain.Utente;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine, Integer>, JpaSpecificationExecutor<Ordine> {

	@EntityGraph(attributePaths = { "theUtente" }, type = EntityGraphType.FETCH)
	Optional<Ordine> findByOrdineId(Integer id);

	@EntityGraph(attributePaths = { "theUtente" }, type = EntityGraphType.FETCH)
	Page<Ordine> findByTheUtente(Utente parentEntity, Pageable pageable);

	@Query("DELETE FROM Ordine WHERE ordineId IN ?1")
	void deleteByIdIn(Collection<Integer> ids);
}
