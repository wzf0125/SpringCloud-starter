package org.quanta.common.controller;

import cn.hutool.core.util.StrUtil;
import org.quanta.base.constants.AuthConstants;
import org.quanta.base.utils.SpringUtils;
import org.quanta.core.annotations.Log;
import org.quanta.core.constants.JWTKeyConstants;
import org.quanta.core.utils.JWTUtils;
import org.quanta.core.utils.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Description: 控制器增强
 * Author: wzf
 * Date: 2023/10/6
 */
@Log
public class BaseController {
    private HttpServletRequest getRequest() {
        return WebUtils.getRequest();
    }

    /**
     * 获取目前用户的id和role
     * 这里采用再次解析jwt的方式 可考虑在认证切面(AuthAspect)将解析内容塞入请求
     *
     * @return id和role数组
     */
    private Map<String, Object> getCurrentUser() {
        HttpServletRequest request = getRequest();
        // 获取jwt工具类
        JWTUtils jwtUtils = SpringUtils.getBean(JWTUtils.class);
        // 从请求头提取token并转换为jwt解析出用户信息
        return jwtUtils.parseHeaderJWT2UserInfo(request.getHeader(AuthConstants.TOKEN_KEY));
    }

    /**
     * 获取用户uid
     *
     * @return uid
     */
    public int getUid() {
        return (int) getCurrentUser().get(JWTKeyConstants.ID);
    }

    /**
     * 获取用户角色
     *
     * @return role
     */
    public List<String> getRole() {
        return StrUtil.split((String) getCurrentUser().get(JWTKeyConstants.ROLE_LIST), ',');
    }

    /**
     * 获取用户权限
     *
     * @return role
     */
    public List<String> getPermission() {
        return StrUtil.split((String) getCurrentUser().get(JWTKeyConstants.PERMISSION_LIST), ',');
    }

    /**
     * 获取用户名
     *
     * @return role
     */
    public String getUsername() {
        return (String) getCurrentUser().get(JWTKeyConstants.USERNAME);
    }
}
