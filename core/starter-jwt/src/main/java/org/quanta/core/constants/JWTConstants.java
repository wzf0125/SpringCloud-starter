package org.quanta.core.constants;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Description: jwt配置
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/6
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "quanta.jwt")
public class JWTConstants {
    // 默认秘钥
    private String defaultKey = "IdKXRzBFQLprpU5kkM7YdrL9eCEZgLvz";
    // 是否开启有状态jwt
    private Boolean isJwtStateful = false;
    // 是否运行多用户登录
    private Boolean allowMultipleLogin = true;
    // accessToken有效时间
    private Long accessTokenExpireTime = 60L * 60 * 24;
    // refreshToken有效时间
    private Long refreshTokenExpireTime = 60L * 60 * 24 * 3;
    // Token存储浅醉
    private String tokenStorePrefix = "jwt:token:";
}
