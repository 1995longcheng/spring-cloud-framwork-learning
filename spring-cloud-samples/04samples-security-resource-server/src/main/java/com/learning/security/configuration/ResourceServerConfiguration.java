package com.learning.security.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 安全管理-资源服务器配置【依赖common组件后请开启EnableResourceServer注解】
 * 
 * @author xiongchaoqun
 * @date 2019年7月8日
 */
@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	
	//Nacos Config 注入
	@Value(value="${jwt.signKey}")
	private String signKey;

	/**
	 * 1、使用JWT进行token校验 2、还可以使用RemoteTokenServices远程调用鉴权服务器
	 * 
	 * @return
	 */
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(signKey);
		return converter;
	}

	/**
	 * 资源服务器相关配置
	 */
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore());
	}

	/**
	 * HTTP请求相关配置
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		super.configure(http);
	}

}
