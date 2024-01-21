package org.quanta.user.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.quanta.base.exception.ServiceException;
import org.quanta.core.constant.cache.PermissionCache;
import org.quanta.user.dto.AddPermissionDTO;
import org.quanta.user.entity.Permission;
import org.quanta.user.entity.RolePermission;
import org.quanta.user.mapper.PermissionMapper;
import org.quanta.user.service.PermissionService;
import org.quanta.user.service.RolePermissionService;
import org.quanta.user.vo.PermissionVO;
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
 * @description 针对表【permission】的数据库操作Service实现
 * @createDate 2023-12-25 18:16:18
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
        implements PermissionService {
    private final RolePermissionService rolePermissionService;

    /**
     * 获取树状权限结构
     * 加上缓存
     */
    @Override
    @Cacheable(value = PermissionCache.SYSTEM_USER_PERMISSION_TREE, key = "'user:system:permission'")
    public List<PermissionVO> getPermissionTree() {
        List<Permission> permissionList = list();
        if (permissionList.isEmpty()) return ListUtil.empty();
        // 根据父节点分组
        Map<Integer, List<Permission>> groupByParent = permissionList.stream()
                .collect(Collectors.groupingBy(Permission::getParentId));
        // 获取根节点列表
        List<Permission> rootList = groupByParent.get(0); // 根节点
        // 递归构建子节点
        return rootList.stream().map(root -> {
            // 构建响应对象
            PermissionVO permission = PermissionVO.builder()
                    .id(root.getId())
                    .parentId(root.getParentId())
                    .path(root.getPath())
                    .name(root.getName())
                    .desc(root.getDesc())
                    .build();
            buildSubTree(groupByParent, permission);
            return permission;
        }).collect(Collectors.toList());
    }

    /**
     * 构建子树
     */
    private void buildSubTree(Map<Integer, List<Permission>> groupByParentMap, PermissionVO cur) {
        if (cur == null) {
            return;
        }
        // 以当前节点为子节点的权限列表
        List<Permission> subPermissionList = groupByParentMap.get(cur.getId());
        // 如果当前节点没有子节点
        if (subPermissionList == null) {
            cur.setSubPermission(null);
            return;
        }
        // 递归遍历子节点
        subPermissionList.forEach(permission -> {
            // 将子节点添加到当前节点列表中
            if (cur.getSubPermission() == null) {
                cur.setSubPermission(new ArrayList<>());
            }
            PermissionVO subRoot = PermissionVO.builder()
                    .id(permission.getId())
                    .parentId(permission.getParentId())
                    .path(permission.getPath())
                    .name(permission.getName())
                    .parentName(cur.getName())
                    .desc(permission.getDesc())
                    .build();
            // 将子节点添加到当前节点中
            cur.getSubPermission().add(subRoot);
            buildSubTree(groupByParentMap, subRoot);
        });
    }

    /**
     * 保存权限信息
     */
    @Override
    @Transactional
    public void savePermission(AddPermissionDTO dto) {
        int parentId = dto.getPatentId() == null ? 0 : dto.getPatentId();
        if (parentId != 0 && getById(parentId) == null) {
            throw new ServiceException("父级权限不存在");
        }
        save(Permission.builder()
                .name(dto.getName())
                .path(dto.getPath())
                .desc(dto.getDesc())
                .parentId(dto.getPatentId())
                .build());
    }


    /**
     * @param root 当前节点
     * @param mark 需要标记的列表
     */
    @Override
    public void markNode(PermissionVO root, List<Integer> mark) {
        if (root == null) {
            return;
        }
        if (mark.contains(root.getId())) {
            root.setHasPermission(true);
        }
        if (root.getSubPermission() == null) {
            return;
        }
        for (PermissionVO subRoot : root.getSubPermission()) {
            markNode(subRoot, mark);
        }
    }

    @Override
    @Transactional
    public void removePermissionById(Integer id) {
        // 检查是否具有子节点
        Long childNodeCount = lambdaQuery().eq(Permission::getParentId, id).count();
        if (childNodeCount != 0) {
            throw new ServiceException("请先删除子节点");
        }
        // 删除权限
        removeById(id);
        // 删除权限角色关联
        rolePermissionService.lambdaUpdate().eq(RolePermission::getPermissionId, id).remove();
    }
}




