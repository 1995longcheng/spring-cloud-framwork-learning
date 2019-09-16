package com.learning.security.entity;

/**
 * 权限对象
 * @author xiongchaoqun
 * @date 2019年9月12日
 */
public class RtRight {
	private Long rightid;
	private String rightname;
	private String righttype;
	private String url;
	private Long inserttime;

	public RtRight() {
		super();
	}
	public Long getRightid() {
		return rightid;
	}
	public void setRightid(Long rightid) {
		this.rightid = rightid;
	}
	public String getRightname() {
		return rightname;
	}
	public void setRightname(String rightname) {
		this.rightname = rightname;
	}
	public String getRighttype() {
		return righttype;
	}
	public void setRighttype(String righttype) {
		this.righttype = righttype;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getInserttime() {
		return inserttime;
	}
	public void setInserttime(Long inserttime) {
		this.inserttime = inserttime;
	}
	public RtRight(Long rightid, String rightname, String righttype, String url, Long inserttime) {
		super();
		this.rightid = rightid;
		this.rightname = rightname;
		this.righttype = righttype;
		this.url = url;
		this.inserttime = inserttime;
	}
}
