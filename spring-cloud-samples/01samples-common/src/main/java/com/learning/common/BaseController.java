package com.learning.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * Controller统一根路径
 * 
 * @author xiongchaoqun
 * @date 2019年7月19日
 */
@RequestMapping(value = "${spring.application.name}")
@ApiResponses({ 
	@ApiResponse(code = 200, message = "OK", response = String.class), 
	@ApiResponse(code = 401, message = "Unauthorized", response = String.class), 
	@ApiResponse(code = 403, message = "Forbidden", response = String.class), 
	@ApiResponse(code = 404, message = "Not Found", response = String.class), 
	@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
public class BaseController {

	@Autowired
	protected RestTemplate restTemplate;

}
