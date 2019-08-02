package com.learning.gateway.configuration;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.alibaba.nacos.NacosPropertySourceRepository;
import org.springframework.cloud.alibaba.nacos.client.NacosPropertySource;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.InMemoryRouteDefinitionRepository;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.yaml.snakeyaml.Yaml;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * 动态路由配置
 * 
 * 动态监听 gatewayDataId 这个配置文件,当配置修改后，动态重置网关路由
 * @author xiongchaoqun
 * @date 2019年7月18日
 */
public class DynamicRouteConfigListener implements ApplicationListener<ApplicationReadyEvent>,ApplicationEventPublisherAware {
	private final static Logger LOG = LoggerFactory.getLogger(DynamicRouteConfigListener.class);
	
	private final static String SPRING_CLOUD_GATEWAY_ROUTES_PREFIX = "routes";
	
	private String gatewayDataId;
	
	private final ConfigService configService;

	private AtomicBoolean ready = new AtomicBoolean(false);

	private Map<String, Listener> listenerMap = new ConcurrentHashMap<>(16);
	
    @Autowired
    private InMemoryRouteDefinitionRepository routeDefinitionWriter;
    
    private ApplicationEventPublisher publisher;
	
	
	public DynamicRouteConfigListener(ConfigService configService,String gatewayDataId) {
		this.configService = configService;
		this.gatewayDataId = gatewayDataId;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		// many Spring context
		if (this.ready.compareAndSet(false, true)) {
			this.registerNacosListenersForApplications();
		}
	}

	private void registerNacosListenersForApplications() {
		for (NacosPropertySource nacosPropertySource : NacosPropertySourceRepository.getAll()) {
			if (!nacosPropertySource.isRefreshable()) {
				continue;
			}
			String dataId = nacosPropertySource.getDataId();
			if (dataId.equals(this.gatewayDataId)) {
				registerNacosListener(nacosPropertySource.getGroup(), dataId);
			}
		}	
	}

	private void registerNacosListener(final String group, final String dataId) {
		Yaml yaml = new Yaml();//yaml 解析器

		Listener listener = listenerMap.computeIfAbsent(dataId, i -> new Listener() {
			@Override
			public void receiveConfigInfo(String configInfo) {
				try {
					Map<String, Object> parms = yaml.load(configInfo);
					JSONObject jsonObject = new JSONObject(parms);
					JSONArray routesArr = jsonObject.getJSONArray(SPRING_CLOUD_GATEWAY_ROUTES_PREFIX);
					for (int j = 0; j <  routesArr.size(); j++) {
						DynamicRouteDefinition dynamicRouteDefinition = JSON.parseObject(routesArr.getJSONObject(j).toJSONString(), DynamicRouteDefinition.class);
						RouteDefinition routeDefinition = transDynamicRouteDefinition(dynamicRouteDefinition);
						
						routeDefinitionWriter.getRouteDefinitions().filter(rd-> rd.getId().equals(routeDefinition.getId()))
						.subscribe(rd -> routeDefinitionWriter.delete(Mono.just(rd.getId())).subscribe());
						
						routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
						publisher.publishEvent(new RefreshRoutesEvent(this));
					}
				} catch (Exception e) {
					LOG.error("动态更新网关路由失败~",e);
				}
			}
			@Override
			public Executor getExecutor() {
				return null;
			}
		});

		try {
			configService.addListener(dataId, group, listener);
		}
		catch (NacosException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}
	
	
	/**
	 * 将DynamicRouteDefinition对象转换为RouteDefinition对象
	 * 
	 * @param dynamicRouteDefinition
	 * @return
	 */
	private RouteDefinition transDynamicRouteDefinition(DynamicRouteDefinition dynamicRouteDefinition) 
			throws Exception {
		RouteDefinition routeDefinition = new RouteDefinition(); //路由定义
		List<PredicateDefinition> predicates = new ArrayList<>();//断言
		List<FilterDefinition> filters = new ArrayList<>();//过滤
		
		List<DynamicPredicateDefinition> dynamicPredicateDefinitions = dynamicRouteDefinition.getPredicates();
		List<DynamicFilterDefinition> dynamicFilterDefinitions = dynamicRouteDefinition.getFilters();
		
		for (DynamicPredicateDefinition dynamicPredicateDefinition : dynamicPredicateDefinitions) {
			PredicateDefinition predicate = new PredicateDefinition();
			predicate.setName(dynamicPredicateDefinition.getName());
			predicate.setArgs(dynamicPredicateDefinition.getArgs());
			predicates.add(predicate);
		}
		
		for (DynamicFilterDefinition dynamicFilterDefinition : dynamicFilterDefinitions) {
			FilterDefinition filter = new FilterDefinition();
			filter.setName(dynamicFilterDefinition.getName());
			filter.setArgs(dynamicFilterDefinition.getArgs());
			filters.add(filter);
		}
		routeDefinition.setFilters(filters);
		routeDefinition.setPredicates(predicates);
		routeDefinition.setId(dynamicRouteDefinition.getId());
		routeDefinition.setUri(new URI(dynamicRouteDefinition.getUri()));
		routeDefinition.setOrder(dynamicRouteDefinition.getOrder());
		return routeDefinition;
	}
	
	public static void main(String[] args) {
		String[] arr = new String[]{"aa"};
		Flux.fromArray(arr).subscribe(t -> System.out.println(t));
	}
	
	public static void main1(String[] args) {
		String configInfo =
				"       routes: \r\n" + 
				"       - id: route-provider-without-security\r\n" + 
				"         uri: lb://05samples-discovery-nacos-provider-without-security #使用注册中心的实例lb=loadbalance\r\n" + 
				"         predicates:\r\n" + 
				"         - name: Path\r\n"
			  + "           args: \r\n"
			  + "            pattern: /05samples-discovery-nacos-provider-without-security/**\r\n"+
				"         filters: " 
				;
		
		System.out.println(configInfo);
		Yaml yaml = new Yaml();//yaml 解析器
		Map<String, Object> properties = yaml.load(configInfo);
		JSONObject jsonObject= new JSONObject(properties);
		System.out.println(jsonObject.toJSONString());		 
		 
//		 try {
//			 DynamicRouteDefinition demo = reader.read(DynamicRouteDefinition.class);
//		} catch (YamlException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		JSONArray carDetailArr=jsonObject.getJSONArray("routes");
		System.out.println(carDetailArr.toJSONString());
		DynamicRouteDefinition d = JSON.parseObject(jsonObject.toJSONString(), DynamicRouteDefinition.class);

		System.out.println(jsonObject);
		
	}
}
