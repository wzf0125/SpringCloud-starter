package org.quanta.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/12/19
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties("quanta.gateway.ip.filter")
public class IPFilterConfig {
    /**
     * 黑名单
     */
    IPFilter BLACK_LIST;
    /**
     * 白名单
     */
    IPFilter WHITE_LIST;

    /**
     * ip过滤配置
     */
    @Data
    public static class IPFilter {
        // 是否启用
        Boolean enable;
        // ip列表
        List<String> ipList;
    }
}
