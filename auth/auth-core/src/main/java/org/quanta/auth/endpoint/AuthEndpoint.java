package org.quanta.auth.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.quanta.auth.service.AuthService;
import org.quanta.core.annotation.ApiPermission;
import org.quanta.core.beans.Response;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: 认证服务拓展接口
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/15
 */
@ApiPermission
@RestController
@Api("认证服务拓展接口")
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthEndpoint {
    private final AuthService authService;

    @ApiOperation("登出")
    @DeleteMapping("/logout")
    public Response<Object> logout() {
        authService.logout();
        return Response.success();
    }
}
