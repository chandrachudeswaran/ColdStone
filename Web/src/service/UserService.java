package service;

import java.util.List;

import dao.UserDao;
import dao.Weight;

public class UserService {
	
	private UserDao userDao;
	
	public UserService(){
		userDao = new UserDao();
	}
	
	public boolean doLogin(String username, String password) {
		return userDao.doLogin(username, password);
	}
	
	public int doSingup(String username,String password){
		return userDao.doSingup(username, password);
	}
	
	public Weight getBill(String username){
		return userDao.getBill(username);
	}
	
	public List<Weight> getHistory(String username){
		return userDao.getHistory(username);
	}
	
	public int updateUserStatus(String status,String username){
		return userDao.updateUserStatus(status, username);
	}
	
	public String checkSessionExists(String device){
		return userDao.checkSessionExists(device);
	}
	
	public int logout(String device){
		return userDao.logout(device);
	}

}
