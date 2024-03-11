package it.yourstore.store.exception;

import java.util.HashMap;
import java.util.Map;

public class ResourceNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String resource;
	private String id;
	
	public ResourceNotFoundException(String id) {
		this("Entity", id);
	}
	
	public ResourceNotFoundException(String entityName, String id) {
		super(String.format("Resource <%s> with id <%s> not found", entityName, id));
		this.resource = entityName;
		this.id = id;
	}
	
	public Object getBody() {
		Map<String, String> body = new HashMap<String, String>();
		body.put("resource", resource);
		body.put("id", id);
		return body;
	}
}