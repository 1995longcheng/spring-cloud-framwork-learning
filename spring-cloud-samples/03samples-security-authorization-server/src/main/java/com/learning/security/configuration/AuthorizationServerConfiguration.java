package com.learning.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	//Nacos Config 注入
	@Value(value="${jwt.signKey}")
	private String signKey;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("123");
		return converter;
	}

	/**
	 * AuthorizationServerSecurityConfigurer：用来配置令牌端点的安全约束。
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		/* 配置token获取合验证时的策略 */
	    security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()").allowFormAuthenticationForClients();
	}

	/**
	 * ClientDetailsServiceConfigurer：定义客户详细信息服务的配置器。客户端详细信息可以被初始化，或者您可以直接引用一个现有的存储。（client_id ，client_secret，redirect_uri 等配置信息）
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
        .withClient("client1")//用于标识用户ID
        .authorizedGrantTypes("authorization_code","refresh_token")//授权方式
        .scopes("test")//授权范围
        .secret(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123456"));
		//客户端安全码,secret密码配置从 Spring Security 5.0开始必须以 {bcrypt}+加密后的密码 这种格式填写;
	}

	/**
	 * AuthorizationServerEndpointsConfigurer：用来配置授权以及令牌的访问端点和令牌服务（比如：配置令牌的签名与存储方式）
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager);// 指定认证管理器
		endpoints.tokenStore(tokenStore());// 指定token存储位置
		endpoints.accessTokenConverter(accessTokenConverter());// token生成方式
		endpoints.userDetailsService(userDetailsService);
	}

}
