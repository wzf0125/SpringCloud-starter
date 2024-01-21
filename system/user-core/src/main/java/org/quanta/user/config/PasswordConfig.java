package org.quanta.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/18
 */
@Configuration
public class PasswordConfig {
    /**
     * 注解密码加密方式
     * 记得要和auth服务的一致
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
