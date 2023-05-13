package com.ruoyi.flowable.listener;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;

/**
 * 用户任务监听器
 *
 * @author KonBAI
 * @since 2023/5/13
 */
@Component(value = "userTaskListener")
public class UserTaskListener implements TaskListener {

    /**
     * 注入字段（名称与流程设计时字段名称一致）
     */
    // private FixedValue field;

    @Override
    public void notify(DelegateTask delegateTask) {
        //TODO 实现你的任务监听器逻辑
        System.out.println("执行任务监听器...");
    }

}
