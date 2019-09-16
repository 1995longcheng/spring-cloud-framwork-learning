package com.learning.security.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.learning.common.BaseController;
import com.learning.common.enums.ResponseData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Token相关端点【token获取+token刷新】
 * 
 * @author xiongchaoqun
 * @date 2019年7月19日
 */
@Api("安全模块-令牌管理中心")
@RestController
public class TokenController extends BaseController {

	@Value("${spring.application.name}")
	private String applicationName;

	/**
	 * 用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围
	 */
	@Value("${security.oauth2.client.scope}")
	private String[] clientScope;
	/**
	 * 配置此客户端允许的授权类型 password 密码类型 client_credentials 客户端类型 authorization_code：授权码类型
	 * implicit：隐式授权类型（简化模式） refresh_token：通过以上授权获得的刷新令牌来获取新的令牌）
	 */
	@Value("${security.oauth2.client.authorizedGrantTypes}")
	private String[] authorizedGrantTypes;

	@Autowired
	private AuthorizationServerEndpointsConfiguration authorizationServerEndpointsConfiguration;// 获取认证服务器端点配置

	public static final String PASSWORD_GRANT_TYPE = "password";
	public static final String CLIENT_GRANT_TYPE = "client_credentials";

	@ApiOperation(value = "登录获取token接口，由于需要传递平台帐号密码，此接口只允许服务端调用，不允许在界面直接调用！") // 使用该注解描述接口方法信息																	// https://blog.csdn.net/u014231523/article/details/76522486
	@ApiImplicitParams({ // client_credentials, refresh_token
			@ApiImplicitParam(name = "grant_type", value = "授权类型【password、client_credentials】", defaultValue = "password", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "scope", value = "权限范围【默认select】", defaultValue = "select", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "client_id", value = "客户端id", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "client_secret", value = "客户端密码", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "username", value = "用户名【password时，必填】", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "password", value = "用户密码【password时，必填】", dataType = "String", paramType = "query") })
	@ApiResponses({@ApiResponse(code = 200, message = "{access_token:令牌,token_type:令牌类型,该值大小写不敏感,expires_in:令牌有效时间，单位为秒}") })
	// 使用该注解描述方法参数信息，此处需要注意的是paramType参数，需要配置成query，否则在UI中访问接口方法时，会报错
	// 注：paramType表示参数的类型，可选的值为"path","body","query","header","form"
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/token/getToken")
	public Object getToken(HttpServletRequest request) {
		Map<String, Object> response = new HashMap<String, Object>();// 返回对象
		String grant_type = request.getParameter("grant_type");
		String scope = request.getParameter("scope");
		String client_id = request.getParameter("client_id");
		String client_secret = request.getParameter("client_secret");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
		if (!Arrays.asList(authorizedGrantTypes).contains(grant_type)) {// 包含此种类型
			response.put("error", "授权服务器无此授权类型【" + grant_type + "】");
			return response;
		}
		// 包含此种类型
		if (!Arrays.asList(clientScope).contains(scope)) {
			response.put("error", "授权服务器无此权限【" + scope + "】");
			return response;
		}
		multiValueMap.set("client_id", client_id);
		multiValueMap.set("client_secret", client_secret);
		multiValueMap.set("grant_type", grant_type);
		multiValueMap.set("scope", scope);
		if (grant_type.equals(PASSWORD_GRANT_TYPE)) {
			multiValueMap.set("username", username);
			multiValueMap.set("password", password);
		}
		ResponseEntity<OAuth2AccessToken> entity = null;
		try {
			entity = restTemplate.postForEntity("http://" + applicationName + "/oauth/token", multiValueMap,OAuth2AccessToken.class);
		} catch (Exception e) {
			response.put("error", "server_error");
			response.put("error_description", e.getMessage());
			return response;
		}
		response.put("access_token", entity.getBody().getValue());
		response.put("expires_in", entity.getBody().getExpiresIn());
		response.put("token_type", entity.getBody().getTokenType());
		return response;
	}
	
	@ApiOperation(value = "通过token获取所属用户名")
	@ApiImplicitParams({@ApiImplicitParam(name = "access_token", value = "access_token", required = true, dataType = "String", paramType = "query"), })
	@ApiResponses({ @ApiResponse(code = 200, message = "检查通过") })
	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "/token/getUser")
	public Object getUser(HttpServletRequest request) {
		String accessToken = request.getParameter("access_token");
		LinkedMultiValueMap<String, Object> response = new LinkedMultiValueMap<String, Object>();
		ResourceServerTokenServices resourceServerTokenServices = authorizationServerEndpointsConfiguration.getEndpointsConfigurer().getResourceServerTokenServices();
		try {
			OAuth2Authentication auth2Authentication = resourceServerTokenServices.loadAuthentication(accessToken);
			OAuth2Request oAuth2Request = auth2Authentication.getOAuth2Request();//获取OAuth2请求【获取token时请求参数】
			String username = oAuth2Request.getRequestParameters().get("username");
			response.set("access_token", accessToken);
			response.set("username", username);
		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
		return ResponseData.ok(response);
	}
	

	@ApiOperation(value = "检查token是否有效，并刷新token有效时间")
	@ApiImplicitParams({@ApiImplicitParam(name = "access_token", value = "access_token", required = true, dataType = "String", paramType = "query"), })
	@ApiResponses({ @ApiResponse(code = 200, message = "检查通过") })
	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "/token/checkToken")
	public Object checkToken(HttpServletRequest request) {
		LinkedMultiValueMap<String, Object> response = new LinkedMultiValueMap<String, Object>();
		ResourceServerTokenServices resourceServerTokenServices = authorizationServerEndpointsConfiguration.getEndpointsConfigurer().getResourceServerTokenServices();
		String accessToken = request.getParameter("access_token");
		response.add("access_token", accessToken);
		try {
			DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) resourceServerTokenServices.readAccessToken(accessToken);
			if (token == null) {
				response.set("result", "token无效");
			}else if(token.isExpired()) {
				response.set("result", "token过期");
			}else {
				response.set("result", "token有效");
			}
		} catch (Exception e) {
			return ResponseData.fail(e.getMessage());
		}
		return response;
	}
}
