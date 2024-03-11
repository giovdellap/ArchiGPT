package it.yourstore.store.exception;

import java.util.HashMap;
import java.util.Map;

public class DisponibilityException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String amount;
	
	public DisponibilityException(String id) {
		this("Product", id);
	}
	
	public DisponibilityException(String id, String amount) {
		super(String.format("Product <%s> is not available for the amount <%s>", id, amount));
		this.id = id;
		this.amount = amount;
	}
	
	public Object getBody() {
		Map<String, String> body = new HashMap<String, String>();
		body.put("id", id);
		body.put("amount", amount);
		return body;
	}
}