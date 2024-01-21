package org.quanta.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/12/18
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "org.quanta")
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
