package org.quanta.core.utils;

import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.quanta.base.constants.AuthConstants;
import org.quanta.base.exception.PermissionException;
import org.quanta.core.constants.JWTConstants;
import org.quanta.core.constants.JWTKeyConstants;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: jwt工具封装
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/6
 */
@Component
@RequiredArgsConstructor
public class JWTUtils {
    private final JWTConstants jwtConstants;

    /**
     * 从header中提取JWT
     * Authorization=Bearer jwtxxxxx
     *
     * @param tokenHead 请求头
     * @return jwt
     */
    public String getJWT(String tokenHead) {
        if (StrUtil.isBlank(tokenHead) || !tokenHead.contains(AuthConstants.TOKEN_PREFIX)) {
            return null;
        }
        return tokenHead.substring(7);
    }

    /**
     * jwt解码工具
     *
     * @param jwt jwtToken
     * @return jwt解析数据
     */
    public Claims parseJWT(String jwt) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Base64.getEncoder().encodeToString(jwtConstants.getDefaultKey().getBytes())).build()
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception exp) { // ExpiredJwtException(jwt过期) SignatureException(签名异常)
            return null;
        }
    }



    /**
     * 从header中提取JWT并解析验证签名
     * Authorization=Bearer jwtxxxxx
     *
     * @param tokenHead 请求头
     * @return jwt解析数据
     */
    public Claims parseHeaderJWT(String tokenHead) {
        String jwt = getJWT(tokenHead);
        if (StrUtil.isBlank(jwt)) {
            throw new PermissionException("非法访问");
        }
        Claims claims = parseJWT(jwt);
        if (claims == null) {
            throw new PermissionException("非法访问");
        }
        return claims;
    }

    /**
     * 映射jwt参数为Map
     *
     * @param tokenHead 请求头
     * @return 映射结果
     */
    public Map<String, Object> parseHeaderJWT2UserInfo(String tokenHead) {
        // 提取token转换为jwt并解析
        Claims claims = parseHeaderJWT(tokenHead);
        // 反射读取jwt字段列表
        Field[] fields = JWTKeyConstants.class.getFields();
        // 解析并放入map
        Map<String, Object> res = new HashMap<>();
        for (Field field : fields) {
            if (claims.containsKey(field.getName())) {
                res.put(field.getName(), claims.get(field.getName()));
            }
        }
        return res;
    }

}
