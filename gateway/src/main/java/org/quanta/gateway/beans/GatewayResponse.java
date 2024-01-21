package org.quanta.gateway.beans;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.quanta.core.constants.ResultCode;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/12/19
 */
@Slf4j
public class GatewayResponse {

    public static Map<String, Object> unAuth() {
        return MapUtil.<String, Object>builder()
                .put("code", ResultCode.PERMISSION_DENIED)
                .put("msg", "unAuth")
                .build();
    }

    public static Map<String, Object> unAuth(String msg) {
        return MapUtil.<String, Object>builder()
                .put("code", ResultCode.PERMISSION_DENIED)
                .put("msg", msg)
                .build();
    }

    public static Map<String, Object> blocked() {
        return MapUtil.<String, Object>builder()
                .put("code", ResultCode.PERMISSION_DENIED)
                .put("msg", "blocked")
                .build();
    }

    public static Map<String, Object> blocked(String msg) {
        return MapUtil.<String, Object>builder()
                .put("code", ResultCode.PERMISSION_DENIED)
                .put("msg", msg)
                .build();
    }

    public static Mono<Void> blocked(ServerHttpResponse resp) {
        return blocked(resp, null);
    }

    public static Mono<Void> blocked(ServerHttpResponse resp, String msg) {
//        resp.setStatusCode(HttpStatus.UNAUTHORIZED); // 设置http状态码为401(看具体业务觉得是否开启这个配置)
        resp.setStatusCode(HttpStatus.OK); // 设置http状态码为200 只以响应内容中的code为准
        // 设置请求头
        resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        String result = "";
        try {
            // 响应内容
            Map<String, Object> respData = StrUtil.isNotBlank(msg) ? blocked(msg) : blocked();
            result = new ObjectMapper().writeValueAsString(respData);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        DataBuffer buffer = resp.bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
        return resp.writeWith(Flux.just(buffer));
    }

}
