package it.YourOnlineStore.Database;

import lombok.Setter;
import lombok.Getter;
import lombok.EqualsAndHashCode;

import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Data transfer object for edit a data element of type Utente
 */
public class Dto extends BaseDto {

	// Generated SERIAL VERSION UID
	private static final long serialVersionUID = 1516953304L;

	private List<Integer> productIds;

	public List<Integer> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<Integer> productIds) {
		this.productIds = productIds;
	}

	public List<Integer> getAmounts() {
		return amounts;
	}

	public void setAmounts(List<Integer> amounts) {
		this.amounts = amounts;
	}

	private List<Integer> amounts;
}
