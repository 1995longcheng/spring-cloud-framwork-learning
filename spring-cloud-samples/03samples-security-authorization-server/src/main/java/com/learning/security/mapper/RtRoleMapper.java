package com.learning.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.learning.security.entity.RtRole;

public interface RtRoleMapper extends BaseMapper<RtRole>{
	
	@Select(value="select role_id,role_name,enable from rt_roles where role_id = #{roleid}")
	@Results(value= {
			@Result(property="roleid",column="role_id",javaType=Long.class),
			@Result(property="rolename",column="role_name",javaType=String.class),
			@Result(property="enabled",column="enable",javaType=Integer.class)})
	RtRole getRoleById(@Param("roleid")Long roleid);
	
	@Select(value="select role_id,role_name,enable from rt_roles where role_name = #{rolename}")
	@Results(value= {
			@Result(property="roleid",column="role_id",javaType=Long.class),
			@Result(property="rolename",column="role_name",javaType=String.class),
			@Result(property="enabled",column="enable",javaType=Integer.class)})
	RtRole getRoleByName(@Param("rolename")String rolename);
	
	@Select(value="select role_id,role_name,enable from rt_roles")
	@Results(value= {
			@Result(property="roleid",column="role_id",javaType=Long.class),
			@Result(property="rolename",column="role_name",javaType=String.class),
			@Result(property="enabled",column="enable",javaType=Integer.class)})
	Page<RtRole> getRolePageList();
	
	@Insert(value="insert rt_roles(role_id,role_name,enable) values(#{roleid},#{rolename},#{enabled})")
	void addRole(RtRole role);
	
	@Update(value="update rt_roles set role_name= #{rolename},enable= #{enabled} where role_id = #{roleid}")
	void uptRole(RtRole role);
	
	@Delete(value="delete from rt_roles where role_id = #{roleid}")
	void delRoleById(@Param("roleid")Long roleid);
	
	/**
	 * 获取角色对应的权限
	 * 
	 * @param roleid
	 * @return
	 */
	@Select("select right_id from rt_role_relate_right where role_id = #{roleid}")
	List<Long> getRightIdsById(@Param("roleid")Long roleid);
	
	/**
	 * 添加用户与角色关联关系
	 * 
	 * @param userid
	 * @param roleid
	 */
	@Insert(value="insert into rt_role_relate_right(role_id,right_id) values(#{roleid},#{rightid})")
	void addRoleReRight(@Param("roleid")Long roleid,@Param("rightid")Long rightid);
	
	/**
	 * 删除用户与角色关联关系
	 * 
	 * @param roleid
	 * @param rightid
	 */
	@Delete(value="delete from rt_role_relate_right where role_id = #{roleid} and right_id = #{rightid}")
	void delRoleReRight(@Param("roleid")Long roleid,@Param("rightid")Long rightid);
}
