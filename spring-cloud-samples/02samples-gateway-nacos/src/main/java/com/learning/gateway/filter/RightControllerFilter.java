package com.learning.gateway.filter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import reactor.core.publisher.Mono;

/**
 * 权限控制器
 * 【使用不同用户获取的token拥有不同访问权限】
 * 
 * @author xiongchaoqun
 * @date 2019年9月16日
 */

@Component
public class RightControllerFilter implements GlobalFilter {
	private static final Logger LOG = LoggerFactory.getLogger(RightControllerFilter.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	public static final String RESPONSEDATA_FIELD_DATA = "data";
	public static final String SECURITY_APPLICATION_NAME = "03samples-security-authorization-server";
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		String token = request.getQueryParams().getFirst("access_token");
		
		if (StringUtils.isBlank(token)) {
			return chain.filter(exchange);
		}
		String req_path = request.getURI().getPath();
		String appName = req_path.substring(1,req_path.indexOf("/",1));
		
		String getUserNameUrl = "http://" + SECURITY_APPLICATION_NAME +"/" + SECURITY_APPLICATION_NAME + "/token/getUser";
		LOG.info("OAuth2令牌:{};请求路径:{}", token, getUserNameUrl);
		// 通过token获取username
		LinkedMultiValueMap<String, Object> getUserNameParam = new LinkedMultiValueMap<String, Object>();
		getUserNameParam.add("access_token", token);
		ResponseEntity<String> userNameEntity = restTemplate.postForEntity(getUserNameUrl, getUserNameParam,String.class);
		
		ServerHttpResponse response = exchange.getResponse();
		JSONObject message = new JSONObject();
		if (userNameEntity == null) {   //逻辑待调优
			LOG.error("无效OAuth2令牌：{}",token);
			message.put("status", -1);
			message.put("data", "无效OAuth2令牌：" + token);
			DataBuffer buffer = response.bufferFactory().wrap(message.toJSONString().getBytes(StandardCharsets.UTF_8));
			response.setStatusCode(HttpStatus.UNAUTHORIZED);
			response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");// 指定编码，否则在浏览器中会中文乱码
			return response.writeWith(Mono.just(buffer));
		}
		
		String username = JSON.parseObject(userNameEntity.getBody()).getJSONObject(RESPONSEDATA_FIELD_DATA).getString("username");
		LinkedMultiValueMap<String, Object> getUserParam = new LinkedMultiValueMap<String, Object>();
		getUserParam.add("username", username);
		String getUserDetailUrl = "http://" + SECURITY_APPLICATION_NAME + "/" + SECURITY_APPLICATION_NAME + "/user/getUserByName";
		LOG.info("用户名称:{};请求路径:{}", username, getUserDetailUrl);
		// 通过username获取user
		ResponseEntity<String> userEntity = restTemplate.postForEntity(getUserDetailUrl, getUserParam,String.class);
			
		List<String> urls = new ArrayList<String>();
		JSONArray roles = JSON.parseObject(userEntity.getBody()).getJSONObject(RESPONSEDATA_FIELD_DATA).getJSONArray("roles");
		for (int i = 0; i < roles.size(); i++) {
			JSONObject role = roles.getJSONObject(i);
			JSONArray rights = role.getJSONArray("rights");
			for (int j = 0; j < rights.size(); j++) {
				String url = rights.getJSONObject(j).getString("url");
				urls.add(url);
			}
		}
		if (!urls.contains(appName)) {
			LOG.error("OAuth2令牌：{};所属用户:{};无权限访问应用:{}",token,username,appName);
			message.put("status", -1);
			message.put("data", "无权限访问应用:" + appName);
			DataBuffer buffer = response.bufferFactory().wrap(message.toJSONString().getBytes(StandardCharsets.UTF_8));
			response.setStatusCode(HttpStatus.UNAUTHORIZED);
			response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");// 指定编码，否则在浏览器中会中文乱码
			return response.writeWith(Mono.just(buffer));
		}
		return chain.filter(exchange);
	}
	
}
