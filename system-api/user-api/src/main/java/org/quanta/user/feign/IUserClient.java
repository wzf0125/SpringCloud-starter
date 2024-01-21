package org.quanta.user.feign;

import lombok.extern.slf4j.Slf4j;
import org.quanta.common.contants.APP_NAME;
import org.quanta.core.beans.PageQueryResult;
import org.quanta.core.beans.Response;
import org.quanta.user.dto.AddUserDTO;
import org.quanta.user.dto.EditUserDTO;
import org.quanta.user.dto.EditUserRoleDTO;
import org.quanta.user.entity.Permission;
import org.quanta.user.entity.Role;
import org.quanta.user.vo.PermissionVO;
import org.quanta.user.vo.RoleVO;
import org.quanta.user.vo.UserDetailsVO;
import org.quanta.user.vo.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
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
        contextId = "user",
        fallback = IUserClient.IUserClientFallback.class,
        path = "/system/user")
public interface IUserClient {
    /**
     * 通过邮箱获取用户
     */
    @GetMapping("/byAccount")
    Response<UserDetailsVO> getUserByAccount(@RequestParam(value = "account") String email);

    /**
     * 通过邮箱获取用户
     */
    @GetMapping("/byEmail")
    Response<UserDetailsVO> getUserByEmail(@RequestParam(value = "email") String email);

    /**
     * 获取用户列表
     */
    @GetMapping
    Response<PageQueryResult<UserVO>> getUserList(@RequestParam(value = "currentPage") Long currentPage,
                                                  @RequestParam(value = "pageSize") Long pageSize,
                                                  @RequestParam(value = "keyword", required = false) String keyword);

    /**
     * 添加用户
     */
    @PostMapping
    <T> Response<T> addUser(@RequestBody AddUserDTO dto);

    /**
     * 编辑用户
     */
    @PutMapping
    <T> Response<T> editUser(@RequestBody EditUserDTO dto);

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    <T> Response<T> deleteUser(@PathVariable("id") Integer id);

    /**
     * 获取用户角色
     */
    @GetMapping("/role/{id}")
    Response<List<RoleVO>> getUserRole(@PathVariable("id") Integer id);

    /**
     * 获取用户角色
     */
    @GetMapping("/role/list/{id}")
    Response<List<Role>> getUserRoleList(@PathVariable("id") Integer id);

    /**
     * 编辑用户角色
     */
    @PutMapping("/role")
    <T> Response<T> editUserRole(@RequestBody EditUserRoleDTO dto);

    /**
     * 获取用户权限（树状）
     */
    @GetMapping("/permission/{id}")
    Response<List<PermissionVO>> getUserPermission(@PathVariable("id") Integer id);

    /**
     * 获取用户权限（列表）
     */
    @GetMapping("/permission/list/{id}")
    Response<List<Permission>> getUserPermissionList(@PathVariable("id") Integer id);

    /**
     * 异常回调类
     */
    @Slf4j
    @Component
    class IUserClientFallback implements IUserClient {
        @Override
        public Response<UserDetailsVO> getUserByAccount(String account) {
            log.error("调用异常IUserClient getUserByAccount");
            return Response.feignError();
        }

        @Override
        public Response<UserDetailsVO> getUserByEmail(String email) {
            log.error("调用异常IUserClient getUserByEmail");
            return Response.feignError();
        }

        @Override
        public Response<PageQueryResult<UserVO>> getUserList(Long currentPage, Long pageSize, String keyword) {
            log.error("调用异常IUserClient getUserList");
            return Response.feignError();
        }

        @Override
        public <T> Response<T> addUser(@RequestBody AddUserDTO dto) {
            log.error("调用异常IUserClient addUser");
            return Response.feignError();
        }

        @Override
        public <T> Response<T> editUser(@RequestBody EditUserDTO dto) {
            log.error("调用异常IUserClient editUser");
            return Response.feignError();
        }

        @Override
        public <T> Response<T> deleteUser(Integer id) {
            log.error("调用异常IUserClient deleteUser");
            return Response.feignError();
        }

        @Override
        public Response<List<RoleVO>> getUserRole(Integer id) {
            log.error("调用异常IUserClient getUserRole");
            return Response.feignError();
        }

        @Override
        public Response<List<Role>> getUserRoleList(Integer id) {
            log.error("调用异常IUserClient getUserRoleList");
            return Response.feignError();
        }

        @Override
        public <T> Response<T> editUserRole(EditUserRoleDTO dto) {
            log.error("调用异常IUserClient editUserRole");
            return Response.feignError();
        }

        @Override
        public Response<List<PermissionVO>> getUserPermission(Integer id) {
            log.error("调用异常IUserClient getUserPermission");
            return Response.feignError();
        }

        @Override
        public Response<List<Permission>> getUserPermissionList(Integer id) {
            log.error("调用异常IUserClient getUserPermissionList");
            return Response.feignError();
        }
    }
}
