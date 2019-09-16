package com.learning.security.entity;

import java.util.List;

/**
 * 角色对象
 * @author xiongchaoqun
 * @date 2019年9月12日
 */
public class RtRole {
	private Long roleid;
	private String rolename;
	private Integer enabled;
	private List<RtRight> rights;

	public RtRole() {
		super();
	}
	public Long getRoleid() {
		return roleid;
	}
	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	public List<RtRight> getRights() {
		return rights;
	}
	public void setRights(List<RtRight> rights) {
		this.rights = rights;
	}
	public RtRole(Long roleid, String rolename, Integer enabled, List<RtRight> rights) {
		super();
		this.roleid = roleid;
		this.rolename = rolename;
		this.enabled = enabled;
		this.rights = rights;
	}
}
