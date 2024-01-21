package org.quanta.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Description: 权限拦截注解
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/16
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiPermission {
    String[] value() default {};
}
