package org.quanta.core.constants;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/15
 */
public interface JWTKeyConstants {
    // 拓展字段
    // 用户id
    String ID = "id";
    // 角色列表
    String ROLE_LIST = "role_list";
    // 权限列表
    String PERMISSION_LIST = "permission_list";
    // 签发时间
    String CREATE_TIME = "create_time";

    // 原始字段
    String USERNAME = "user_name";
    String SCOPE = "scope";
    String JTI = "jti";
    String EXP = "exp";
    String AUTHORITIES = "authorities";
}
