package it.yourstore.gateway.filter;

import it.yourstore.gateway.config.AppConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Component
public class CorrelationTrackingPostFilter implements GlobalFilter, Ordered {

	private final AppConfiguration config;

	public CorrelationTrackingPostFilter(AppConfiguration config) {
		this.config = config;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("Injecting correlation into response...");

		HttpHeaders header = exchange.getRequest().getHeaders();
		exchange.getResponse().getHeaders().add(config.getCorrelationHeaderName(), getCorrelationId(header));
		return chain.filter(exchange);
	}

	private String getCorrelationId(HttpHeaders header) {
		return Objects.requireNonNull(header.get(config.getCorrelationHeaderName())).iterator().next();
	}

	@Override
	public int getOrder() {
		return FilterOrderType.POST.getOrder();
	}
}

