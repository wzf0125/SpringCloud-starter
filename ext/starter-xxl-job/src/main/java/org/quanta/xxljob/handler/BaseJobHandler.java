package org.quanta.xxljob.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.handler.IJobHandler;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

/**
 * Description: 执行器基类 继承自该类的handler会自动注册到xxl-job 成为执行器
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/26
 */
@Slf4j
public abstract class BaseJobHandler extends IJobHandler {

    @PostConstruct
    public void registerJobHandler() {
        try {
            String className = this.getClass().getSimpleName();
            XxlJobExecutor.registJobHandler(this.getClass().getSimpleName(), this);
            log.info("xxl-job执行器注册成功:{}", className);
        } catch (Exception e) {
            log.error("xxl-job执行器注册失败,{}", ExceptionUtil.stacktraceToString(e));
        }
    }
}
