package it.yourstore.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import it.yourstore.store.domain.Utente;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Integer>, JpaSpecificationExecutor<Utente> {

	Optional<Utente> findByUtenteId(Integer id);
	
	Optional<Utente> findByOauthId(String oauthId);
	
	@Query("DELETE FROM Utente WHERE utenteId IN ?1")
	void deleteByIdIn(Collection<Integer> ids);
}
