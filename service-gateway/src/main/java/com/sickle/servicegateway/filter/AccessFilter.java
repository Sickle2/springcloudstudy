package com.sickle.servicegateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @program: springcloudDemo
 * @description:
 * @author: sickle
 * @create: 2019-08-15 14:35
 **/
@Component
public class AccessFilter implements GlobalFilter, Ordered {

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    private static final String[] permitPathPattern = new String[]{"/**/auth/**"};


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        for (String pathPattern : permitPathPattern) {
            if (pathMatcher.match(pathPattern, request.getPath().value())) {
                return chain.filter(exchange);
            }
        }

        HttpHeaders headers = request.getHeaders();
        //获取token，进行处理
//        String token = headers.getFirst("Authorization");
//        if (token == null || token.isEmpty()) {
//            ServerHttpResponse response = exchange.getResponse();
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            Response message = new Response().failure(ResponseMeta.UNAUTHORIZED);
//            byte[] bits = JSON.toJSONString(message).getBytes(StandardCharsets.UTF_8);
//            byte[] bits = "sssss".getBytes(StandardCharsets.UTF_8);
//            DataBuffer buffer = response.bufferFactory().wrap(bits);
//            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
//            return response.writeWith(Mono.just(buffer));
//        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
