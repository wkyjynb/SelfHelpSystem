package com.t226.service.user;

import java.util.List;

import javax.annotation.Resource;

import com.t226.pojo.Detection;
import org.springframework.stereotype.Service;

import com.t226.dao.user.UserMapper;
import com.t226.pojo.User;
@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserMapper userMapper;

	/**
	 * affair开头的方法名都是进行事务处理的
	 * @param user
	 * @return
	 */

	//注册用户信息
	@Override
	public void affairAddUser(User user) {
		userMapper.affairAddUser(user);
	}

	//用户登录
	@Override
	public User userLogin(String phone, String password) {
		User user=userMapper.userLogin(phone);
		if(user!=null){
			if(user.getPassword().equals(password)){
				return user;
			}
		}
		return null;
	}
	public User dologin(String userCode,String userPassword) {
		User user=userMapper.dologin(userCode);
		System.out.println(user!=null);
		if(user!=null){
			if(user.getPassword().equals(userPassword)){
				return user;
			}
		}
		return null;
	}
	@Override
	public boolean doInsert(Detection detection) {
		return userMapper.doInsert(detection);
	}
	@Override
	public List<Detection> finSelect() {
		return userMapper.finSelect();
	}
	@Override
	public List<Object> finCount(String date, String date1) {
		return userMapper.finCount(date, date1);
	}
}
