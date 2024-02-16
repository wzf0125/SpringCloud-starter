package org.quanta.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.quanta.auth.service.AuthService;
import org.quanta.auth.utils.TokenUtils;
import org.quanta.base.constants.AuthConstants;
import org.quanta.base.exception.ServiceException;
import org.quanta.core.utils.JWTUtils;
import org.quanta.core.utils.WebUtils;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/15
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final TokenUtils tokenUtils;
    private final JWTUtils jwtUtils;
    private final TokenStore tokenStore;

    @Override
    public void logout() {
        HttpServletRequest request = WebUtils.getRequest();
        if (request == null) {
            throw new ServiceException("非法请求");
        }
        String token = request.getHeader(AuthConstants.TOKEN_KEY);
        if (token.isEmpty()) {
            throw new ServiceException("非法请求");
        }
        String accessToken = jwtUtils.getJWT(token);
        tokenUtils.destroyAccessToken(accessToken);

        // 清除OAuth2内部关联关系
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
        OAuth2RefreshToken refreshToken = null;
        if (accessToken != null && StrUtil.isNotBlank(oAuth2AccessToken.getValue())) {
            refreshToken = oAuth2AccessToken.getRefreshToken();
            tokenStore.removeAccessToken(oAuth2AccessToken);
        }
        if (refreshToken != null && StrUtil.isNotBlank(refreshToken.getValue())) {
            tokenStore.removeRefreshToken(refreshToken);
        }
    }

}
