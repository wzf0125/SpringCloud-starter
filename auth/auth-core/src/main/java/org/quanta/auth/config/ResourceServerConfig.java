package org.quanta.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/12
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 允许所有访问 "/oauth/**" 的请求，通常用于 OAuth2 认证服务的端点
                .antMatchers("/oauth/**").permitAll()
                // 任何其他请求都需要身份验证
                .anyRequest().authenticated();
    }
}
