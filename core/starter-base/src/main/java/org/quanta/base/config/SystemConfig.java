package org.quanta.base.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/5
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "quanta.system")
public class SystemConfig {
    /**
     * 是否开启debug模式
     */
    Boolean debug = false;
    /**
     * 当前环境
     */
    @Value("${spring.profiles.active:}")
    String env;
    /**
     * 服务器地址
     */
    String host;
    /**
     * 应用名称
     */
    @Value("${spring.application.name}")
    String appName;
}
