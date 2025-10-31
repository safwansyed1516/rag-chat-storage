package com.rag.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Very simple in-memory rate limiter.
 * Limits each client IP to N requests per minute.
 */
@Component
public class SimpleRateLimitFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(SimpleRateLimitFilter.class);

    @Value("${RATE_LIMIT_REQUESTS_PER_MINUTE:60}")
    private int limitPerMinute;

    // track <clientIp, Counter>
    private final Map<String, RequestCounter> requestMap = new ConcurrentHashMap<>();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String clientIp = getClientIp(exchange);

        RequestCounter counter = requestMap.computeIfAbsent(clientIp, k -> new RequestCounter());
        synchronized (counter) {
            long now = Instant.now().getEpochSecond();
            if (now - counter.timestamp >= 60) {
                // reset every minute
                counter.timestamp = now;
                counter.count = 0;
            }
            counter.count++;

            if (counter.count > limitPerMinute) {
                log.warn("Rate limit exceeded for IP: {} ({} requests/min)", clientIp, limitPerMinute);
                exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                exchange.getResponse().getHeaders().add("Retry-After", "60");
                return exchange.getResponse().setComplete();
            }
        }

        return chain.filter(exchange);
    }

    private String getClientIp(ServerWebExchange exchange) {
        InetSocketAddress remote = exchange.getRequest().getRemoteAddress();
        return remote != null ? remote.getAddress().getHostAddress() : "unknown";
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 10;
    }

    // inner class to hold timestamp + count
    private static class RequestCounter {
        long timestamp = Instant.now().getEpochSecond();
        int count = 0;
    }
}
