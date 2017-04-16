package com.store.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import com.store.dao.UserDao;
import com.store.domain.User;
import com.store.utils.DataSourceUtils;

public class UserDaoImpl implements UserDao {
	QueryRunner qr =new QueryRunner(DataSourceUtils.getDataSource());
	public void addUser(User user) throws Exception {
		qr.update("insert into user values (?,?,?,?,?,?,?,?,?,?)",user.getUid(),
				user.getUsername(),user.getPassword(),user.getName(),user.getEmail()
				,user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),
				user.getCode());
	}
	public void updateUser(User user) throws Exception {
		qr.update("update user set username=?,password=?,name=?,email=?,birthday=?,state=?,code=? where uid=?", 
				user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getBirthday()
				,user.getState(),null,user.getUid());
	}
	public User getUserByCode(String code) throws Exception {
		return (User) qr.query("select * from user where code = ? limit 1",new BeanHandler<User>(User.class),code); 
	}
	public User getUserByUsernameAndPwd(String username, String password) throws Exception {
		String sql = "select * from user where username=? and password=? limit 1";
		return qr.query(sql, new BeanHandler<User>(User.class),username,password);
	}
	public User checkUsernameIsExist(String username) throws Exception {
		String sql = "select * from user where username = ?";
		User user = null;
		try {
			user = qr.query(sql, new BeanHandler<User>(User.class),username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

}
