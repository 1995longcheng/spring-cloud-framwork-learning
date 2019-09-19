package com.learning.gateway.swagger2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;


/**
 * 将注册到网关上面的服务API统一
 * 
 * @author xiongchaoqun
 * @date 2019年7月15日
 */
@Component
@Primary
public class Swagger2Provider implements SwaggerResourcesProvider {
	public static final String API_URI = "/v2/api-docs"; // swagger-api端点【jar包内置端点/依赖了swagger-api】【收集使用swagger相关注解的信息】
	
	@Autowired
    private RouteDefinitionLocator routeDefinitionLocator;//网关路由配置
 
    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        routeDefinitionLocator.getRouteDefinitions().subscribe(routeDefinition -> routeDefinition.getPredicates().stream()
        		.filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
        		.forEach(predicateDefinition -> resources.add(swaggerResource(routeDefinition.getId(),predicateDefinition.getArgs().get("_genkey_0").replace("/**", API_URI)))));
        return resources;
    }
 
    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
