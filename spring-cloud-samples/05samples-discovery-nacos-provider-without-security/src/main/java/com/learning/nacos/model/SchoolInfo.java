package com.learning.nacos.model;

/**
 * 学校信息
 * @author xiongchaoqun
 * @date 2019年8月28日
 */
public class SchoolInfo {
	private Long schoolId;
	private String schoolName;
	private String schoolAge;
	private String schoolAddr;
	private String schoolSize;
	
	public Long getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getSchoolAge() {
		return schoolAge;
	}
	public void setSchoolAge(String schoolAge) {
		this.schoolAge = schoolAge;
	}
	public String getSchoolAddr() {
		return schoolAddr;
	}
	public void setSchoolAddr(String schoolAddr) {
		this.schoolAddr = schoolAddr;
	}
	public String getSchoolSize() {
		return schoolSize;
	}
	public void setSchoolSize(String schoolSize) {
		this.schoolSize = schoolSize;
	}
}
