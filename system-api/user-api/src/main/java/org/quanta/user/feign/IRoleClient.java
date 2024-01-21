package org.quanta.user.feign;

import lombok.extern.slf4j.Slf4j;
import org.quanta.common.contants.APP_NAME;
import org.quanta.core.beans.Response;
import org.quanta.user.dto.AddRoleDTO;
import org.quanta.user.dto.BatchDeleteDTO;
import org.quanta.user.dto.EditRoleDTO;
import org.quanta.user.dto.EditRolePermissionDTO;
import org.quanta.user.vo.PermissionVO;
import org.quanta.user.vo.RoleVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/12/25
 */
@FeignClient(value = APP_NAME.APP_SYSTEM_USER,
        contextId = "role",
        fallback = IRoleClient.IRoleFallback.class,
        path = "/system/role")
public interface IRoleClient {

    /**
     * 获取角色列表
     */
    @GetMapping
    Response<List<RoleVO>> getRoleTree();

    /**
     * 添加角色
     */
    @PostMapping
    <T> Response<T> addRole(@Validated  @RequestBody AddRoleDTO dto);

    /**
     * 编辑角色
     */
    @PutMapping
    <T> Response<T> editRole(@Validated  @RequestBody EditRoleDTO editRoleDTO);

    /**
     * 删除角色
     */
    @DeleteMapping("/{id}")
    <T> Response<T> deleteRole(@PathVariable Integer id);

    /**
     * 获取角色权限列表
     */
    @GetMapping("/permission/{roleId}")
    Response<List<PermissionVO>> getRolePermission(@PathVariable Integer roleId);

    /**
     * 编辑角色权限
     */
    @PutMapping("/permission")
    <T> Response<T> editRolePermission(@Validated @RequestBody EditRolePermissionDTO dto);

    /**
     * 删除角色
     */
    @DeleteMapping("/batch")
    <T> Response<T> batchDeleteRole(@Validated  @RequestBody BatchDeleteDTO dto);

    /**
     * 异常回调类
     */
    @Slf4j
    @Component
    class IRoleFallback implements IRoleClient {
        @Override
        public Response<List<RoleVO>> getRoleTree() {
            log.error("调用异常IRoleClient getRoleList");
            return Response.feignError();
        }

        @Override
        public <T> Response<T> addRole(@RequestBody AddRoleDTO dto) {
            log.error("调用异常IRoleClient addRole");
            return Response.feignError();
        }

        @Override
        public <T> Response<T> editRole(@RequestBody EditRoleDTO editRoleDTO) {
            log.error("调用异常IRoleClient editRole");
            return Response.feignError();
        }

        @Override
        public <T> Response<T> deleteRole(@PathVariable Integer id) {
            log.error("调用异常IRoleClient deleteRole");
            return Response.feignError();
        }

        @Override
        public <T> Response<T> batchDeleteRole(BatchDeleteDTO dto) {
            log.error("调用异常IRoleClient batchDeleteRole");
            return Response.feignError();
        }

        @Override
        public Response<List<PermissionVO>> getRolePermission(@PathVariable Integer roleId) {
            log.error("调用异常IRoleClient getRolePermission");
            return Response.feignError();
        }

        @Override
        public <T> Response<T> editRolePermission(@RequestBody EditRolePermissionDTO dto) {
            log.error("调用异常IRoleClient editRolePermission");
            return Response.feignError();
        }
    }
}
