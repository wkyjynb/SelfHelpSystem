package com.t226.dao.user;

import com.t226.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
	void affairAddUser(User user);//添加用户
	User userLogin(@Param("phone") String phone);//用户登录
}
