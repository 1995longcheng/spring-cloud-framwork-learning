package com.learning.security.entity;

import java.util.List;

/**
 * 权限管理-用户实体
 * 
 * @author xiongchaoqun
 * @date 2019年8月22日
 */
public class RtUser {
	private Long userid;
	private String username;
	private String password;
	private Integer enabled;
	private List<RtRole> roles;
	
	public RtUser() {
	
	}
	public RtUser(Long userid, String username, String password, Integer enabled, List<RtRole> roles) {
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.roles = roles;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	public List<RtRole> getRoles() {
		return roles;
	}
	public void setRoles(List<RtRole> roles) {
		this.roles = roles;
	}
}
