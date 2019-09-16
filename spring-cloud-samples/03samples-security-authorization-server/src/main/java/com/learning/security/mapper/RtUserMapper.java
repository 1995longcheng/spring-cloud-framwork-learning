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
import com.learning.security.entity.RtUser;

/**
 * 用户持久类
 * @author xiongchaoqun
 * @date 2019年9月12日
 */
public interface RtUserMapper extends BaseMapper<RtUser>{
	
	/**
	 * 获取用户新信息列表
	 * @param username
	 * @return
	 */
	@Select(value="select user_id,user_name,password,enable from rt_users where user_name = #{username}")
	@Results(value= {
			@Result(property="userid",column="user_id",javaType=Long.class),
			@Result(property="username",column="user_name",javaType=String.class),
			@Result(property="password",column="password",javaType=String.class),
			@Result(property="enabled",column="enable",javaType=Integer.class)})
	RtUser getUserByName(@Param("username")String username);
	
	/**
	 * 获取用户新信息列表
	 * @param username
	 * @return
	 */
	@Select(value="select user_id,user_name,password,enable from rt_users where user_id = #{userid}")
	@Results(value= {
			@Result(property="userid",column="user_id",javaType=Long.class),
			@Result(property="username",column="user_name",javaType=String.class),
			@Result(property="password",column="password",javaType=String.class),
			@Result(property="enabled",column="enable",javaType=Integer.class)})
	RtUser getUserById(@Param("userid")Long userid);
	
	
	/**
	 * 获取用户新信息列表
	 * @param username
	 * @return
	 */
	@Select(value="select user_id,user_name,password,enable from rt_users")
	@Results(value= {
			@Result(property="userid",column="user_id",javaType=Long.class),
			@Result(property="username",column="user_name",javaType=String.class),
			@Result(property="password",column="password",javaType=String.class),
			@Result(property="enabled",column="enable",javaType=Integer.class)})
	Page<RtUser> getUserPageList();
	
	/**
	 *  插入用户信息
	 * @param user
	 */
	@Insert(value="insert into rt_users(user_id,user_name,password,enable) values(#{userid},#{username},#{password},#{enabled})")
	void addUser(RtUser user);
	
	/**
	 * 更新用户信息
	 * @param user
	 */
	@Update(value="update rt_users set user_name= #{username},password= #{password},enable= #{enabled} where user_id = #{userid}")
	void uptUser(RtUser user);
	
	/**
	 * 通过用户ID删除用户
	 * @param userid
	 */
	@Delete(value="delete from rt_users where user_id = #{userid}")
	void delUserById(@Param("userid")Long userid);
	
	/**
	 * 获取用户所属角色
	 * 
	 * @param userid
	 * @return
	 */
	@Select("select role_id from rt_user_relate_role where user_id = #{userid}")
	List<Long> getRoleIdsByUserId(@Param("userid")Long userid);
	
	/**
	 * 添加用户与角色关联关系
	 * 
	 * @param userid
	 * @param roleid
	 */
	@Insert(value="insert into rt_user_relate_role(user_id,role_id) values(#{userid},#{roleid})")
	void addUserReRole(@Param("userid")Long userid,@Param("roleid")Long roleid);
	
	/**
	 * 删除用户与角色关联关系
	 * 
	 * @param userid
	 * @param roleid
	 */
	@Delete(value="delete from rt_user_relate_role where user_id = #{userid} and role_id = #{roleid}")
	void delUserReRole(@Param("userid")Long userid,@Param("roleid")Long roleid);
}
