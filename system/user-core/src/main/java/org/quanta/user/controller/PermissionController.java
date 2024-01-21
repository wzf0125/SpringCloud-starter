package org.quanta.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.quanta.common.controller.BaseController;
import org.quanta.core.annotation.ApiPermission;
import org.quanta.core.beans.Response;
import org.quanta.core.constant.cache.PermissionCache;
import org.quanta.user.dto.AddPermissionDTO;
import org.quanta.user.dto.BatchDeleteDTO;
import org.quanta.user.dto.EditPermissionDTO;
import org.quanta.user.entity.Permission;
import org.quanta.user.feign.IPermissionClient;
import org.quanta.user.service.PermissionService;
import org.quanta.user.vo.PermissionVO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.validation.annotation.Validated;
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
@EnableCaching
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/permission")
@Api(tags = "权限管理", value = "权限管理")
@ApiPermission({":user", ":user:permission"})
public class PermissionController extends BaseController implements IPermissionClient {
    private final PermissionService permissionService;

    /**
     * 获取接口权限列表
     */
    @Override
    @ApiOperation(value = "获取接口权限树")
    @ApiPermission({":user:permission:get"})
    @Cacheable(value = PermissionCache.SYSTEM_USER_PERMISSION_TREE, key = PermissionCache.SYSTEM_USER_PERMISSION_TREE)
    public Response<List<PermissionVO>> getPermissionTree() {
        return Response.success(permissionService.getPermissionTree());
    }

    /**
     * 添加接口权限
     */
    @Override
    @ApiOperation(value = "添加接口权限")
    @ApiPermission({":user:permission:add"})
    @CacheEvict(value = {PermissionCache.SYSTEM_USER_PERMISSION_TREE,PermissionCache.SYSTEM_USER_PERMISSION_LIST})
    public <T> Response<T> addPermission(@Validated @RequestBody AddPermissionDTO dto) {
        permissionService.savePermission(dto);
        return Response.success();
    }

    /**
     * 编辑接口权限
     */
    @Override
    @ApiOperation(value = "编辑接口权限")
    @ApiPermission({":user:permission:edit"})
    @CacheEvict(value = {PermissionCache.SYSTEM_USER_PERMISSION_TREE,PermissionCache.SYSTEM_USER_PERMISSION_LIST})
    public <T> Response<T> editPermission(@Validated @RequestBody EditPermissionDTO dto) {
        permissionService.updateById(Permission.builder()
                .id(dto.getId())
                .path(dto.getPath())
                .name(dto.getName())
                .desc(dto.getDesc())
                .build());
        return Response.success();
    }

    /**
     * 删除接口权限
     */
    @Override
    @ApiOperation(value = "删除接口权限")
    @ApiPermission({":user:permission:delete"})
    @CacheEvict(value = {PermissionCache.SYSTEM_USER_PERMISSION_TREE,PermissionCache.SYSTEM_USER_PERMISSION_LIST})
    public <T> Response<T> deletePermission(Integer id) {
        permissionService.removePermissionById(id);
        return Response.success();
    }

    /**
     * 批量删除接口权限
     */
    @Override
    @ApiOperation(value = "批量删除接口权限")
    @ApiPermission({":user:permission:delete"})
    @CacheEvict(value = {PermissionCache.SYSTEM_USER_PERMISSION_TREE,PermissionCache.SYSTEM_USER_PERMISSION_LIST})
    public <T> Response<T> batchDeletePermission(BatchDeleteDTO dto) {
        permissionService.removeBatchByIds(dto.getIdList());
        return Response.success();
    }
}
