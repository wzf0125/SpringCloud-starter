package org.quanta.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.quanta.common.controller.BaseController;
import org.quanta.core.annotation.ApiPermission;
import org.quanta.core.beans.Response;
import org.quanta.core.constant.cache.RoleCache;
import org.quanta.core.constant.cache.UserCache;
import org.quanta.user.dto.AddRoleDTO;
import org.quanta.user.dto.BatchDeleteDTO;
import org.quanta.user.dto.EditRoleDTO;
import org.quanta.user.dto.EditRolePermissionDTO;
import org.quanta.user.entity.Role;
import org.quanta.user.feign.IRoleClient;
import org.quanta.user.service.RolePermissionService;
import org.quanta.user.service.RoleService;
import org.quanta.user.vo.PermissionVO;
import org.quanta.user.vo.RoleVO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/12/25
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/role")
@Api(tags = "角色管理", value = "角色管理")
@ApiPermission({":user", ":user:role"})
public class RoleController extends BaseController implements IRoleClient {
    private final RoleService roleService;
    private final RolePermissionService rolePermissionService;

    @Override
    @ApiOperation(value = "获取角色列表")
    @ApiPermission({":user:role:get"})
    @Cacheable(value = RoleCache.SYSTEM_USER_ROLE_TREE, key = RoleCache.SYSTEM_USER_ROLE_TREE)
    public Response<List<RoleVO>> getRoleTree() {
        return Response.success(roleService.getRoleTree());
    }

    @Override
    @ApiOperation(value = "添加角色")
    @ApiPermission({":user:role:add"})
    @CacheEvict(value = {RoleCache.SYSTEM_USER_ROLE_LIST, RoleCache.SYSTEM_USER_ROLE_TREE})
    public <T> Response<T> addRole(@Validated @RequestBody AddRoleDTO dto) {
        roleService.saveRole(dto);
        return Response.success();
    }

    @Override
    @ApiOperation(value = "编辑角色")
    @ApiPermission({":user:role:edit"})
    @CacheEvict(value = {RoleCache.SYSTEM_USER_ROLE_LIST, RoleCache.SYSTEM_USER_ROLE_TREE})
    public <T> Response<T> editRole(@Validated @RequestBody EditRoleDTO dto) {
        roleService.updateById(Role.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build());
        return Response.success();
    }

    @Override
    @ApiOperation(value = "删除角色")
    @ApiPermission({":user:role:delete"})
    @CacheEvict(value = {RoleCache.SYSTEM_USER_ROLE_LIST, RoleCache.SYSTEM_USER_ROLE_TREE})
    public <T> Response<T> deleteRole(@PathVariable Integer id) {
        roleService.removeRoleById(id);
        return Response.success();
    }

    @Override
    @ApiOperation(value = "批量删除角色")
    @ApiPermission({":user:role:delete"})
    @CacheEvict(value = {RoleCache.SYSTEM_USER_ROLE_LIST, RoleCache.SYSTEM_USER_ROLE_TREE})
    public <T> Response<T> batchDeleteRole(@Validated @RequestBody BatchDeleteDTO dto) {
        roleService.removeBatchByIds(dto.getIdList());
        return Response.success();
    }

    @Override
    @ApiOperation(value = "获取角色权限列表")
    @ApiPermission({":user:role:get"})
    @Cacheable(value = RoleCache.SYSTEM_USER_ROLE_PERMISSION, key = "#roleId")
    public Response<List<PermissionVO>> getRolePermission(@PathVariable Integer roleId) {
        return Response.success(roleService.getRolePermissionById(roleId));
    }

    @Override
    @ApiOperation(value = "编辑角色权限")
    @ApiPermission({":user:role:get"})
    @Caching(evict = {
            @CacheEvict(value = RoleCache.SYSTEM_USER_ROLE_PERMISSION, key = "#dto.roleId"),
            @CacheEvict(value = UserCache.SYSTEM_USER_USER_PERMISSION_LIST, allEntries = true),
            @CacheEvict(value = UserCache.SYSTEM_USER_USER_BY_EMAIL, allEntries = true),
            @CacheEvict(value = UserCache.SYSTEM_USER_USER_BY_ACCOUNT, allEntries = true)
    })
    public <T> Response<T> editRolePermission(@Validated @RequestBody EditRolePermissionDTO dto) {
        rolePermissionService.editRolePermission(dto);
        return Response.success();
    }

}
