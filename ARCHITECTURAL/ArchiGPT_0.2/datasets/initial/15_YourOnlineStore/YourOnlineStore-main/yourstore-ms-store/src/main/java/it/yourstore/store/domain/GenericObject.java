package it.yourstore.store.domain;

import org.springframework.data.annotation.Transient;

public class GenericObject {

	private static final String STATE_UPDATED = "U";
	private static final String STATE_DELETED = "D";
	private static final String STATE_CREATED = "C";

	@Transient
	private String entityState = "";
	
	public String getEntityState() {
		return entityState;
	}

	public void setEntityState(String entityState) {
		this.entityState = entityState;
	}

	public void setUpdatedEntityState() {
		setEntityState(STATE_UPDATED);
	}

	public void setDeletetedEntityState() {
		setEntityState(STATE_DELETED);
	}

	public void setCreatedEntityState() {
		setEntityState(STATE_CREATED);
	}

	public void resetEntityState() {
		setEntityState("");
	}

	public boolean isUpdatedEntityState() {
		return (STATE_UPDATED.equals(getEntityState()));
	}

	public boolean isDeletedEntityState() {
		return (STATE_DELETED.equals(getEntityState()));
	}

	public boolean isCreatedEntityState() {
		return (STATE_CREATED.equals(getEntityState()));
	}
}
