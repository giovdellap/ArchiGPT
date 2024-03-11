package it.yourstore.store.mapper;

import it.yourstore.store.dto.EditProductDto;
import it.yourstore.store.dto.ViewProductDto;
import it.yourstore.store.domain.Product;
import it.yourstore.store.domain.OrderItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;
import java.util.Optional;

@Mapper
public interface ProductMappers {

	@Mappings({ @Mapping(source = "entityState", target = "entityState") })
	Product map(EditProductDto productDto);

	ViewProductDto map(Product product);

	default Page<ViewProductDto> map(Page<Product> page) {
		return page.map(this::map);
	}

	default Optional<ViewProductDto> map(Optional<Product> read) {
		return read.map(this::map);
	}

	@AfterMapping
	default void propagateKeyInChildren(@MappingTarget Product bean) {
		String key = bean.getObjectKey();
		if(bean.getTheOrderItem() != null)
			for (OrderItem item : bean.getTheOrderItem()) {
				item.setTheProductObjectKey(key);
			}
	}
}
