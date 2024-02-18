package org.quanta.core.aspect;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.quanta.base.constants.AuthConstants;
import org.quanta.base.exception.PermissionException;
import org.quanta.core.annotation.ApiPermission;
import org.quanta.core.constants.JWTKeyConstants;
import org.quanta.core.utils.JWTUtils;
import org.quanta.core.utils.WebUtils;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Description: 认证切面
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/16
 */
@Aspect
@Component
@RequiredArgsConstructor
public class AuthAspect {
    private final JWTUtils jwtUtils;

    /**
     * 带有权限拦截注解的进行鉴权处理
     * 对用户权限和拦截权限去交集 交集不为空则放行
     */
    @Before("@within(org.quanta.core.annotation.ApiPermission) || @annotation(org.quanta.core.annotation.ApiPermission)")
    public void before(JoinPoint joinPoint) {
        List<String> requiredPermissionList = getAnnotation(joinPoint);
        // 跳过放行方法
        if (requiredPermissionList.isEmpty() || requiredPermissionList.contains(AuthConstants.SKIP_AUTH)) {
            return;
        }
        List<String> permissionList = getPermissionList();
        if (CollUtil.intersection(requiredPermissionList, permissionList).isEmpty()) {
            throw new PermissionException("非法访问");
        }
    }

    /**
     * 读取方法/类权限拦截注解
     *
     * @param joinPoint 切点
     * @return 权限集合
     */
    private List<String> getAnnotation(JoinPoint joinPoint) {
        // 这里得到的可能是接口方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Class<?> targetClass = method.getDeclaringClass();
        // 获取具体实现类方法
        Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
        specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
        ApiPermission annotation = AnnotatedElementUtils.findMergedAnnotation(specificMethod, ApiPermission.class);
        // 方法上找不到则找类上的
        if (annotation == null) {
            annotation = AnnotatedElementUtils.findMergedAnnotation(specificMethod.getDeclaringClass(), ApiPermission.class);
        }
        return ListUtil.toList(annotation.value());
    }

    /**
     * 解析jwt中的权限列表
     *
     * @return jwt权限列表解析
     */
    private List<String> getPermissionList() {
        String header = WebUtils.getRequest().getHeader(AuthConstants.TOKEN_KEY);
        String jwt = jwtUtils.getJWT(header);
        if (jwt == null) {
            throw new PermissionException("非法访问");
        }
        Claims claims = jwtUtils.parseJWT(jwt);
        if (claims == null) {
            throw new PermissionException("授权过期");
        }
        // 放行客户端模式jwt
        if (!claims.containsKey(JWTKeyConstants.PERMISSION_LIST)) {
            return ListUtil.toList(AuthConstants.SKIP_CLIENT);
        }
        return StrUtil.split((String) claims.get(JWTKeyConstants.PERMISSION_LIST), ',');
    }

}
