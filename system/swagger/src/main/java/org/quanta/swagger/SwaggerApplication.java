package org.quanta.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/12/28
 */
@EnableDiscoveryClient // 开启服务发现客户端
@SpringBootApplication(scanBasePackages = "org.quanta")
public class SwaggerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SwaggerApplication.class, args);
    }
}
