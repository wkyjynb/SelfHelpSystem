package com.t226.service.user;

import java.util.List;

import javax.annotation.Resource;

import com.mysql.jdbc.StringUtils;
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

	//进行身份认证
	@Override
	public int addIdentity(User user) {
		return userMapper.addIdentity(user);
	}


	//微信登录或注册
	@Override
	public User wxUser(String wxId) {
		if(!StringUtils.isNullOrEmpty(wxId)){
			User user=userMapper.wxUser(wxId);
			if(user==null){
				userMapper.addWxUser(wxId);//注册
				return userMapper.wxUser(wxId);//注册完后在进行查询
			}else {
				return user;
			}
		}else{
			return null;
		}


	}

	//微信绑定
	@Override
	public int wxBind(String wxId, int userId) {
		return userMapper.wxBind(wxId,userId);
	}

	//充值
	@Override
	public int updateMoney(int money, int id) {
		return userMapper.updateMoney(money,id);
	}


}
