package org.quanta.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.quanta.auth.service.AuthService;
import org.quanta.auth.utils.TokenUtils;
import org.quanta.base.constants.AuthConstants;
import org.quanta.base.exception.ServiceException;
import org.quanta.core.utils.JWTUtils;
import org.quanta.core.utils.WebUtils;
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
    }

}
