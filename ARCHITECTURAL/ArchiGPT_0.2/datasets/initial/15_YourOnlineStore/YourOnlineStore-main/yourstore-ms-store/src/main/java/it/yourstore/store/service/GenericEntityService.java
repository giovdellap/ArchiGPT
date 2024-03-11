package it.yourstore.store.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenericEntityService<TYPE, ID> {
	Page<TYPE> findAll(Pageable pageable);
	
	List<TYPE> findAll();

	boolean exists(ID id);

	TYPE insert(@Valid TYPE entity);

	TYPE update(@Valid TYPE entity);

	void deleteById(ID id);
	
	Optional<TYPE> findByObjectKey(String objectKey);
}
