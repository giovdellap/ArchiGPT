package it.yourstore.store.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import java.util.Collection;
import java.util.ArrayList;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "product")
public class Product extends GenericEntity implements Serializable {

	// Generated SERIAL VERSION UID
	private static final long serialVersionUID = 3964889677L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(Product.class);

	// ATTRIBUTES
	@Id
	@Column(name = "product_id", columnDefinition = "INTEGER")
	private Integer productId;
	
	@Column(name = "cost", columnDefinition = "FLOAT")
	private Float cost;

	// IMPORTED PARENTS

	// CONSTRUCTORS
	public Product(String objectKey) {
		super();
		setObjectKey(objectKey);
	}

	// CHILDREN
	@OneToMany(mappedBy = "theProduct", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@ToString.Exclude
	private Collection<OrderItem> theOrderItem = new ArrayList<>();

	// PARENTS

	// CHILD GETTER/SETTER
	/**
	 * @return the OrderItem
	 */
	public Collection<OrderItem> getTheOrderItem() {
		return theOrderItem;
	}

	/**
	 * @param aOrderItemList to set
	 */
	public void setTheOrderItem(Collection<OrderItem> aOrderItemList) {
		if (aOrderItemList != null) {
			for (OrderItem orderItem : aOrderItemList) {
				orderItem.setTheProduct(this);
			}
		}
		theOrderItem = aOrderItemList;
	}

	// ADD CHILD
	public void addOrderItem(OrderItem orderItem) {
		theOrderItem.add(orderItem);
	}

	// PARENT GETTER/SETTER

	// PARENT ID GETTER/SETTER

	// PARENT OBJECT TITLE

	// PARENT OBJECT KEY

	// IMPORTED PARENT OBJECT KEY

	// OBJECT KEY
	/**
	 * Restituisce l'identificativo della chiave in formato stringa. Ritorna
	 * conveniente nelle selezioni da lista.
	 * 
	 * @return L'identificativo della chiave in formato pk1||pk2||pk3...
	 */
	public String getObjectKey() {
		StringBuilder output = new StringBuilder();
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

		setProductId(getIntegerCheckedAgainstNullContent(array[ctr++]));
	}

	// OBJECT TITLE
	public String getObjectTitle() {
		StringBuilder output = new StringBuilder();
		output.append(getObjectKey());
		return output.toString();
	}

	// Equals / HashCode
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
			return false;
		Product that = (Product) o;
		return productId != null && Objects.equals(productId, that.productId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(productId);
	}
}
