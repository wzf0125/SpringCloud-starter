package org.quanta.auth.utils;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.quanta.auth.entity.SystemUserDetails;
import org.quanta.core.constant.cache.TokenCache;
import org.quanta.core.constants.JWTConstants;
import org.quanta.core.utils.RedisUtils;
import org.springframework.stereotype.Component;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/14
 */
@Component
@RequiredArgsConstructor
public class TokenUtils {
    private final RedisUtils redisUtils;
    private final JWTConstants jwtConstants;

    /**
     * 保存accessToken并进行双向绑定
     * uid => token
     * token => uid
     *
     * @param accessToken       jwt accessToken
     * @param systemUserDetails 用户数据
     */
    public void saveAccessToken(String accessToken, SystemUserDetails systemUserDetails) {
        bindToken(accessToken, systemUserDetails, TokenCache.SYSTEM_USER_ACCESS_TOKEN,
                TokenCache.SYSTEM_ACCESS_TOKEN_USER, jwtConstants.getAccessTokenExpireTime());
    }

    /**
     * 保存refreshToken并进行双向绑定
     * uid => token
     * token => uid
     *
     * @param refreshToken      jwt refreshToken
     * @param systemUserDetails 用户数据
     */
    public void saveRefreshToken(String refreshToken, SystemUserDetails systemUserDetails) {
        bindToken(refreshToken, systemUserDetails, TokenCache.SYSTEM_USER_REFRESH_TOKEN,
                TokenCache.SYSTEM_REFRESH_TOKEN_USER, jwtConstants.getRefreshTokenExpireTime());
    }

    /**
     * 绑定token和用户的关系
     *
     * @param token             token
     * @param systemUserDetails 用户数据
     * @param uid2tokenKey      uid => token key
     * @param token2uidKey      token => uid key
     * @param seconds           过期时间
     */
    private void bindToken(String token, SystemUserDetails systemUserDetails,
                           String uid2tokenKey, String token2uidKey, Long seconds) {
        // 保存用户id => Token映射
        redisUtils.set(String.format(uid2tokenKey, systemUserDetails.getUser().getId()), token, seconds);
        // 保存Token => 用户信息映射
        redisUtils.set(String.format(token2uidKey, token), systemUserDetails, seconds);
    }

    /**
     * @param accessToken 通过token删除用户登陆数据
     */
    public void destroyAccessToken(String accessToken) {
        destroyToken(TokenCache.SYSTEM_USER_ACCESS_TOKEN, TokenCache.SYSTEM_ACCESS_TOKEN_USER, accessToken);
    }

    /**
     * @param refreshToken 通过uid删除用户token
     */
    public void destroyRefreshToken(String refreshToken) {
        destroyToken(TokenCache.SYSTEM_USER_REFRESH_TOKEN, TokenCache.SYSTEM_REFRESH_TOKEN_USER, refreshToken);
    }


    /**
     * @param uid 通过uid删除用户token
     */
    public void destroyAccessToken(Integer uid) {
        destroyToken(TokenCache.SYSTEM_USER_ACCESS_TOKEN, TokenCache.SYSTEM_ACCESS_TOKEN_USER, uid);
    }

    /**
     * @param uid 通过uid删除用户token
     */
    public void destroyRefreshToken(Integer uid) {
        destroyToken(TokenCache.SYSTEM_USER_REFRESH_TOKEN, TokenCache.SYSTEM_REFRESH_TOKEN_USER, uid);
    }

    /**
     * 通过id销毁用户登录数据
     */
    public void destroyToken(String uid2TokenKey, String token2UidKey, Integer uid) {
        String tokenKey = StrUtil.format(uid2TokenKey, uid);
        String accessToken = redisUtils.get(tokenKey);
        if (accessToken == null) {
            return;
        }
        // 删除token => 用户 和用户 => token绑定
        redisUtils.del(StrUtil.format(uid2TokenKey, uid));
        redisUtils.del(StrUtil.format(token2UidKey, accessToken));
    }

    /**
     * 通过token销毁用户登陆数据
     */
    public void destroyToken(String uid2TokenKey, String token2UidKey, String token) {
        // 获取用户信息
        String tokenKey = StrUtil.format(token2UidKey, token);
        SystemUserDetails userInfo = redisUtils.get(tokenKey);
        // 先删除token绑定用户
        redisUtils.del(StrUtil.format(token2UidKey, token));
        // 判断用户信息是否存在 不存在或允许多用户登录则跳过删除id 绑定token数据
        if (userInfo == null || jwtConstants.getAllowMultipleLogin()) {
            return;
        }
        // 删除用户数据
        redisUtils.del(StrUtil.format(uid2TokenKey, userInfo.getUser().getId()));
    }

    /**
     * @param accessToken jwtToken
     * @return 用户数据
     */
    public SystemUserDetails getUserByToken(String accessToken) {
        return redisUtils.get(String.format(TokenCache.SYSTEM_ACCESS_TOKEN_USER, accessToken));
    }

    /**
     * @param uid 用户id
     * @return token
     */
    public String getTokenByUid(Integer uid) {
        return redisUtils.get(String.format(TokenCache.SYSTEM_USER_ACCESS_TOKEN, uid));
    }


}
