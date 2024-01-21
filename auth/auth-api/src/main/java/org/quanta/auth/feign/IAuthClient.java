package org.quanta.auth.feign;

import lombok.extern.slf4j.Slf4j;
import org.quanta.auth.vo.AuthVO;
import org.quanta.common.contants.APP_NAME;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Description: 认证客户端调用接口
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/15
 */
@FeignClient(value = APP_NAME.APP_AUTH,
        contextId = "oauth",
        fallback = IAuthClient.IAuthClientFallback.class,
        path = "/oauth")
public interface IAuthClient {
    /**
     * OAuth2提供的认证接口
     * 密码模式
     * 请求方式:
     * 请求头Authorization=Basic Base64(clientId:clientSecret) 例: Basic cXVhbnRhOnF1YW50YTIwMjM=
     * 表单参数:grant_type=password,username=xxx,password=xxx
     */

    @PostMapping(value = "/token", params = {"grant_type=password"})
    AuthVO passwordAuth(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam(value = "scope", required = false) String scope,
            @RequestHeader("Authorization") String head);

    /**
     * OAuth2提供的认证接口
     * 授权码模式
     * 请求方式:
     * 请求头Authorization=Basic Base64(clientId:clientSecret) 例: Basic cXVhbnRhOnF1YW50YTIwMjM=
     * 表单参数:grant_type=authorization_code,code=xxx,redirect_uri=xxx(注意uri要在配置的运行域名中(http/https也要区分))
     */
    @PostMapping(value = "/token", params = {"grant_type=authorization_code"})
    AuthVO codeAuth(
            @RequestParam("code") String code,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestHeader("Authorization") String head);

    /**
     * OAuth2提供的认证接口
     * 客户端模式
     * 请求方式:
     * 请求头Authorization=Basic Base64(clientId:clientSecret) 例: Basic cXVhbnRhOnF1YW50YTIwMjM=
     * 表单参数:grant_type=client_credentials
     */
    @PostMapping(value = "/token", params = {"grant_type=client_credentials"})
    AuthVO clientAuth(@RequestParam(value = "scope", required = false) String scope,
                      @RequestHeader("Authorization") String head);

    @Slf4j
    @Component
    class IAuthClientFallback implements IAuthClient {

        @Override
        public AuthVO passwordAuth(String grantType, String username, String password, String head) {
            log.error("IAuthClient passwordAuth");
            return null;
        }

        @Override
        public AuthVO codeAuth(String code, String redirectUri, String head) {
            log.error("IAuthClient codeAuth");
            return null;
        }

        @Override
        public AuthVO clientAuth(String scope, String head) {
            log.error("IAuthClient clientAuth");
            return null;
        }

    }
}
