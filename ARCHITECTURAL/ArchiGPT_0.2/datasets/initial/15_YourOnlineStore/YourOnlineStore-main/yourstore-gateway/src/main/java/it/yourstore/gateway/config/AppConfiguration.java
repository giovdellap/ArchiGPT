package it.yourstore.gateway.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AppConfiguration {

	@Value("${app.config.correlationHeaderName}")
	private String correlationHeaderName;

}
