package com.learning.security.configuration.userdetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import com.learning.security.entity.RtUser;
import com.learning.security.service.RtUserService;

@Service
public class UserDetailService implements UserDetailsService {
	@Autowired
	private RtUserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserDetails userDetails = null;
		try {
			RtUser user = userService.getUserByName(username);
			if (user.getEnabled() == 0) {
				userDetails = new UserDetail(null, username, PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassword()), true, true, true, false);
			} else if (user.getEnabled() == 1){
				userDetails = new UserDetail(null, username, PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassword()), true, true, true, true);
			}
		} catch (Exception e) {
			throw new UsernameNotFoundException("用户名：" + username + "【用户不存在】");
		}
		return userDetails;
	}
}
