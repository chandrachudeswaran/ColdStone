package service;

import dao.UserDao;

public class UserService {
	
	private UserDao userDao;
	
	public UserService(){
		userDao = new UserDao();
	}
	
	public boolean doLogin(String username, String password) {
		return userDao.doLogin(username, password);
	}

}
