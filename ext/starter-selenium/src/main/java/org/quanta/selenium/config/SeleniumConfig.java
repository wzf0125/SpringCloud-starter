package org.quanta.selenium.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Description: selenium配置
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/2/2
 */
@Data
@Configuration
@ConfigurationProperties(value = "quanta.selenium")
public class SeleniumConfig {
    /**
     * 远程selenium配置
     */
    private RemoteSeleniumConfig remote;
    /**
     * 本地selenium配置
     */
    private LocalSeleniumConfig local;
    /**
     * 等待工具等待时间
     */
    private Integer waitTIme = 10;
    /**
     * ChromeOptions配置
     */
    private List<String> optionsParam;

    @Data
    public static class RemoteSeleniumConfig {
        String url;
    }

    @Data
    public static class LocalSeleniumConfig {
        String driverPath;
    }
}
