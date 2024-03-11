package it.yourstore.store.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

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
@Table(name = "utente")
public class Utente extends GenericEntity implements Serializable {

	// Generated SERIAL VERSION UID
	private static final long serialVersionUID = 1516953304L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(Utente.class);

	// ATTRIBUTES
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "utente_id", columnDefinition = "INTEGER")
	private Integer utenteId;
	@Column(name = "oauth_id", columnDefinition = "VARCHAR(80)")
	private String oauthId;
	@Column(name = "name", columnDefinition = "VARCHAR(80)")
	private String name;
	@Column(name = "surname", columnDefinition = "VARCHAR(80)")
	private String surname;
	@Column(name = "email", columnDefinition = "VARCHAR(80)")
	private String email;
	@Column(name = "is_admin", columnDefinition = "BOOLEAN")
	private Boolean isAdmin;

	// IMPORTED PARENTS

	// CONSTRUCTORS
	public Utente(String objectKey) {
		super();
		setObjectKey(objectKey);
	}

	// CHILDREN
	@OneToMany(mappedBy = "theUtente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@ToString.Exclude
	private Collection<Ordine> theOrdine = new ArrayList<>();

	// PARENTS

	// CHILD GETTER/SETTER
	/**
	 * @return the Ordine
	 */
	public Collection<Ordine> getTheOrdine() {
		return theOrdine;
	}

	/**
	 * @param aOrdineList to set
	 */
	public void setTheOrdine(Collection<Ordine> aOrdineList) {
		if (aOrdineList != null) {
			for (Ordine ordine : aOrdineList) {
				ordine.setTheUtente(this);
			}
		}
		theOrdine = aOrdineList;
	}

	// ADD CHILD
	public void addOrdine(Ordine ordine) {
		theOrdine.add(ordine);
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
		output.append(getUtenteId());
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

		setUtenteId(getIntegerCheckedAgainstNullContent(array[ctr++]));
	}

	// OBJECT TITLE
	public String getObjectTitle() {
		StringBuilder output = new StringBuilder();
		output.append(getName());
		return output.toString();
	}

	// Equals / HashCode
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
			return false;
		Utente that = (Utente) o;
		return utenteId != null && Objects.equals(utenteId, that.utenteId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(utenteId);
	}
}
