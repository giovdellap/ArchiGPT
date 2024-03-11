package it.yourstore.store.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.MapsId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Table;

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
@Table(name = "order_item")
public class OrderItem extends GenericEntity implements Serializable {

	// Generated SERIAL VERSION UID
	private static final long serialVersionUID = 2771621724L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(OrderItem.class);

	// COMPOSITE PRIMARY KEY
	@EmbeddedId
	private OrderItemKey theOrderItemKey = new OrderItemKey();

	// ATTRIBUTES
	@Column(name = "amount", columnDefinition = "INTEGER")
	private Integer amount;

	// IMPORTED PARENTS

	// CONSTRUCTORS
	public OrderItem(String objectKey) {
		super();
		setObjectKey(objectKey);
	}

	// CHILDREN

	// PARENTS
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@ToString.Exclude
	@JoinColumn(name = "ordine_id", referencedColumnName = "ordine_id", nullable = false, insertable = false, updatable = false)
	@MapsId("ordineId")
	private Ordine theOrdine;
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@ToString.Exclude
	@JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false, insertable = false, updatable = false)
	@MapsId("productId")
	private Product theProduct;

	// CHILD GETTER/SETTER

	// ADD CHILD

	// PARENT GETTER/SETTER
	/**
	 * @return the Ordine Id
	 */
	public Integer getOrdineId() {
		return theOrderItemKey.getOrdineId();
	}

	/**
	 * @param aordineId to set
	 */
	public void setOrdineId(Integer ordineId) {
		theOrderItemKey.setOrdineId(ordineId);
	}

	/**
	 * @return the Product Id
	 */
	public Integer getProductId() {
		return theOrderItemKey.getProductId();
	}

	/**
	 * @param aproductId to set
	 */
	public void setProductId(Integer productId) {
		theOrderItemKey.setProductId(productId);
	}

	// PARENT ID GETTER/SETTER

	// PARENT OBJECT TITLE
	/**
	 * Return the object title of theOrdine.
	 * 
	 * @return the object title of theOrdine.
	 */
	public String getTheOrdineObjectTitle() {
		return getTheOrdine() != null ? getTheOrdine().getObjectTitle() : null;
	}

	/**
	 * Return the object title of theProduct.
	 * 
	 * @return the object title of theProduct.
	 */
	public String getTheProductObjectTitle() {
		return getTheProduct() != null ? getTheProduct().getObjectTitle() : null;
	}

	// PARENT OBJECT KEY
	/**
	 * Return the object key of theOrdine.
	 * 
	 * @return the object key of theOrdine.
	 */
	@ToString.Include
	public String getTheOrdineObjectKey() {
		return getTheOrdine() != null ? getTheOrdine().getObjectKey() : null;
	}

	/**
	 * Set object key of theOrdine.
	 * 
	 */
	public void setTheOrdineObjectKey(String objectKey) {
		if (isValueChanged(getTheOrdineObjectKey(), objectKey, false)) {
			Ordine ordine = new Ordine();
			ordine.setObjectKey(objectKey);
			setTheOrdine(ordine);
		}
		if (getTheOrdine() != null) {
			theOrderItemKey.setOrdineId(getTheOrdine().getOrdineId());
		}
	}

	/**
	 * Return the object key of theProduct.
	 * 
	 * @return the object key of theProduct.
	 */
	@ToString.Include
	public String getTheProductObjectKey() {
		return getTheProduct() != null ? getTheProduct().getObjectKey() : null;
	}

	/**
	 * Set object key of theProduct.
	 * 
	 */
	public void setTheProductObjectKey(String objectKey) {
		if (isValueChanged(getTheProductObjectKey(), objectKey, false)) {
			Product product = new Product();
			product.setObjectKey(objectKey);
			setTheProduct(product);
		}
		if (getTheProduct() != null) {
			theOrderItemKey.setProductId(getTheProduct().getProductId());
		}
	}

	// IMPORTED PARENT OBJECT KEY

	// OBJECT KEY
	/**
	 * Restituisce l'identificativo della chiave composita in formato stringa
	 *
	 */
	public String getObjectKey() {
		return theOrderItemKey.getObjectKey();
	}

	/**
	 * Inizializza la parte identificativa chiamando il setter della chiave
	 * composita
	 *
	 */
	public void setObjectKey(String key) {
		theOrderItemKey.setObjectKey(key);
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
		OrderItem that = (OrderItem) o;
		return theOrderItemKey != null && Objects.equals(theOrderItemKey, that.theOrderItemKey);
	}

	@Override
	public int hashCode() {
		return Objects.hash(theOrderItemKey);
	}
}
