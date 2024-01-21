package org.quanta.auth.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/10
 */
@Configuration
public class JWTConfig {

    // jwt配置
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        // 生成RSA密钥对
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 构建RSA密钥对象
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        // 构建JWK集合
        JWKSet jwkSet = new JWKSet(rsaKey);
        // 返回不可变的JWK集合
        return new ImmutableJWKSet<>(jwkSet);
    }

    // jwt秘钥生成器
    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            // 使用RSA算法生成密钥对
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    // jwt解码器
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        // 使用OAuth2AuthorizationServerConfiguration提供的jwtDecoder方法创建JwtDecoder
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

}
