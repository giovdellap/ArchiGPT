package it.yourstore.store.actuator;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "yourstoremsstore")
public class YourstoreMsStoreProperties {
	private String path;
}
