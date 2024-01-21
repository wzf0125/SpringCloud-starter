package org.quanta.core.constant.cache;

import org.quanta.core.constant.CacheConstant;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/18
 */
public interface PermissionCache extends CacheConstant {
    // 权限
    String SYSTEM_USER_PERMISSION_TREE = SYSTEM_USER + ":permission:tree";
    String SYSTEM_USER_PERMISSION_LIST = SYSTEM_USER + ":permission:list";
}
