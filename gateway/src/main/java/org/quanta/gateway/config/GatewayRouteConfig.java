package org.quanta.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * Description: 路径放行规则
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/12/19
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties("quanta.gateway.route")
public class GatewayRouteConfig {
    /**
     * 白名单
     */
    Set<String> whiteList;
    /**
     * 黑名单
     */
    Set<String> blackList;
}
