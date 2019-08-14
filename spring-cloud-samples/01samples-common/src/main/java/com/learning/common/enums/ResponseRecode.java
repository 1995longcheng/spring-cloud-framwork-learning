package com.learning.common.enums;

/**
 * 接口参数枚举值
 * 
 * @author xuzhicheng
 *
 */
public enum ResponseRecode {

	RS_SUCCESS(1, "成功"), 
	RS_INVALID_FORMAT(2, "格式错误"), 
	RS_CMD_NOACCEPT(3, "命令不支持"), 
	RS_INVALID_PARA(4, "参数错误"), 
	RS_INFC_ERROR(5, "接口错误"), 
	RS_PERMISSION_LIMIT(6, "权限不够"), 
	RS_TIME_OUT(7, "指令超时"), 
	RS_IF_RETURN_ERROR(8, "接口返回错误"), 
	RS_LOGIN_TIMEOUT(9, "登录超时"), 
	RS_LOGIN_FALSE(10, "登录失败"), 
	RS_LOGIN_NULL(11, "用户没有登录"), 
	RS_IF_RETURN_DATA_ERROR(12, "接口返回数据解析失败"), 
	RS_OHTER(99, "其他"),
	/** 正确 **/
	SUCCESS_CODE(200, "成功"),
	/** 参数错误 **/
	PARAM_ERROR_CODE(400, "参数错误"),
	/** 限制调用 **/
	LIMIT_ERROR_CODE(401, "限制调用"),
	/** token 过期 **/
	TOKEN_TIMEOUT_CODE(402, "token 过期"),
	/** 禁止访问 **/
	NO_AUTH_CODE(403, "禁止访问"),
	/** 资源没找到 **/
	NOT_FOUND(404, "资源没找到"),
	/** 服务器错误 **/
	SERVER_ERROR_CODE(500, "服务器错误"),
	/** 服务降级中 **/
	DOWNGRADE(406, "服务降级中");

	private int recode;

	private String redesc;

	public int getRecode() {
		return recode;
	}

	public void setRecode(int recode) {
		this.recode = recode;
	}

	public String getRedesc() {
		return redesc;
	}

	public void setRedesc(String redesc) {
		this.redesc = redesc;
	}

	private ResponseRecode(int recode, String redesc) {
		this.recode = recode;
		this.redesc = redesc;
	}

}
