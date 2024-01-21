package org.quanta.gateway.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quanta.gateway.beans.GatewayResponse;
import org.quanta.gateway.config.IPFilterConfig;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Description: ip过滤器 支持白名单黑名单
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/12/19
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class IPFilter implements GlobalFilter, Ordered {
    private final IPFilterConfig ipFilterConfig;

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String ip = exchange.getRequest().getHeaders().getHost().getHostName();
        log.info(ip);
        ServerHttpResponse response = exchange.getResponse();
        // 黑名单拦截
        if (ipFilterConfig.getBLACK_LIST() != null
                && ipFilterConfig.getBLACK_LIST().getEnable()
                && ipFilterConfig.getBLACK_LIST().getIpList() != null
                && ipFilterConfig.getBLACK_LIST().getIpList().contains(ip)
        ) {
            // 在黑名单中
            return GatewayResponse.blocked(response);
        }

        // 白名单过滤
        if (ipFilterConfig.getWHITE_LIST() != null
                && ipFilterConfig.getWHITE_LIST().getEnable()
                && ipFilterConfig.getWHITE_LIST().getIpList() != null
                && !ipFilterConfig.getWHITE_LIST().getIpList().contains(ip)) {
            // 不在白名单中
            return GatewayResponse.blocked(response);
        }
        // 放行
        return chain.filter(exchange);
    }




}
