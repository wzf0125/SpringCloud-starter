package org.quanta.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.quanta.base.exception.ServiceException;
import org.quanta.user.dto.EditRolePermissionDTO;
import org.quanta.user.entity.Permission;
import org.quanta.user.entity.Role;
import org.quanta.user.entity.RolePermission;
import org.quanta.user.mapper.RolePermissionMapper;
import org.quanta.user.service.PermissionService;
import org.quanta.user.service.RolePermissionService;
import org.quanta.user.service.RoleService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wzf
 * @description 针对表【role_permission】的数据库操作Service实现
 * @createDate 2023-12-25 18:17:16
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
        implements RolePermissionService {
    private final RoleService roleService;
    private final PermissionService permissionService;

    /**
     * 编辑角色权限
     */
    @Override
    @Transactional
    public void editRolePermission(EditRolePermissionDTO dto) {
        // 先判断角色是否存在
        Role role = roleService.getById(dto.getRoleId());
        if (role == null) throw new ServiceException("角色不存在");
        // 校验权限列表
        List<Permission> permissionList = permissionService.lambdaQuery()
                .in(Permission::getId, dto.getPermissionIdList())
                .list();
        if (permissionList.size() != dto.getPermissionIdList().size()) throw new ServiceException("存在非法权限");
        // 查询现有权限
        List<RolePermission> currentPermission = lambdaQuery()
                .eq(RolePermission::getRoleId, dto.getRoleId())
                .list();
        // 当前权限id列表
        List<Integer> currentPermissionIdList = currentPermission.stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());
        // 当前拥有权限 - 编辑后权限 = 需要删除的权限
        List<Integer> deleteIdList = CollUtil.subtractToList(currentPermissionIdList, dto.getPermissionIdList());
        // 非空则执行删除操作
        if (!deleteIdList.isEmpty()) {
            lambdaUpdate().eq(RolePermission::getRoleId, dto.getRoleId())
                    .in(RolePermission::getPermissionId, deleteIdList)
                    .remove();
        }

        // 编辑后权限 - 当前权限 = 需要新增的权限
        List<Integer> insertIdList = CollUtil.subtractToList(dto.getPermissionIdList(), currentPermissionIdList);
        // 非空则执行插入操作
        if (!insertIdList.isEmpty()) {
            // 构造新增角色权限列表
            List<RolePermission> rolePermissionSaveList = insertIdList.stream()
                    .map(id -> RolePermission.builder()
                            .roleId(dto.getRoleId())
                            .permissionId(id)
                            .build())
                    .collect(Collectors.toList());
            // 保存角色权限列表
            saveBatch(rolePermissionSaveList);
        }
    }

    @Override
    public List<Permission> getPermissionByRoleIdList(List<Integer> roleIdList) {
        return getBaseMapper().getPermissionByRoleIdList(roleIdList);
    }
}




