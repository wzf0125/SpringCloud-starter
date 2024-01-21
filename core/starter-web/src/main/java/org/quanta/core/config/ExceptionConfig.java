package org.quanta.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Description: 异常配置
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/12
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "quanta.exception")
public class ExceptionConfig {
    /**
     * 异常捕获白名单
     * 比如Security OAuth2的InsufficientAuthenticationException 由此异常引导跳转到登录页
     */
    private List<String> white;

    // 返回白名单class配置
    public List<Class<?>> getWhiteList() {
        if (white == null || white.isEmpty()) return new ArrayList<>();
        return white.stream().map(name -> {
            try {
                return Class.forName(name);
            } catch (ClassNotFoundException e) {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

}
