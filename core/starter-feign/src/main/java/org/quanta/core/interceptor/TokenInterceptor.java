package org.quanta.core.interceptor;

import cn.hutool.core.util.StrUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.quanta.base.constants.AuthConstants;
import org.quanta.core.utils.WebUtils;
import org.springframework.stereotype.Component;

/**
 * Description: Token切面 将当前请求Token传递到feign请求中
 * 可以通过手动设置Authorization覆盖这里的默认配置
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/16
 */
@Component
public class TokenInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (WebUtils.getRequest() == null) {
            return;
        }
        // 判断当前请求是否携带Token
        String header = WebUtils.getRequest().getHeader(AuthConstants.TOKEN_KEY);
        if (StrUtil.isBlank(header)) {
            return;
        }
        requestTemplate.header(AuthConstants.TOKEN_KEY, header);
    }
}
