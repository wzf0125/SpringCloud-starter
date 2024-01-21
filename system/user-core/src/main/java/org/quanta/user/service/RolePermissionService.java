package org.quanta.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.quanta.user.dto.EditRolePermissionDTO;
import org.quanta.user.entity.Permission;
import org.quanta.user.entity.RolePermission;

import java.util.List;

/**
 * @author wzf
 * @description 针对表【role_permission】的数据库操作Service
 * @createDate 2023-12-25 18:17:16
 */
public interface RolePermissionService extends IService<RolePermission> {

    /**
     * 编辑角色权限
     */
    void editRolePermission(EditRolePermissionDTO dto);

    /**
     * 通过角色id获取用户权限
     */
    List<Permission> getPermissionByRoleIdList(List<Integer> roleIdList);
}
