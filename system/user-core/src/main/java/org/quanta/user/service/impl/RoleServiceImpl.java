package org.quanta.user.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.quanta.base.exception.ServiceException;
import org.quanta.core.constant.cache.RoleCache;
import org.quanta.user.dto.AddRoleDTO;
import org.quanta.user.entity.Role;
import org.quanta.user.entity.RolePermission;
import org.quanta.user.entity.UserRole;
import org.quanta.user.mapper.RoleMapper;
import org.quanta.user.service.PermissionService;
import org.quanta.user.service.RolePermissionService;
import org.quanta.user.service.RoleService;
import org.quanta.user.service.UserRoleService;
import org.quanta.user.vo.PermissionVO;
import org.quanta.user.vo.RoleVO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wzf
 * @description 针对表【role】的数据库操作Service实现
 * @createDate 2023-12-25 18:17:12
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {
    private final PermissionService permissionService;
    private final RolePermissionService rolePermissionService;
    private final UserRoleService userRoleService;

    /**
     * 保存角色
     */
    @Override
    @Transactional
    public void saveRole(AddRoleDTO dto) {
        int parentId = dto.getParentId() == null ? 0 : dto.getParentId();
        if (parentId != 0 && getById(parentId) == null) {
            throw new ServiceException("父级节点不存在");
        }
        save(Role.builder()
                .parentId(dto.getParentId())
                .name(dto.getName())
                .desc(dto.getDesc())
                .build());
    }

    /**
     * 获取角色列表
     */
    @Override
    @Cacheable(value = RoleCache.SYSTEM_USER_ROLE_TREE, key = "'role'")
    public List<RoleVO> getRoleTree() {
        List<Role> roleList = list();
        if (roleList.isEmpty()) return ListUtil.empty();
        Map<Integer, List<Role>> groupByParent = list().stream()
                .collect(Collectors.groupingBy(Role::getParentId));
        // 获取根节点列表
        List<Role> rootList = groupByParent.get(0);
        // 遍历根节点构建子树
        // 递归构建子节点
        return rootList.stream().map(root -> {
            // 构建响应对象
            RoleVO roleVO = RoleVO.builder()
                    .id(root.getId())
                    .parentId(root.getParentId())
                    .name(root.getName())
                    .desc(root.getDesc())
                    .build();
            buildSubTree(groupByParent, roleVO);
            return roleVO;
        }).collect(Collectors.toList());
    }

    private void buildSubTree(Map<Integer, List<Role>> groupByParent, RoleVO cur) {
        if (cur == null) {
            return;
        }
        // 获取以当前节点为父节点的角色列表
        List<Role> subRoleList = groupByParent.get(cur.getId());
        // 如果当前节点没有子节点
        if (subRoleList == null) {
            return;
        }
        // 递归遍历子节点 构建子树
        subRoleList.forEach(role -> {
            // 将子节点添加到当前节点列表中
            if (cur.getSubRoleList() == null) {
                cur.setSubRoleList(new ArrayList<>());
            }
            RoleVO roleVO = RoleVO.builder()
                    .id(role.getId())
                    .parentId(role.getParentId())
                    .name(role.getName())
                    .parentName(cur.getName())
                    .desc(role.getDesc())
                    .build();
            cur.getSubRoleList().add(roleVO);
            buildSubTree(groupByParent, roleVO);
        });
    }

    /**
     * 获取角色权限列表
     */
    @Override
    @CacheEvict(value = RoleCache.SYSTEM_USER_ROLE_PERMISSION, key = "'role-permission:'+#roleId")
    public List<PermissionVO> getRolePermissionById(Integer roleId) {
        // 树状permission结构
        List<PermissionVO> permissionList = permissionService.getPermissionTree();
        // 获取该角色持有权限列表
        List<RolePermission> rolePermissionList = rolePermissionService
                .lambdaQuery()
                .eq(RolePermission::getRoleId, roleId)
                .list();
        if (rolePermissionList.isEmpty()) return permissionList;

        // 持有权限id列表
        List<Integer> permissionIdList = rolePermissionList.stream()
                .map(RolePermission::getPermissionId)
                .distinct()
                .collect(Collectors.toList());
        // 对树进行深度优先搜索遍历标记持有权限的结点
        permissionList.forEach(root -> permissionService.markNode(root, permissionIdList));
        return permissionList;
    }

    /**
     * 删除角色
     */
    @Override
    @Transactional
    public void removeRoleById(Integer id) {
        // 校验角色
        Role role = getById(id);
        if (role == null) {
            throw new ServiceException("角色不存在");
        }
        // 校验是否存在子节点
        Long childCount = lambdaQuery().eq(Role::getParentId, id).count();
        if (childCount != 0) {
            throw new ServiceException("存在子节点");
        }
        // 校验是否存在用户关联
        Long userCount = userRoleService.lambdaQuery().eq(UserRole::getRoleId, id).count();
        if (userCount != 0) {
            throw new ServiceException("该角色下存在用户");
        }
        // 删除角色
        removeById(id);
    }


}




