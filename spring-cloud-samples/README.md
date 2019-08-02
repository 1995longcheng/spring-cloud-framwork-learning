# 搭建微服务平台
包含模块：
1、服务注册中心（nacos-server）
2、应用配置中心（nacos-server）
3、安全管理中心（spring-cloud-security+OAuth2）
4、API管理中心（springfox-swagger2）
5、API网关中心（spring-cloud-gateway+ribbon+hystrix）


# spring-security【oauth2】
封装：client_credentials模式


1、安全-认证模块-AbstractAuthenticationProcessingFilter：
a、基本请求认证【username+password】-UsernamePasswordAuthenticationFilter
b、获取token认证【auth/token】-ClientCredentialsTokenEndpointFilter
c、OAuth2ClientAuthenticationProcessingFilter？ 不知道此过滤器作用

2、安全-校验模块：
c、校验token认证【】-OAuth2AuthenticationProcessingFilter
