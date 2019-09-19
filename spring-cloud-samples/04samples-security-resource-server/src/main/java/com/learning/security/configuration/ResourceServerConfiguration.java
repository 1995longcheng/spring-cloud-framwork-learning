package com.learning.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.web.client.RestTemplate;

/**
 * 安全管理-资源服务器配置【依赖common组件后请开启EnableResourceServer注解】
 * 
 * @author xiongchaoqun
 * @date 2019年7月8日
 */
@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private RemoteTokenServices remoteTokenServices;
		
	/**
	 * 不需要拦截的 路径api-security
	 */
	@Value("${security.basic.allowPath:/login*}")
	private String allowPath;
	
	@Value("${security.basic.filterPath:}")
	private String filterPath;

	/**
	 * 资源服务器相关配置
	 */
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		this.remoteTokenServices.setRestTemplate(restTemplate);
		super.configure(resources);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		String[] tmpAllowPath = allowPath == null ? new String[] { "/login" } : allowPath.split(",");
		String[] tmpFilterPath = filterPath == null || "".equals(filterPath.trim()) ? null : filterPath.split(",");

		if (tmpFilterPath == null || tmpFilterPath.length == 0) {
			http.authorizeRequests().anyRequest().permitAll();// 所有的请求不认证
		} else {
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and().requestMatchers()
					.anyRequest().and().anonymous().and()
					.authorizeRequests().antMatchers(tmpAllowPath).permitAll()// 无需认证【没有配置的不会拦截】
					.antMatchers(tmpFilterPath).authenticated(); // 配置访问权限控制，必须认证过后才可以访问

		}
	}

}
