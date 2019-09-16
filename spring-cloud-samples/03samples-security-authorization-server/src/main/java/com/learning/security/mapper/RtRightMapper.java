package com.learning.security.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.learning.security.entity.RtRight;

public interface RtRightMapper extends BaseMapper<RtRight>{
	
	@Select(value="select right_id,right_name,right_type,url,insert_time from rt_rights where right_id = #{rightid}")
	@Results(value= {
			@Result(property="rightid",column="right_id",javaType=Long.class),
			@Result(property="rightname",column="right_name",javaType=String.class),
			@Result(property="righttype",column="right_type",javaType=String.class),
			@Result(property="url",column="url",javaType=String.class),
			@Result(property="inserttime",column="insert_time",javaType=Long.class)})
	RtRight getRightById(@Param("rightid")Long rightid);
	
	@Select(value="select right_id,right_name,right_type,url,insert_time from rt_rights where right_name = #{rightname}")
	@Results(value= {
			@Result(property="rightid",column="right_id",javaType=Long.class),
			@Result(property="rightname",column="right_name",javaType=String.class),
			@Result(property="righttype",column="right_type",javaType=String.class),
			@Result(property="url",column="url",javaType=String.class),
			@Result(property="inserttime",column="insert_time",javaType=Long.class)})
	RtRight getRightByName(@Param("rightname")String rightname);
	
	@Select(value="select right_id,right_name,right_type,url,insert_time from rt_rights")
	@Results(value= {
			@Result(property="rightid",column="right_id",javaType=Long.class),
			@Result(property="rightname",column="right_name",javaType=String.class),
			@Result(property="righttype",column="right_type",javaType=String.class),
			@Result(property="url",column="url",javaType=String.class),
			@Result(property="inserttime",column="insert_time",javaType=Long.class)})
	Page<RtRight> getRightPageList();
	
	@Insert(value="insert into rt_rights(right_id,right_name,right_type,url,insert_time) values(#{rightid},#{rightname},#{righttype},#{url},#{inserttime})")
	void addRight(RtRight right);
	
	@Update(value="update rt_rights set right_name=#{rightname},right_type=#{righttype},url=#{url},insert_time=#{inserttime} where right_id=#{rightid}")
	void uptRight(RtRight right);
	
	@Delete(value="delete from rt_rights where right_id = #{rightid}")
	void delRightById(@Param("rightid")Long rightid);
}
