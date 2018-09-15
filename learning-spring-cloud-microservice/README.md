# learning-spring-cloud
the project of learning of spring-cloud

----------------------------------------------------------------------------

项目名称说明：
默认使用eureka集群，名称中存在-without-eureka表示未使用eureka集群

----------------------------------------------------------------------------
功能场景：
--eureka服务注册中心-单机版、eureka服务注册中心-集群版

项目名称：
learning-spring-cloud-microservice-eureka
learning-spring-cloud-microservice-eureka-ha

核心要点：
spring-boot-v2.0及以上版本
使用组件spring-boot-starter-security，需要重写以下部分代码
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        super.configure(http);
    }
}

主要配置说明：
eureka:
  client:
    service-url:
      defaultZone: http://admin:admin@node2:3002/eureka/ #eueka集群配置有用
  instance:
    hostname: node1   #当前机器hostname为node1
    lease-renewal-interval-in-seconds: 5  #设置客户端向eureka服务器发送心跳间隔
    lease-expiration-duration-in-seconds: 10 #设置租期超时时间
    
----------------------------------------------------------------------------


----------------------------------------------------------------------------
功能场景：
--spring-boot-restful接口启动与调用






