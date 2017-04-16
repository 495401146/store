package com.store.service.impl;

import com.store.dao.UserDao;
import com.store.dao.impl.UserDaoImpl;
import com.store.domain.User;
import com.store.service.UserService;
import com.store.utils.MailUtils;

public class UserServiceImpl implements UserService {
	private UserDao userDao = new UserDaoImpl();
	/***
	 * ע���ʺ�
	 */
	public void register(User user) throws Exception {
		userDao.addUser(user);
		//�����ʼ�
		String emailMsg = "��ӭ��ע��������վ�����������ӻ���ֱ�Ӹ��Ƴ�����������<a href='http://localhost:8080/store/user?method=active&code="+user.getCode()+"'>http://localhost:8080/store/user?method=active&code="+user.getCode()+"<a>";
		MailUtils.sendMail(user.getEmail(), emailMsg);
	}
	/**
	 * �����ʺ�
	 * @throws Exception 
	 */
	public User active(String code) throws Exception {
		//����code�����ʺ�
		User user = userDao.getUserByCode(code);
		//�ж��ʺ��Ƿ���ҳɹ�,����ɹ�������״̬������user�����ɹ�������null
		if(user==null){
			return null;
		}
		user.setState(1);
		userDao.updateUser(user);
		return user;
		
	}
	public User login(String username, String password) throws Exception {
		User user = userDao.getUserByUsernameAndPwd(username,password);
		
		return user;
	}
	
	public User checkUsernameIsExist(String username) throws Exception {
		return userDao.checkUsernameIsExist(username);
	}

}
