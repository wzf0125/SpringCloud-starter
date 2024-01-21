package org.quanta.gateway.filter;

import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import org.quanta.gateway.beans.GatewayResponse;
import org.quanta.gateway.config.GatewayRouteConfig;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Description: 路径拦截
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/12/19
 */
@Configuration
@RequiredArgsConstructor
public class RouteFilter implements GlobalFilter, Ordered {
    private final GatewayRouteConfig gatewayRouteConfig;

    /**
     * 如果需要无服务名称的路径拦截
     * 将Order调整至>=2
     */
    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        // 黑名单为空 直接放行
        if (CollUtil.isEmpty(gatewayRouteConfig.getBlackList())) {
            return chain.filter(exchange);
        }
        // 用Spring提供的antPathMatcher匹配
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        // 任意匹配到一个规则
        if (gatewayRouteConfig.getBlackList().stream().anyMatch(rule -> antPathMatcher.match(rule, path))) {
            return GatewayResponse.blocked(exchange.getResponse());
        }
        // 放行
        return chain.filter(exchange);
    }

}
