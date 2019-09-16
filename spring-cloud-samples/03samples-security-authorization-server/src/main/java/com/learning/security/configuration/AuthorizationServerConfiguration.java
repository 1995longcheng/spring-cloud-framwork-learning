package com.learning.security.configuration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * spring-boot 已经提供了自动配置类：OAuth2AuthorizationServerConfiguration
 * HttpSecurity  请求 说明
 * 
 * 
 * @author xiongchaoqun
 * @date 2019年7月22日
 */
@Configuration
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
	
	@Value(value="${security.oauth2.authorization.token-key-access}")
	private String tokenKeyAccess;
	
	@Value(value="${security.oauth2.authorization.check-token-access}")
	private String checkTokenAccess;
	
	@Autowired
	private AuthenticationManager authenticationManager;//org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter$AuthenticationManagerDelegator@448cdb47

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private OAuth2ClientProperties oAuth2ClientProperties;
	
    /**
     * 用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围
     */
    @Value("${security.oauth2.client.scope}")
    private String[] clientScope;
    /**
     * 配置此客户端允许的授权类型  password 密码类型 client_credentials 客户端类型 authorization_code：授权码类型 implicit：隐式授权类型（简化模式） refresh_token：通过以上授权获得的刷新令牌来获取新的令牌）
     */
    @Value("${security.oauth2.client.authorizedGrantTypes}")
    private String[] authorizedGrantTypes;

    
/*********************************Redis Token存储方式*************************************/
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    
	@Bean
	public TokenStore redisTokenStore() {
		return new RedisTokenStore(redisConnectionFactory);
	}
    
/*********************************JWT Token存储方式*************************************/
//	@Value(value="${jwt.signKey}")
//	private String signKey;
    
//	@Bean
//	public TokenStore jwtTokenStore() {
//		return new JwtTokenStore(accessTokenConverter());
//	}
//	@Bean
//	public JwtAccessTokenConverter accessTokenConverter() {
//		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//		converter.setSigningKey(signKey);
//		return converter;
//	}
/*********************************JWT Token存储方式*************************************/


    
//	/**
//	 * AuthorizationServerSecurityConfigurer：用来配置令牌端点的安全约束。
//	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		/* 配置token获取合验证时的策略   . permitAll /isAuthenticated /denyAll*/
	    security.tokenKeyAccess(tokenKeyAccess).checkTokenAccess(checkTokenAccess).allowFormAuthenticationForClients();
	}
//
	/**
	 * ClientDetailsServiceConfigurer：定义客户详细信息服务的配置器。客户端详细信息可以被初始化，或者您可以直接引用一个现有的存储。（client_id ，client_secret，redirect_uri 等配置信息）
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		String clientId = oAuth2ClientProperties.getClientId();
        String clientSecret = oAuth2ClientProperties.getClientSecret();
        
        if (!StringUtils.isEmpty(clientId) && !StringUtils.isEmpty(clientSecret)) {
            String[] clientIds = clientId.split(",");
            String[] clientSecrets = clientSecret.split(",");
            //TODO  clients.inMemory()
            InMemoryClientDetailsServiceBuilder clientDetailsServiceBuilder = clients.inMemory();
            for (int i=0; i<clientIds.length; i++) {
                clientDetailsServiceBuilder.withClient(clientIds[i].trim())
                        .authorizedGrantTypes(authorizedGrantTypes)
                        .scopes(clientScope)
                        .secret(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(clientSecrets[i]));
//						.accessTokenValiditySeconds(1200)    //token有效期
//                      .refreshTokenValiditySeconds(50000)	//refresh_token有效期
            }
        }
	}

	/**
	 * AuthorizationServerEndpointsConfigurer：用来配置授权以及令牌的访问端点和令牌服务（比如：配置令牌的签名与存储方式）
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager);// 指定认证管理器
		endpoints.tokenStore(redisTokenStore());// 指定token存储位置
//		endpoints.accessTokenConverter(accessTokenConverter());// token生成方式
		endpoints.userDetailsService(userDetailsService);
	}

}
