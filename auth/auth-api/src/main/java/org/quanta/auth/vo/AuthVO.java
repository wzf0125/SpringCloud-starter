package org.quanta.auth.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/15
 */
@Data
public class AuthVO {
    @JsonAlias("access_token")
    private String accessToken;
    @JsonAlias("token_type")
    private String tokenType;
    @JsonAlias("refresh_token")
    private String refresh_token;
    @JsonAlias("expires_in")
    private Integer expiresIn;
    @JsonAlias("scope")
    private String scope;
    @JsonAlias("jti")
    private String jti;
}
