package com.learning.gateway.scheduler;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.alibaba.nacos.NacosServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.actuate.GatewayControllerEndpoint;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import reactor.core.publisher.Mono;

/**
 * 动态路由-定时任务
 * 
 * @author xiongchaoqun
 * @date 2019年8月1日
 */
@Configuration // 1.主要用于标记配置类
@EnableScheduling // 2.开启定时任务
public class GatewayScheduler {
	private static final Logger log = LoggerFactory.getLogger(GatewayScheduler.class);
	
	@Value("${spring.application.name}")
	private String applicationName;
	
	@Autowired
	private GatewayControllerEndpoint gatewayControllerEndpoint;

	@Autowired
	private RouteDefinitionWriter routeDefinitionWriter;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private DiscoveryClient discoveryClient;

	// @Scheduled(cron = "0/5 * * * * ?") //3.添加定时任务
	@Scheduled(fixedRate = 10000) // 直接指定时间间隔，例如：5秒
	public void configureTasks() {
		Map<String, Boolean> routeMap = new HashMap<String, Boolean>();
		Mono<List<Map<String, Object>>> routes = gatewayControllerEndpoint.routes();
		List<Map<String, Object>> routeList = routes.block();

		// 获取已经存在的路由转发配置
		for (Map<String, Object> m : routeList) {
			String route_id = (String) m.get("route_id");
			if (route_id.startsWith("CompositeDiscoveryClient_")) {
				continue;
			}
			routeMap.put(route_id, true);
		}

		try {
			List<String> services = discoveryClient.getServices();
			for (String service : services) {
				
				List<ServiceInstance> sis = discoveryClient.getInstances(service);
				
				if (!sis.isEmpty()) {
					ServiceInstance si = sis.get(0);
					NacosServiceInstance nsi = (NacosServiceInstance) si;
					String orginalName = nsi.getServiceId();
					// 已经存在路由配置的不处理
					if (routeMap.containsKey(orginalName) || applicationName.equals(orginalName)) {
						continue;
					}
					log.info("服务模块：{} 【动态路由添加】",orginalName);
					// 添加路由转发规则
					RouteDefinition route = new RouteDefinition();
					route.setId(orginalName);
					route.setUri(new URI("lb://" + orginalName));
					
					//添加断言-转发规则
					PredicateDefinition predicate = new PredicateDefinition();
					predicate.setName("Path");
					Map<String, String> predicateParams = new HashMap<>(8);
					predicateParams.put("_genkey_0", "/" + orginalName + "/**");
					predicate.setArgs(predicateParams);
					
					//添加过滤器
//					FilterDefinition filter = new FilterDefinition();
//					filter.setName("Hystrix");//必须是Hystrix
//					Map<String, String> filterParams = new HashMap<>(8);
//					filterParams.put("fallbackUri", "forward:/fallback");
//					filter.setArgs(filterParams);
					
					route.setPredicates(Arrays.asList(predicate));
//					route.setFilters(Arrays.asList(filter));
					
					routeDefinitionWriter.save(Mono.just(route)).subscribe();
					this.publisher.publishEvent(new RefreshRoutesEvent(this));
				}else if (routeMap.containsKey(service)) {
					
					log.info("服务模块：{} 【无可用实例，动态路由删除】",service);
					routeDefinitionWriter.delete(Mono.just(service)).subscribe();
					this.publisher.publishEvent(new RefreshRoutesEvent(this));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}
}
