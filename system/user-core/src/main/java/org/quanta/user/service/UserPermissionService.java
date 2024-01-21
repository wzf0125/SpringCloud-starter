package org.quanta.user.service;

import org.quanta.user.entity.Permission;
import org.quanta.user.vo.PermissionVO;

import java.util.List;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/4
 */
public interface UserPermissionService {

    /**
     * 获取用户权限(树状)
     */
    List<PermissionVO> getUserPermission(Integer id);

    /**
     * 获取用户权限列表
     */
    List<Permission> getUserPermissionList(Integer id);
}
