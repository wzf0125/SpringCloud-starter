package org.quanta.auth.enhancer;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.quanta.auth.entity.SystemUserDetails;
import org.quanta.auth.utils.TokenUtils;
import org.quanta.core.constants.JWTConstants;
import org.quanta.core.constants.JWTKeyConstants;
import org.quanta.user.entity.Permission;
import org.quanta.user.entity.Role;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description: Token增强
 * 提供以下增强:
 * 1.jwt内容增强
 * 2.Token有状态增强
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/13
 */
@Component
@RequiredArgsConstructor
public class JWTEnhancer implements TokenEnhancer {
    private final JWTConstants jwtConstants;
    private final JwtAccessTokenConverter jwtAccessTokenConverter;
    private final TokenUtils tokenUtils;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (authentication.getUserAuthentication() == null || authentication.getUserAuthentication().getPrincipal() == null) {
            return accessToken;
        }
        // 获取用户信息
        SystemUserDetails systemUserDetails = (SystemUserDetails) authentication.getUserAuthentication().getPrincipal();
        // 响应增强
        JWTInfoEnhancer(accessToken, authentication, systemUserDetails);
        // 有状态增强
        statefulEnhancer(accessToken, authentication, systemUserDetails);
        return accessToken;
    }

    /**
     * jwt内容增强
     * 增强内容会一同在获取Token时响应(/oauth/token)
     *
     * @param accessToken       accessToken
     * @param authentication    认证信息
     * @param systemUserDetails 用户数据
     */
    private void JWTInfoEnhancer(OAuth2AccessToken accessToken, OAuth2Authentication authentication, SystemUserDetails systemUserDetails) {
        // 认证数据
        Map<String, Object> details = (Map<String, Object>) authentication.getUserAuthentication().getDetails();
        // 增强数据
        Map<String, Object> extInfo = MapUtil.<String, Object>builder()
                // 用户id
                .put(JWTKeyConstants.ID, systemUserDetails.getUser().getId())
                // 添加角色信息
                .put(JWTKeyConstants.ROLE_LIST, buildStr(systemUserDetails.getUser().getUserRoleList(), Role::getName))
                // 添加权限信息
                .put(JWTKeyConstants.PERMISSION_LIST, buildStr(systemUserDetails.getUser().getUserPermissionList(), Permission::getPath))
                // 签发时间 yyyy-MM-dd HH:mm:ss
                .put(JWTKeyConstants.CREATE_TIME, DateUtil.now())
                .build();
        // 保存至Token中
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(extInfo);
    }

    /**
     * 有状态增强
     *
     * @param accessToken       accessToken
     * @param authentication    认证信息
     * @param systemUserDetails 用户数据
     */
    private void statefulEnhancer(OAuth2AccessToken accessToken, OAuth2Authentication authentication, SystemUserDetails systemUserDetails) {
        // 不开启有状态则跳过
        if (!jwtConstants.getIsJwtStateful()) {
            return;
        }
        // 是否允许多用户登录
        if (!jwtConstants.getAllowMultipleLogin()) {// 不允许则清除上次绑定的数据
            // 删除用户绑定accessTokenToken关系
            tokenUtils.destroyAccessToken(systemUserDetails.getUser().getId());
            // 删除用户绑定refreshToken关系
            tokenUtils.destroyRefreshToken(systemUserDetails.getUser().getId());
        }
        // 获取accessToken值
        OAuth2AccessToken oAuth2AccessToken = jwtAccessTokenConverter.enhance(accessToken, authentication);
        String accessTokenValue = oAuth2AccessToken.getValue();
        // 获取refreshToken值
        OAuth2RefreshToken refreshToken = oAuth2AccessToken.getRefreshToken();
        // refreshToken只在提供refresh_token模式授权时才不会空
        if (refreshToken != null) {
            String refreshTokenValue = refreshToken.getValue();
            // 保存accessToken
            tokenUtils.saveAccessToken(accessTokenValue, systemUserDetails);
            // 保存refreshToken
            tokenUtils.saveRefreshToken(refreshTokenValue, systemUserDetails);
        }
    }

    /**
     * 提取数据
     */
    public <T, R> String buildStr(List<T> list, Function<T, R> func) {
        return StrUtil.join(",", list.stream().map(func).collect(Collectors.toList()));
    }
}
