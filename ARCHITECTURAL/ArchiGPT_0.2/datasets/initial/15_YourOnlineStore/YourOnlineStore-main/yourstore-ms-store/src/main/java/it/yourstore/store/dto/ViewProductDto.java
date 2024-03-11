package it.yourstore.store.dto;

import lombok.Setter;
import lombok.Getter;
import lombok.EqualsAndHashCode;

/**
 * Data transfer object for view a data element of type Product
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ViewProductDto extends BaseDto {

	// Generated SERIAL VERSION UID
	private static final long serialVersionUID = 3964889677L;

	private String objectKey;
	private String objectTitle;

	private Integer productId;
	
	private Float cost;

}
