package com.rag.gateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class ApiKeyFilter implements GlobalFilter {
  @Value("${gateway.api-key}")
  private String key;

  @Override
  public Mono<Void> filter(ServerWebExchange ex, GatewayFilterChain chain){
    var apiKey = ex.getRequest().getHeaders().getFirst("x-api-key");
    if(apiKey == null || !apiKey.equals(key)){
      ex.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return ex.getResponse().setComplete();
    }
    return chain.filter(ex);
  }
}
