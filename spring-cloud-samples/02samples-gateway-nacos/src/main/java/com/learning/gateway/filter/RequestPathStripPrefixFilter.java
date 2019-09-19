package com.learning.gateway.filter;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.learning.gateway.swagger2.Swagger2Provider;

import reactor.core.publisher.Mono;

/**
 * 由于所有请求只有添加请求前缀【/spring.application.name/】才会被正确转发
 * 1、先保证请求正确转发【可以找到对应应用】
 * 2、真正执行请求前，去掉/spring.application.name，请求前缀
 * 
 * @author xiongchaoqun
 * @date 2019年7月25日
 */
@Component
public class RequestPathStripPrefixFilter implements GlobalFilter, Ordered {
	private static final Logger LOG = LoggerFactory.getLogger(RequestPathStripPrefixFilter.class);
	public static final int SWAGGER_RESOURCE_FILTER_ORDER = 10101;// 在负载均衡过滤器【10100】后运行
		
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		URI url = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
		URI new_url = null;
		try {
			String req_path = url.getPath();
			// 如果是获取swagger-api请求或者是token相关请求，则去掉第一个请求路径
			if (req_path.endsWith(Swagger2Provider.API_URI)) {
				new_url = new URI(url.getScheme() + "://" + url.getAuthority() + Swagger2Provider.API_URI);//scheme=http authority=ip:端口
			} else {
				return chain.filter(exchange);
			}
		} catch (Exception e) {
			LOG.error("",e);
		}
		LOG.trace("new_url：" + new_url);
		exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, new_url);
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return SWAGGER_RESOURCE_FILTER_ORDER;
	}
}
