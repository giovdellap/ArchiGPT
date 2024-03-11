package it.yourstore.store.dto;

import lombok.Setter;
import lombok.Getter;
import lombok.EqualsAndHashCode;

/**
 * Data transfer object for view a data element of type OrderItem
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ViewOrderItemDto extends BaseDto {

	// Generated SERIAL VERSION UID
	private static final long serialVersionUID = 2771621724L;

	private String objectKey;
	private String objectTitle;

	private Integer amount;

	private String theOrdineObjectKey;

	private String theOrdineObjectTitle;

	private String theProductObjectKey;

	private String theProductObjectTitle;
}
