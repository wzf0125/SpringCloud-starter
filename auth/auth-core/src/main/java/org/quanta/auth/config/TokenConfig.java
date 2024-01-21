package org.quanta.auth.config;

import lombok.RequiredArgsConstructor;
import org.quanta.core.constants.JWTConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/12
 */
@Configuration
@RequiredArgsConstructor
public class TokenConfig {
    private final JWTConstants jwtConstants;
    private final RedisConnectionFactory redisConnectionFactory;

    /**
     * 注册JWT生成器
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(jwtConstants.getDefaultKey());
        return converter;
    }

    /**
     * 注册Token存储方式
     * 采用redis存储
     */
    @Bean
    public TokenStore tokenStore() {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        // 设置前缀
        tokenStore.setPrefix(jwtConstants.getTokenStorePrefix());
        // 设置key生成规则
        tokenStore.setAuthenticationKeyGenerator(
                new DefaultAuthenticationKeyGenerator() {
                    @Override
                    public String extractKey(OAuth2Authentication authentication) {
                        return super.extractKey(authentication);
                    }
                }
        );
        return tokenStore;
    }

}
