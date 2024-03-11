package it.yourstore.store.domain;

import java.io.Serializable;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.util.Collection;
import java.util.ArrayList;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
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

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "ordine")
public class Ordine extends GenericEntity implements Serializable {

	// Generated SERIAL VERSION UID
	private static final long serialVersionUID = 400727816L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(Ordine.class);

	// ATTRIBUTES
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ordine_id", columnDefinition = "INTEGER")
	private Integer ordineId;
	@Column(name = "date", columnDefinition = "DATETIME")
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
	private LocalDateTime date;
	@Column(name = "total_cost", columnDefinition = "FLOAT")
	private Float totalCost;
	@Column(name = "address", columnDefinition = "VARCHAR(80)")
	private String address;

	// IMPORTED PARENTS

	// CONSTRUCTORS
	public Ordine(String objectKey) {
		super();
		setObjectKey(objectKey);
	}

	// CHILDREN
	@OneToMany(mappedBy = "theOrdine", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@ToString.Exclude
	private Collection<OrderItem> theOrderItem = new ArrayList<>();

	// PARENTS
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@ToString.Exclude
	@JoinColumn(name = "utente_id", referencedColumnName = "utente_id", nullable = false)
	private Utente theUtente;

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
				orderItem.setTheOrdine(this);
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
	/**
	 * Return the utenteId from theUtente.
	 * 
	 * @return utenteId from theUtente.
	 */
	public Integer getUtenteId() {
		// If the parent entity object is null, then return null
		if (getTheUtente() == null) {
			return null;
		}
		// Return requested attribute
		return theUtente.getUtenteId();
	}

	// PARENT OBJECT TITLE
	/**
	 * Return the object title of theUtente.
	 * 
	 * @return the object title of theUtente.
	 */
	public String getTheUtenteObjectTitle() {
		return getTheUtente() != null ? getTheUtente().getObjectTitle() : null;
	}

	// PARENT OBJECT KEY
	/**
	 * Return the object key of theUtente.
	 * 
	 * @return the object key of theUtente.
	 */
	@ToString.Include
	public String getTheUtenteObjectKey() {
		return getTheUtente() != null ? getTheUtente().getObjectKey() : null;
	}

	/**
	 * Set object key of theUtente.
	 * 
	 */
	public void setTheUtenteObjectKey(String objectKey) {
		if (isValueChanged(getTheUtenteObjectKey(), objectKey, false)) {
			Utente utente = new Utente();
			utente.setObjectKey(objectKey);
			setTheUtente(utente);
		}
		if (getTheUtente() != null) {
			theUtente.setUtenteId(getTheUtente().getUtenteId());
		}
	}

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
		output.append(getOrdineId());
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
	}

	// OBJECT TITLE
	public String getObjectTitle() {
		StringBuilder output = new StringBuilder();
		output.append(getAddress());
		return output.toString();
	}

	// Equals / HashCode
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
			return false;
		Ordine that = (Ordine) o;
		return ordineId != null && Objects.equals(ordineId, that.ordineId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ordineId);
	}
}
