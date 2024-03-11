package it.yourstore.store.mapper;

import it.yourstore.store.dto.EditOrderItemDto;
import it.yourstore.store.dto.ViewOrderItemDto;
import it.yourstore.store.domain.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;
import java.util.Optional;

@Mapper
public interface OrderItemMappers {

	@Mappings({ @Mapping(source = "entityState", target = "entityState") })
	OrderItem map(EditOrderItemDto orderItemDto);

	ViewOrderItemDto map(OrderItem orderItem);

	default Page<ViewOrderItemDto> map(Page<OrderItem> page) {
		return page.map(this::map);
	}

	default Optional<ViewOrderItemDto> map(Optional<OrderItem> read) {
		return read.map(this::map);
	}
}
