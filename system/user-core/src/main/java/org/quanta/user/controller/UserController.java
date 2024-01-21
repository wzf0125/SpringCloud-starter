package org.quanta.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.quanta.base.constants.AuthConstants;
import org.quanta.common.controller.BaseController;
import org.quanta.core.annotation.ApiPermission;
import org.quanta.core.beans.PageQueryDO;
import org.quanta.core.beans.PageQueryResult;
import org.quanta.core.beans.Response;
import org.quanta.core.constant.cache.UserCache;
import org.quanta.user.dto.AddUserDTO;
import org.quanta.user.dto.EditUserDTO;
import org.quanta.user.dto.EditUserRoleDTO;
import org.quanta.user.entity.Permission;
import org.quanta.user.entity.Role;
import org.quanta.user.feign.IUserClient;
import org.quanta.user.service.UserPermissionService;
import org.quanta.user.service.UserRoleService;
import org.quanta.user.service.UserService;
import org.quanta.user.vo.PermissionVO;
import org.quanta.user.vo.RoleVO;
import org.quanta.user.vo.UserDetailsVO;
import org.quanta.user.vo.UserVO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
@RequestMapping("/system/user")
@Api(tags = "用户管理", value = "用户管理")
@ApiPermission({":user", ":user:user"})
public class UserController extends BaseController implements IUserClient {
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final UserPermissionService userPermissionService;

    @Override
    @ApiOperation("通过账号获取用户")
    @ApiPermission({AuthConstants.SKIP_AUTH})
    @Cacheable(value = UserCache.SYSTEM_USER_USER_BY_ACCOUNT, key = "#account")
    public Response<UserDetailsVO> getUserByAccount(
            @ApiParam(name = "account", value = "账号", required = true) String account) {
        return Response.success(userService.getUserByAccount(account));
    }

    @Override
    @ApiOperation("通过邮箱获取用户")
    @ApiPermission({":user:user:get:byEmail"})
    @Cacheable(value = UserCache.SYSTEM_USER_USER_BY_EMAIL, key = "#email")
    public Response<UserDetailsVO> getUserByEmail(
            @ApiParam(name = "email", value = "邮箱", required = true) String email) {
        return Response.success(userService.getUserByEmail(email));
    }

    @Override
    @ApiOperation(value = "分页获取用户列表")
    @ApiPermission({":user:user:get"})
    // 分页缓存可以考虑去掉 如果用户经常变化的话
    @Cacheable(value = UserCache.SYSTEM_USER_USER_PAGE, key = "#currentPage+','+#pageSize", condition = "#keyword==null")
    public Response<PageQueryResult<UserVO>> getUserList(
            @ApiParam(name = "currentPage", value = "当前页", required = true) Long currentPage,
            @ApiParam(name = "pageSize", value = "页大小", required = true) Long pageSize,
            @ApiParam(name = "keyword", value = "关键词检索") String keyword) {
        return Response.success(userService.getUserByPage(keyword,
                PageQueryDO.builder()
                        .currentPage(currentPage)
                        .pageSize(pageSize)
                        .build()));
    }

    @Override
    @ApiOperation(value = "添加用户")
    @ApiPermission({":user:user:add"})
    @CacheEvict(value = UserCache.SYSTEM_USER_USER_PAGE, allEntries = true)
    public <T> Response<T> addUser(@RequestBody AddUserDTO dto) {
        userService.createUser(dto);
        return Response.success();
    }

    @Override
    @ApiOperation(value = "编辑用户")
    @ApiPermission({":user:user:edit"})
    @Caching(
            evict = {
                    @CacheEvict(value = UserCache.SYSTEM_USER_USER_BY_EMAIL, key = "#dto.email"),
                    @CacheEvict(value = UserCache.SYSTEM_USER_USER_PAGE, allEntries = true),
            }
    )
    public <T> Response<T> editUser(@RequestBody EditUserDTO dto) {
        userService.editUser(dto);
        return Response.success();
    }

    @Override
    @ApiOperation(value = "删除用户")
    @ApiPermission({":user:user:delete"})
    @CacheEvict(value = UserCache.SYSTEM_USER_USER_PAGE, allEntries = true)
    public <T> Response<T> deleteUser(Integer id) {
        userService.deleteUser(id);
        return Response.success();
    }

    @Override
    @ApiOperation(value = "获取用户角色")
    @ApiPermission({":user:user:role:get"})
    @Cacheable(value = UserCache.SYSTEM_USER_USER_ROLE_TREE, key = "#id")
    public Response<List<RoleVO>> getUserRole(Integer id) {
        return Response.success(userRoleService.getUserRoleByUid(id));
    }


    @Override
    @ApiOperation(value = "获取用户角色列表")
    @ApiPermission({":user:user:role:get"})
    @Cacheable(value = UserCache.SYSTEM_USER_USER_ROLE_LIST, key = "#id")
    public Response<List<Role>> getUserRoleList(Integer id) {
        return Response.success(userRoleService.getUserRoleListByUid(id));
    }

    @Override
    @ApiOperation(value = "编辑用户角色")
    @ApiPermission({":user:user:role:edit"})
    @CacheEvict(value = {UserCache.SYSTEM_USER_USER_ROLE_TREE, UserCache.SYSTEM_USER_USER_ROLE_LIST}, key = "#dto.userId")
    public <T> Response<T> editUserRole(@RequestBody EditUserRoleDTO dto) {
        userRoleService.editUserRole(dto);
        return Response.success();
    }

    @Override
    @ApiOperation(value = "获取用户权限(树状)")
    @ApiPermission({":user:user:role:get"})
    @Cacheable(value = UserCache.SYSTEM_USER_USER_PERMISSION_LIST, key = "#id")
    public Response<List<PermissionVO>> getUserPermission(Integer id) {
        return Response.success(userPermissionService.getUserPermission(id));
    }

    @Override
    @ApiOperation(value = "获取用户列表权限(内部鉴权使用)")
    @Cacheable(value = UserCache.SYSTEM_USER_USER_PERMISSION_LIST, key = "#id")
    public Response<List<Permission>> getUserPermissionList(Integer id) {
        return Response.success(userPermissionService.getUserPermissionList(id));
    }

}
