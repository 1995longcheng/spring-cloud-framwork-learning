package com.learning.gateway.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;


/**
 * swagger-ui【内置请求URL】
 * 
 * API端点
 * 
 * @author xiongchaoqun
 * @date 2019年7月15日
 */
@RestController
@RequestMapping("/swagger-resources")
public class Swagger2Controller {

	@Autowired(required = false)
	private SecurityConfiguration securityConfiguration;
	
	@Autowired(required = false)
	private UiConfiguration uiConfiguration;
	
	@Autowired
	private SwaggerResourcesProvider swaggerResources;//gateway将注册到网关中心上的API收集起来

	@GetMapping("/configuration/security")
	public Mono<ResponseEntity<SecurityConfiguration>> securityConfiguration() {
		return Mono.just(new ResponseEntity<>(Optional.ofNullable(securityConfiguration).orElse(SecurityConfigurationBuilder.builder().build()),HttpStatus.OK));
	}

	@GetMapping("/configuration/ui")
	public Mono<ResponseEntity<UiConfiguration>> uiConfiguration() {
		return Mono.just(new ResponseEntity<>(Optional.ofNullable(uiConfiguration).orElse(UiConfigurationBuilder.builder().build()), HttpStatus.OK));
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("")
	public Mono<ResponseEntity> swaggerResources() {
		return Mono.just((new ResponseEntity<>(swaggerResources.get(), HttpStatus.OK)));
	}
}
