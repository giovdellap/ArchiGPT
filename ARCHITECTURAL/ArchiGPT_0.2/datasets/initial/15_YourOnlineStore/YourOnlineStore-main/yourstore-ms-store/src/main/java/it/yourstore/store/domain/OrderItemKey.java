package it.yourstore.store.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Embeddable
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrderItemKey extends GenericEntity implements Serializable {

	// Generated SERIAL UID
	private static final long serialVersionUID = 2771621724L;

	// ATTRIBUTES
	@Column(name = "ordine_id", columnDefinition = "INTEGER")
	private Integer ordineId;
	@Column(name = "product_id", columnDefinition = "INTEGER")
	private Integer productId;

	// Getter & Setter for parent entity attributes

	// Getter & Setter for Object Key
	/**
	 * Restituisce l'identificativo della chiave in formato stringa. Ritorna
	 * conveniente nelle selezioni da lista.
	 * 
	 * @return L'identificativo della chiave in formato pk1||pk2||pk3...
	 */
	public String getObjectKey() {
		StringBuilder output = new StringBuilder();
		output.append(getOrdineId());
		output.append(getRowIdFieldDelimiter());
		output.append(getProductId());
		return output.toString();
	}

	/**
	 * Inizializza la parte identificativa del bean in base alla stringa tokenizzata
	 * da "||" fornita in input.
	 * 
	 * @param key L'identificativo della chiave in formato pk1||pk2||pk3...
	 */
	public void setObjectKey(String key) {
		if (key == null || key.trim().length() == 0) {
			return;
		}
		String[] array = StringUtils.splitByWholeSeparatorPreserveAllTokens(key, getRowIdFieldDelimiter());
		int ctr = 0;
		setOrdineId(getIntegerCheckedAgainstNullContent(array[ctr++]));
		setProductId(getIntegerCheckedAgainstNullContent(array[ctr++]));
	}
}
