package org.quanta.base.constants;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/5
 */
public interface AuthConstants {
    // Token在请求header中的key
    String TOKEN_KEY = "Authorization";
    // Token在请求header中的value前缀
    String TOKEN_PREFIX = "Bearer ";
    // 认证时(访问/oauth/token)传递的Authorization=Basic Base64(client_id:client_secret)前缀
    String AUTH_PREFIX = "Basic ";
    // 跳过JWT认证拦截(用于类上有拦截注解 但是方法想要放行的情况)
    String SKIP_AUTH = "skip_auth";
}
