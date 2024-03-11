package it.yourstore.gateway.filter;

import it.yourstore.gateway.config.AppConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
public class CorrelationTrackingPreFilter implements GlobalFilter, Ordered {

	private final AppConfiguration config;

	public CorrelationTrackingPreFilter(AppConfiguration config) {
		this.config = config;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("Tracking filter invoked...");

		ServerHttpRequest request = exchange.getRequest();
		HttpHeaders headers = request.getHeaders();

		if (headers.containsKey(config.getCorrelationHeaderName())) {
			log.info("Tracked request with correlation id {}", headers.get(config.getCorrelationHeaderName()));
		} else {
			request = exchange.getRequest().mutate()
					.header(config.getCorrelationHeaderName(), UUID.randomUUID().toString()).build();
			return chain.filter(exchange.mutate().request(request).build());
		}

		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return FilterOrderType.PRE.getOrder();
	}
}

