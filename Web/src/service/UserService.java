package service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.FormParam;

import dao.UserDao;
import dao.Weight;

public class UserService {
	
	Logger logger = Logger.getLogger("UserService");

	private UserDao userDao;

	public UserService() {
		userDao = new UserDao();
	}

	public boolean doLogin(String username, String password, String device,
			String session) {
		return userDao.doLogin(username, password, device, session);
	}

	public int doSingup(String username, String password,String device) {
		return userDao.doSingup(username, password,device);
	}

	public Weight getBill(String username) {
		return userDao.getBill(username);
	}

	public List<Weight> getHistory(String username) {
		return userDao.getHistory(username);
	}

	public int updateUserStatus(String status, String username,String id,String toppings,String price) {
		return userDao.updateUserStatus(status, username,id,toppings,Float.valueOf(price));
	}

	public String checkSessionExists(String device) {
		logger.log(Level.INFO, "Entered device id"+ device);
		return userDao.checkSessionExists(device,"Y");
	}

	public boolean isUserExists(@FormParam("username") String username){ 
		return userDao.isUserExists(username);
	}
	
	public int toggleSession(String device, String session,String username ) {
		return userDao.toggleSession(device, session,username);
	}

	
}
