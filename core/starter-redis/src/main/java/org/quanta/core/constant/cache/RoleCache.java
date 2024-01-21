package org.quanta.core.constant.cache;

import org.quanta.core.constant.CacheConstant;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/18
 */
public interface RoleCache extends CacheConstant {
    // 角色列表
    String SYSTEM_USER_ROLE_LIST = SYSTEM_USER + ":role:list";
    String SYSTEM_USER_ROLE_TREE = SYSTEM_USER + ":role:tree";
    // 角色权限关联
    String SYSTEM_USER_ROLE_PERMISSION = SYSTEM_USER + ":role-permission";
}
