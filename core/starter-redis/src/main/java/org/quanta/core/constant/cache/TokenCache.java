package org.quanta.core.constant.cache;

import org.quanta.core.constant.CacheConstant;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/18
 */
public interface TokenCache extends CacheConstant {
    // TOKEN存储
    // token -> 用户信息
    String SYSTEM_USER_ACCESS_TOKEN = SYSTEM_USER + ":access-token:%s";
    // uid -> token
    String SYSTEM_ACCESS_TOKEN_USER = SYSTEM + ":token:user:%s";

    // TOKEN存储
    // token -> 用户信息
    String SYSTEM_USER_REFRESH_TOKEN = SYSTEM_USER + ":refresh-token:%s";
    // uid -> token
    String SYSTEM_REFRESH_TOKEN_USER = SYSTEM + ":refresh-token:user:%s";

    // 用户Token清除时间
    String SYSTEM_USER_TOKEN_LOCK = SYSTEM_USER + ":token:lock:%s";
}
