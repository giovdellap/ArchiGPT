package it.yourstore.store.dto;

import lombok.Setter;
import lombok.Getter;

import java.math.BigInteger;

import lombok.EqualsAndHashCode;

/**
 * Data transfer object for view a data element of type Utente
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ViewUtenteDto extends BaseDto {

	// Generated SERIAL VERSION UID
	private static final long serialVersionUID = 1516953304L;

	private String objectKey;
	private String objectTitle;

	private Integer utenteId;

	private BigInteger oauthId;

	private String name;

	private String surname;

	private String email;

	private Boolean isAdmin;

}
