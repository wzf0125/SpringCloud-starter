package org.quanta.core.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quanta.base.config.LogConfig;
import org.quanta.base.config.SystemConfig;
import org.quanta.base.constants.AuthConstants;
import org.quanta.base.constants.LogLevel;
import org.quanta.base.exception.PermissionException;
import org.quanta.base.exception.ServiceException;
import org.quanta.core.beans.Response;
import org.quanta.core.config.ExceptionConfig;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

/**
 * Description: 全局异常处理
 * Author: wzf
 * Date: 2023/10/5
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final SystemConfig systemConfig;
    private final LogConfig logConfig;
    private final ExceptionConfig exceptionConfig;

    @ExceptionHandler(Exception.class)
    public @ResponseBody
    Response<Object> errorResult(Exception e) throws Exception {
        // 白名单异常直接抛出
        handleWhiteList(e);

        log.error(e.getMessage());
        // 非debug模式的话只返回服务器错误提示 不返回具体错误内容
        if (!systemConfig.getDebug()) {
            return Response.serverError();
        }
        // debug模式控制台打印错误信息，响应内容提供错误信息
        e.printStackTrace();
        return logAndReturn(Response.serverError(String.format("操作失败，请重试[%s]", e.getMessage())));
    }

    /**
     * 处理白名单
     */
    private void handleWhiteList(Exception e) throws Exception {
        List<Class<?>> whiteList = exceptionConfig.getWhiteList();
        if (whiteList == null || whiteList.isEmpty()) return;
        for (Class<?> clazz : whiteList) {
            if (clazz.isInstance(e)) {
                throw e;
            }
        }
    }

    // 数据校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody
    Response<Object> validationErrorResult(MethodArgumentNotValidException e) {
        return logAndReturn(buildParamErrorResp(e));
    }

    @ExceptionHandler(BindException.class)
    public @ResponseBody
    Response<Object> handleBindException(BindException ex) {
        return logAndReturn(buildParamErrorResp(ex));
    }

    /**
     * 参数异常响应信息
     */
    private Response<Object> buildParamErrorResp(BindException ex) {
        BindingResult result = ex.getBindingResult();
        StringBuilder sb = new StringBuilder();
        if (result.getFieldErrorCount() > 0) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
        }
        return Response.paramError(String.format("参数校验错误[%s]", sb));
    }

    // 自定义异常处理类
    @ExceptionHandler(ServiceException.class)
    public @ResponseBody
    Response<Object> apiErrorResult(ServiceException e) {
        return logAndReturn(Response.fail(e.getMessage()));
    }

    // 权限异常校验类
    @ExceptionHandler(PermissionException.class)
    public @ResponseBody
    Response<Object> permissionError(PermissionException e) {
        // 全局日志或者响应日志启用
        if (LogLevel.ALL.equals(logConfig.getLogLevel()) || LogLevel.RESPONSE.equals(logConfig.getLogLevel())) {

        }
        return logAndReturn(Response.permissionDenied(e.getMessage()));
    }

    // 响应日志模板日志模板
    private static final String AFTER_LOG_TEMPLATE = "RESPONSE_LOG: Token: {} | Uid: {} | Result: {} ";

    /**
     * 打印响应日志(异常处理后不会触发@AfterReturning 会导致响应日志丢失)
     */
    private Response<Object> logAndReturn(Response<Object> result) {
        if (LogLevel.ALL.equals(logConfig.getLogLevel()) || LogLevel.RESPONSE.equals(logConfig.getLogLevel())) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            long uid = 0L;
            String token = null;
            if (attributes != null) {
                token = attributes.getRequest().getHeader(AuthConstants.TOKEN_KEY);
                try {
                    uid = (long) attributes.getRequest().getAttribute("uid");
                } catch (Exception ignored) {
                }
            }
            log.info(AFTER_LOG_TEMPLATE, token, uid, result);
        }
        return result;
    }
}
