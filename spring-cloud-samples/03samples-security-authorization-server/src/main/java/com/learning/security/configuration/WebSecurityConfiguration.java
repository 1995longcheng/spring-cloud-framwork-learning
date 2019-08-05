package com.learning.security.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity // 开启权限验证
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Value("${spring.application.name}")
	private String applicationName;

	@Value("${spring.security.user.name}")
	private String username;

	@Value("${spring.security.user.password}")
	private String password;

	@Value("${spring.security.user.authorities}")
	private String[] authorities;

	/**
	 * 配置这个bean会在做AuthorizationServerConfigurer配置的时候使用
	 * 
	 * @return
	 * @throws Exception
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * 配置用户
	 * 使用内存中的用户，实际项目中，一般使用的是数据库保存用户，具体的实现类可以使用JdbcDaoImpl或者JdbcUserDetailsManager
	 * 
	 * @return
	 */
	@Bean
	@Override
	protected UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername(username)
				.password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password))
				.authorities(authorities).build());
		return manager;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		// super.configure(http); //需要关闭这个，否则以下配置均为无效
		http.csrf().disable()//不关闭的话，post请求会直接返回403错误
		.authorizeRequests().antMatchers("/" + applicationName + "/token/**").access("permitAll")// token相关端点无需认证【没有配置的不会拦截】 优先级高于
		.antMatchers("/" + applicationName + "/**").authenticated(); // 配置访问权限控制，必须认证过后才可以访问
	}
}
