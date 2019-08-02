package com.learning.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 路断器熔断-调用
 * 
 * @author xiongchaoqun
 * @date 2019年8月1日
 */
@RestController
public class FallbackController {

	@RequestMapping(value = "/fallback")
	public String fallback() {
		return "服务不可用";
	}
}
