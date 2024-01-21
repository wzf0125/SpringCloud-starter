package org.quanta.core.utils;

import org.quanta.base.exception.ServiceException;
import org.quanta.core.beans.Response;
import org.quanta.core.constants.ResultCode;

/**
 * Description: Response解码工具
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/8
 */
public class ResponseUtils {
    /**
     * @param response 服务调用响应对象
     * @param <T>      data内容
     * @return 解析响应code并提取内容
     */
    public static <T> T getData(Response<T> response) {
        ResultCode resultCode = ResultCode.codeOf(response.getCode());
        if (resultCode == null) return null;
        switch (resultCode) {
            // 仅success时提取
            case SUCCESS:
                return response.getData();
            // 其他情况一律抛异常
            case FAIL:
            case PERMISSION_DENIED:
            case PARAM_ERROR:
            case UNAUTHORIZED:
            case SERVER_ERROR:
            default:
                throw new ServiceException(response.getMsg());
        }
    }
}
