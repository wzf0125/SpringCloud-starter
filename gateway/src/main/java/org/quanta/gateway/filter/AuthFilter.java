package org.quanta.gateway.filter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.quanta.base.constants.AuthConstants;
import org.quanta.core.constant.cache.TokenCache;
import org.quanta.core.constants.JWTConstants;
import org.quanta.core.constants.JWTKeyConstants;
import org.quanta.core.utils.JWTUtils;
import org.quanta.core.utils.RedisUtils;
import org.quanta.gateway.beans.GatewayResponse;
import org.quanta.gateway.config.GatewayRouteConfig;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/15
 */
@Configuration
@RequiredArgsConstructor
public class AuthFilter implements GlobalFilter, Ordered {
    private final GatewayRouteConfig gatewayRouteConfig;
    private final JWTUtils jwtUtils;
    private final RedisUtils redisUtils;
    private final JWTConstants jwtConstants;

    /**
     * /app-auth/oauth/token
     * 如果需要无服务名称的路径拦截
     * 将Order调整至>=2  ->  /oauth/token
     */
    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        // TODO 使用责任链重构..
        // 白名单跳过基础验证
        if (skipWhiteListPath(path)) {
            return chain.filter(exchange);
        }
        String tokenHeader = request.getHeaders().getFirst(AuthConstants.TOKEN_KEY);
        if (StrUtil.isBlank(tokenHeader)) {
            return GatewayResponse.blocked(exchange.getResponse(), "请求未认证");
        }
        // 校验解析Jwt
        String jwt = jwtUtils.getJWT(tokenHeader);
        // jwt过期
        if (StrUtil.isBlank(jwt)) {
            return GatewayResponse.blocked(exchange.getResponse(), "请求未认证");
        }
        // 放行客户端模式jwt
        Claims claims = jwtUtils.parseJWT(jwt);
        if (!claims.containsKey(JWTKeyConstants.PERMISSION_LIST)) {
            return chain.filter(exchange);
        }

        // 判断用户数据是否存在 存在则放行(有状态才检测)
        if (jwtConstants.getIsJwtStateful() && isExists(jwt)) {
            return GatewayResponse.blocked(exchange.getResponse(), "认证过期");
        }

        // token黑名单处理
        if (handelTokenLock(jwt)) {
            // 清除jwt绑定
            redisUtils.del(String.format(TokenCache.SYSTEM_ACCESS_TOKEN_USER, jwt));
            return GatewayResponse.blocked(exchange.getResponse(), "认证过期");
        }

        // token黑名单处理
        if (handelTokenLock(jwt)) {
            // 清除jwt绑定
            redisUtils.del(String.format(TokenCache.SYSTEM_ACCESS_TOKEN_USER, jwt));
            return GatewayResponse.blocked(exchange.getResponse(), "认证过期");
        }

        return chain.filter(exchange);
    }

    /**
     * 路径匹配
     * 基于Spring AntPathMatcher实现路径匹配
     * /ext/** 匹配 /ext/下的所有路径 /ext/** => /ext/a/b/c
     * /ext/* 匹配 /ext/下的一层任意路径 -> /ext/* => /ext/a or /ext/b
     *
     * @param path 路径
     * @return 是否放行
     */
    private boolean skipWhiteListPath(String path) {
        // 白名单为空
        if (CollUtil.isEmpty(gatewayRouteConfig.getWhiteList())) {
            return false;
        }
        // 用Spring提供的antPathMatcher匹配
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        // 匹配到直接放行
        return gatewayRouteConfig.getWhiteList().stream().anyMatch(rule -> antPathMatcher.match(rule, path));
    }

    /**
     * 处理Token黑名单逻辑
     *
     * @param jwt jwt
     * @return 是否
     */
    private boolean handelTokenLock(String jwt) {
        Claims claims = jwtUtils.parseJWT(jwt);
        if (claims == null) {
            return false;
        }
        Object uid = claims.get(JWTKeyConstants.ID);
        // 获取uid
        if (uid == null) {
            return false;
        }
        // 获取签发时间
        Object createTime = claims.get(JWTKeyConstants.CREATE_TIME);
        if (createTime == null) {
            return false;
        }
        // 获取用户Token黑名单签发时间
        String authTokenLock = getAuthTokenLock((Integer) uid);
        // 无黑名单跳过
        if (StrUtil.isBlank(authTokenLock)) {
            return false;
        }
        // compare:createTime < authTokenLock ? <0 : >0
        // 当签发时间大于黑名单时间时放行
        return DateUtil.compare(DateUtil.parse((String) createTime, DatePattern.NORM_DATETIME_PATTERN),
                DateUtil.parse(authTokenLock, DatePattern.NORM_DATETIME_PATTERN)) <= 0;
    }

    /**
     * 判断用户登录信息是否存在 实现有状态Token
     *
     * @param accessToken accessToken
     * @return 是否存在
     */
    public boolean isExists(String accessToken) {
        return redisUtils.hasKey(String.format(TokenCache.SYSTEM_ACCESS_TOKEN_USER, accessToken));
    }

    /**
     * 获取用户token黑名单
     *
     * @param uid 用户id
     * @return 黑名单创建时间
     */
    public String getAuthTokenLock(Integer uid) {
        return redisUtils.get(String.format(TokenCache.SYSTEM_USER_TOKEN_LOCK, uid));
    }
}
