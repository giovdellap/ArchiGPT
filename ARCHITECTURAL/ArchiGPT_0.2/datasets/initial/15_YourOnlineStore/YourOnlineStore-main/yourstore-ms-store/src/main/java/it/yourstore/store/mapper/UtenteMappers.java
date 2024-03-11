package it.yourstore.store.mapper;

import it.yourstore.store.dto.EditUtenteDto;
import it.yourstore.store.dto.ViewUtenteDto;
import it.yourstore.store.domain.Utente;
import it.yourstore.store.domain.Ordine;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;
import java.util.Optional;

@Mapper
public interface UtenteMappers {

	@Mappings({ @Mapping(source = "entityState", target = "entityState") })
	Utente map(EditUtenteDto utenteDto);

	ViewUtenteDto map(Utente utente);

	default Page<ViewUtenteDto> map(Page<Utente> page) {
		return page.map(this::map);
	}

	default Optional<ViewUtenteDto> map(Optional<Utente> read) {
		return read.map(this::map);
	}

	@AfterMapping
	default void propagateKeyInChildren(@MappingTarget Utente bean) {
		String key = bean.getObjectKey();
		if(bean.getTheOrdine() != null)
			for (Ordine item : bean.getTheOrdine()) {
				item.setTheUtenteObjectKey(key);
			}
	}
}
