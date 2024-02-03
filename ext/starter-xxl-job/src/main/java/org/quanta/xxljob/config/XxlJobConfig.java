package org.quanta.xxljob.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quanta.xxljob.beans.XxlJobProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description: 配置xxl job
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/26
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class XxlJobConfig {
    private final XxlJobProperties xxlJobProperties;

    /**
     * 配置xxl job相关配置
     */
    @Bean
    public XxlJobSpringExecutor xxlJobSpringExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor executor = new XxlJobSpringExecutor();
        executor.setAdminAddresses(xxlJobProperties.getAdminAddress());
        executor.setAppname(xxlJobProperties.getAppName());
        executor.setAddress(xxlJobProperties.getAddress());
        executor.setIp(xxlJobProperties.getIp());
        executor.setPort(xxlJobProperties.getPort());
        executor.setLogPath(xxlJobProperties.getLogPath());
        executor.setAccessToken(xxlJobProperties.getAccessToken());
        executor.setLogRetentionDays(xxlJobProperties.getLogRetentionDays());
        return executor;
    }
}
