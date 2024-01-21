package org.quanta.core.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/5
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    SUCCESS(200, "成功"),
    FAIL(400, "操作失败"),
    PERMISSION_DENIED(401, "权限不足"),
    PARAM_ERROR(402, "参数错误"),
    UNAUTHORIZED(403, "未认证"),
    SERVER_ERROR(500, "服务器内部错误");
    final Integer code;
    final String msg;

    public static ResultCode codeOf(Integer code) {
        ResultCode[] enumConstants = ResultCode.class.getEnumConstants();
        for (ResultCode enumConstant : enumConstants) {
            if (enumConstant.getCode().equals(code)) {
                return enumConstant;
            }
        }
        return null;
    }
}
