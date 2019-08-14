package com.learning.common.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 方法统一返回对象
 * {
 *     code: 200,
 *     message: 状态描述
 *     data： 实际业务返回值
 * }
 *
 */
@ApiModel(value = "方法统一返回对象")
public class ResponseData {
	/**
	 * 状态码  ==200（ResponseRecode.SUCCESS_CODE.getRecode()） 时 成功
     */
	@ApiModelProperty(value = "状态码")
	private int code = 200;
	/**
	 * 状态描述信息
	 */
	@ApiModelProperty(value = "状态描述信息")
	private String message = "";
	/**
	 * 实际的业务返回值
	 */
	@ApiModelProperty(value = "实际的业务返回值")
	private Object data;
	
	public static ResponseData ok(Object data) {
		return new ResponseData(data);
	}
	
	public static ResponseData fail() {
		return new ResponseData(null, ResponseRecode.PARAM_ERROR_CODE.getRecode());
	}
	
	public static ResponseData fail(String message) {
		return new ResponseData(message, ResponseRecode.PARAM_ERROR_CODE.getRecode());
	}
	
	public ResponseData(Object data) {
		super();
		this.data = data;
	}
	
	public ResponseData(String message) {
		super();
		this.message = message;
	}
	
	public ResponseData(String message, int code) {
		super();
		this.message = message;
		this.code = code;
	}
	
	public ResponseData() {
		super();
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
