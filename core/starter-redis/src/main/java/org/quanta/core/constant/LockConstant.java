package org.quanta.core.constant;

/**
 * Description: 锁redis key
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/4
 */
public interface LockConstant {
    String LOCK_USER_EMAIL = "lock:user:email:%s";
    String LOCK_USER_ACCOUNT = "lock:user:account:%s";
}
