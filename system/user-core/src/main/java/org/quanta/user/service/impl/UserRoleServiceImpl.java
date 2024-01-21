package org.quanta.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.quanta.base.exception.ServiceException;
import org.quanta.user.dto.EditUserRoleDTO;
import org.quanta.user.entity.Role;
import org.quanta.user.entity.User;
import org.quanta.user.entity.UserRole;
import org.quanta.user.mapper.UserRoleMapper;
import org.quanta.user.service.RoleService;
import org.quanta.user.service.UserRoleService;
import org.quanta.user.service.UserService;
import org.quanta.user.vo.RoleVO;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wzf
 * @description 针对表【user_role】的数据库操作Service实现
 * @createDate 2023-12-25 18:17:25
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
        implements UserRoleService {
    private final RoleService roleService;
    private final UserService userService;

    /**
     * 获取用户拥有的角色
     * 可考虑是否加缓存(用户量大可能导致缓存数据过多)
     */
    @Override
    public List<RoleVO> getUserRoleByUid(Integer id) {
        List<RoleVO> roleList = roleService.getRoleTree();
        List<UserRole> userRoleList = lambdaQuery().eq(UserRole::getUid, id).list();
        if (userRoleList.isEmpty()) return roleList;
        // 取出拥有的角色id
        List<Integer> hasRoleIdList = userRoleList.stream()
                .map(UserRole::getRoleId)
                .distinct()
                .collect(Collectors.toList());
        // 遍历编辑标记用户拥有的角色
        roleList.forEach(userRole -> {
            // 深度优先搜索遍历子树
            dfs(userRole, hasRoleIdList);
        });
        return roleList;
    }

    @Override
    @Transactional
    public void editUserRole(EditUserRoleDTO dto) {
        // 先判断用户是否存在
        User user = userService.getById(dto.getUserId());
        if (user == null) throw new ServiceException("用户不存在");
        // 校验角色列表
        List<Role> roleList = roleService.lambdaQuery()
                .in(Role::getId, dto.getRoleIdList())
                .list();

        if (roleList.size() != dto.getRoleIdList().size()) throw new ServiceException("存在非法角色");
        // 查询用户已有角色
        List<UserRole> currentUserRoleList = lambdaQuery()
                .eq(UserRole::getUid, dto.getUserId())
                .list();
        // 用户已有角色id
        List<Integer> currentUserRoleIdList = currentUserRoleList.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());

        // 用户已有角色id - 修改角色id = 需要删除的角色
        List<Integer> deleteList = CollUtil.subtractToList(currentUserRoleIdList, dto.getRoleIdList());
        if (!deleteList.isEmpty()) {
            lambdaUpdate()
                    .eq(UserRole::getUid, dto.getUserId())
                    .in(UserRole::getId, deleteList)
                    .remove();
        }

        // 修改角色id - 用户已有角色id = 需要新增的角色
        List<Integer> insertList = CollUtil.subtractToList(dto.getRoleIdList(), currentUserRoleIdList);
        if (!insertList.isEmpty()) {
            List<UserRole> saveList = insertList.stream()
                    .map(id -> UserRole.builder()
                            .uid(dto.getUserId())
                            .roleId(id)
                            .build())
                    .collect(Collectors.toList());
            saveBatch(saveList);
        }
    }

    /**
     * 通过用户id查询用户角色列表
     */
    @Override
    public List<Role> getUserRoleListByUid(Integer id) {
        return getBaseMapper().getUserRoleListByUid(id);
    }

    /**
     * @param root      当前节点
     * @param effective 需要标记的列表
     */
    public void dfs(RoleVO root, List<Integer> effective) {
        if (root == null) {
            return;
        }
        if (effective.contains(root.getId())) {
            root.setHasRole(true);
        }
        if (root.getSubRoleList() == null) {
            return;
        }
        for (RoleVO subRoot : root.getSubRoleList()) {
            dfs(subRoot, effective);
        }
    }
}




