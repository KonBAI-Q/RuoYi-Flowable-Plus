package org.dromara.common.flowable.config;

import lombok.AllArgsConstructor;
import org.dromara.common.flowable.listener.GlobalEventListener;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.engine.RuntimeService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Flowable全局监听配置
 *
 * @author KonBAI
 */
@Configuration
@AllArgsConstructor
public class GlobalEventListenerConfig implements ApplicationListener<ContextRefreshedEvent> {

	private final GlobalEventListener globalEventListener;
	private final RuntimeService runtimeService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// 流程正常结束
		runtimeService.addEventListener(globalEventListener, FlowableEngineEventType.PROCESS_COMPLETED);
	}
}
