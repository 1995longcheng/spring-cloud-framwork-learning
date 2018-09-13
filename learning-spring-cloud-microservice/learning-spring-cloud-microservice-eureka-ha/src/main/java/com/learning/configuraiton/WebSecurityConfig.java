package com.learning.configuraiton;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 解决Eureka客户端无法连接至服务端的问题。（spring-boot2.0以上存在的问题）
 * 
 * 1、在 Eureka Server 项目中，增加存放配置的专用包目录；
 * 2、添加一个继承 WebSecurityConfigurerAdapter 的类；
 * 3、在类上添加 @EnableWebSecurity 注解；
 * 4、覆盖父类的 configure(HttpSecurity http) 方法，关闭掉 csrf，至此大工告成。
 * @author xiongchaoqun
 * @date 2018年8月26日
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        super.configure(http);
    }
}