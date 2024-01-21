package org.quanta.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.quanta.user.entity.Permission;
import org.quanta.user.entity.RolePermission;
import org.quanta.user.entity.UserRole;
import org.quanta.user.service.PermissionService;
import org.quanta.user.service.RolePermissionService;
import org.quanta.user.service.UserPermissionService;
import org.quanta.user.service.UserRoleService;
import org.quanta.user.vo.PermissionVO;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/4
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class UserPermissionServiceImpl implements UserPermissionService {
    private final UserRoleService userRoleService;
    private final RolePermissionService rolePermissionService;
    private final PermissionService permissionService;

    /**
     * 获取用户权限(树状)
     * */
    @Override
    public List<PermissionVO> getUserPermission(Integer id) {
        // 获取用户角色
        List<Integer> roleIdList = userRoleService.lambdaQuery()
                .eq(UserRole::getUid, id)
                .list()
                .stream().map(UserRole::getRoleId)
                .collect(Collectors.toList());

        // 通过角色查询权限id列表
        List<Integer> permissionIdList = rolePermissionService.lambdaQuery()
                .in(RolePermission::getRoleId, roleIdList)
                .list()
                .stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());

        // 获取树状权限结构
        List<PermissionVO> permissionList = permissionService.getPermissionTree();

        // 遍历标记有效权限
        permissionList.forEach(root -> permissionService.markNode(root, permissionIdList));
        return permissionList;
    }

    @Override
    public List<Permission> getUserPermissionList(Integer id) {
        // 获取用户角色
        List<Integer> roleIdList = userRoleService.lambdaQuery()
                .eq(UserRole::getUid, id)
                .list()
                .stream().map(UserRole::getRoleId)
                .collect(Collectors.toList());
        // 返回所有角色对应权限
        return rolePermissionService.getPermissionByRoleIdList(roleIdList);
    }
}
