package org.quanta.core.constant.cache;

import org.quanta.core.constant.CacheConstant;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/18
 */
public interface UserCache extends CacheConstant {
    // 用户缓存
    String SYSTEM_USER_USER_BY_ACCOUNT = SYSTEM_USER + ":user:account";
    String SYSTEM_USER_USER_BY_EMAIL = SYSTEM_USER + ":user:email";
    String SYSTEM_USER_USER_BY_ID = SYSTEM_USER + ":user:id";
    String SYSTEM_USER_USER_PAGE = SYSTEM_USER + ":user:page";
    String SYSTEM_USER_USER_ROLE_TREE = SYSTEM_USER + ":user:role:tree";
    String SYSTEM_USER_USER_ROLE_LIST = SYSTEM_USER + ":user:role:list";
    String SYSTEM_USER_USER_PERMISSION_TREE = SYSTEM_USER + ":user:permission:tree";
    String SYSTEM_USER_USER_PERMISSION_LIST = SYSTEM_USER + ":user:permission:list";
}
