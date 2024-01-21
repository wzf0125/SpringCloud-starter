package org.quanta.core.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quanta.core.beans.WebMvcConfigProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Description: web mvc配置 处理跨域，路径放行，拦截器注册
 * Author: wzf
 * Date: 2023/10/3
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final WebMvcConfigProperties webMvcConfigProperties;

    /**
     * 解决跨域问题
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

    /**
     * 添加资源路径映射策略
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        for (WebMvcConfigProperties.ResourceHandler resourceHandler : webMvcConfigProperties.getResourceHandlers()) {
            log.info(resourceHandler.getHandlerPaths() + " => "+resourceHandler.getResourceLocations());
            registry.addResourceHandler(resourceHandler.getHandlerPaths().toArray(new String[0]))
                    .addResourceLocations(resourceHandler.getResourceLocations().toArray(new String[0]));
        }
    }
}
