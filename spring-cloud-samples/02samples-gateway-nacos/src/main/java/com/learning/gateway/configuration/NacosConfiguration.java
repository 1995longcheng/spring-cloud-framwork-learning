package com.learning.gateway.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.alibaba.nacos.NacosConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 利用Nacos-Config实现动态路由方式
 * @author xiongchaoqun
 * @date 2019年8月1日
 */
@Configuration
public class NacosConfiguration {

	//动态路由配置data-id
//	@Value("${spring.cloud.nacos.config.gateway-data-id}")
//	private String gatewayDataId;
	
//	@Bean
//	public DynamicRouteConfigListener dynamicRouteConfigListener(NacosConfigProperties nacosConfigProperties) {
//		return new DynamicRouteConfigListener(nacosConfigProperties.configServiceInstance(), gatewayDataId);
//	}
}
