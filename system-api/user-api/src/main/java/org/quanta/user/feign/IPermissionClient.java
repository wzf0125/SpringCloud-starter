package org.quanta.user.feign;

import lombok.extern.slf4j.Slf4j;
import org.quanta.common.contants.APP_NAME;
import org.quanta.core.beans.Response;
import org.quanta.user.dto.AddPermissionDTO;
import org.quanta.user.dto.BatchDeleteDTO;
import org.quanta.user.dto.EditPermissionDTO;
import org.quanta.user.vo.PermissionVO;
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
        contextId = "permission",
        fallback = IPermissionClient.IPermissionFallback.class,
        path = "/system/permission")
public interface IPermissionClient {

    /**
     * 获取权限列表
     */
    @GetMapping
    Response<List<PermissionVO>> getPermissionTree();

    /**
     * 添加权限
     */
    @PostMapping
    <T> Response<T> addPermission(@Validated AddPermissionDTO dto);

    /**
     * 编辑权限
     */
    @PutMapping
    <T> Response<T> editPermission(@Validated EditPermissionDTO dto);

    /**
     * 删除权限
     */
    @DeleteMapping("/{id}")
    <T> Response<T> deletePermission(@PathVariable Integer id);

    /**
     * 删除权限
     */
    @DeleteMapping("/batch")
    <T> Response<T> batchDeletePermission(@RequestBody BatchDeleteDTO dto);

    /**
     * 异常回调类
     */
    @Slf4j
    @Component
    class IPermissionFallback implements IPermissionClient {

        @Override
        public Response<List<PermissionVO>> getPermissionTree() {
            log.error("调用异常IPermissionClient getPermissionList");
            return Response.feignError();
        }

        @Override
        public <T> Response<T> addPermission(AddPermissionDTO dto) {
            log.error("调用异常IPermissionClient addPermission");
            return Response.feignError();
        }

        @Override
        public <T> Response<T> editPermission(EditPermissionDTO dto) {
            log.error("调用异常IPermissionClient editPermission");
            return Response.feignError();
        }

        @Override
        public <T> Response<T> deletePermission(Integer id) {
            log.error("调用异常IPermissionClient deletePermission");
            return Response.feignError();
        }

        @Override
        public <T> Response<T> batchDeletePermission(BatchDeleteDTO dto) {
            log.info("调用异常IPermissionClient batchDeletePermission");
            return Response.feignError();
        }
    }
}
