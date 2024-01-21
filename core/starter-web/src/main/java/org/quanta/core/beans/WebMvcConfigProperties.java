package org.quanta.core.beans;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/5
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "quanta.web")
public class WebMvcConfigProperties {
//    // 放行路径 微服务框架路径拦截功能放到了网关
//    private List<String> excludePath = new ArrayList<>();
//    // 拦截路径
//    private List<String> interceptorPath = new ArrayList<>();
    // 资源路径映射策略
    private List<ResourceHandler> resourceHandlers = new ArrayList<>();

    @Data
    public static class ResourceHandler {
        List<String> handlerPaths = new ArrayList<>();
        List<String> resourceLocations = new ArrayList<>();
    }
}
