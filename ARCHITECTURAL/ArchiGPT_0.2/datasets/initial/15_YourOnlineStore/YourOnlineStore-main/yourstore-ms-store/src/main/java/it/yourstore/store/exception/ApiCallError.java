package it.yourstore.store.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiCallError {
	private String reasonPhrase; // HTTP STATUS REASON PHRASE
	private Object message; // BREAF ERROR DESCRIPTION
	private String instance; // PATH

	public ApiCallError(String reasonPhrase, Object message) {
		this.reasonPhrase = reasonPhrase;
		this.message = message;
	}

//	public ApiCallError(String reasonPhrase, List<Map<String, Object>> details) {
//		this.reasonPhrase = reasonPhrase;
//		this.message = "[";
//
//		details.forEach(detail -> {
//			this.message += convertWithStream(detail);
//		});
//
//		this.message += "]";
//	}
//
//	public ApiCallError(String reasonPhrase, Map<String, Object> detail) {
//		this.reasonPhrase = reasonPhrase;
//		this.message += convertWithStream(detail);
//	}
//
//	public static String convertWithStream(Map<String, Object> map) {
//		String mapAsString = map.keySet().stream().map(key -> key + " = " + map.get(key))
//				.collect(Collectors.joining(", ", "{ ", " }"));
//		return mapAsString;
//	}
}
