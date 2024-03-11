package it.yourstore.store.mapper;

import it.yourstore.store.dto.EditOrdineDto;
import it.yourstore.store.dto.ViewOrdineDto;
import it.yourstore.store.domain.Ordine;
import it.yourstore.store.domain.OrderItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;
import java.util.Optional;

@Mapper
public interface OrdineMappers {

	@Mappings({ @Mapping(source = "entityState", target = "entityState") })
	Ordine map(EditOrdineDto ordineDto);

	ViewOrdineDto map(Ordine ordine);

	default Page<ViewOrdineDto> map(Page<Ordine> page) {
		return page.map(this::map);
	}

	default Optional<ViewOrdineDto> map(Optional<Ordine> read) {
		return read.map(this::map);
	}

	@AfterMapping
	default void propagateKeyInChildren(@MappingTarget Ordine bean) {
		String key = bean.getObjectKey();
		if(bean.getTheOrderItem() != null)
			for (OrderItem item : bean.getTheOrderItem()) {
				item.setTheOrdineObjectKey(key);
			}
	}
}
