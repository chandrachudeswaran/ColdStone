package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.DataBaseConnection;
import database.DataBaseSingleton;

public class UserDao {
	Logger logger = Logger.getLogger("UserDao");

	public boolean doLogin(String username, String password,String device,String session) {
		boolean result = false;
		DataBaseConnection dbConnection = DataBaseSingleton.getInstance();
		Statement ps = null;
		ResultSet rs = null;
		try {
			ps = dbConnection.getConnection();
			int count = 0;
			String sqlStatement = "select userid,password from userlogin where userid='"+username+"' and password='"+password+"'";
			rs = ps.executeQuery(sqlStatement);

			while (rs.next()) {
				count++;
			}
			if (count == 1) {
				result = true;
				toggleSession(device, session,username);
			}
			
			ps.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.toString());
		}
		return result;
	}
	
	public int doSingup(String username,String password,String device){
		DataBaseConnection dbConnection = DataBaseSingleton.getInstance();
		Statement ps = null;
		int status=0;
		
		try {
			ps = dbConnection.getConnection();
			String sqlStatement = "insert into userlogin(userid,password) "
					+ "values ('"+username+"','"+password+"')";
			
			 status= ps.executeUpdate(sqlStatement);
			ps.close();
			createSession(username, device);
		} catch (SQLException e) {
			logger.log(Level.SEVERE,e.toString());
		}
		logger.log(Level.INFO, "created an account " + status);
		return status;
	}
	
	public Weight getBill(String username){
		String status="P";
		Weight weight = new Weight();
		DataBaseConnection dbConnection = DataBaseSingleton.getInstance();
		Statement ps = null;
		ResultSet rs = null;
		try {
			ps = dbConnection.getConnection();
			String sqlStatement = "select * from billinfo where userid='"+username+"' and billstatus='"+status+"'";
			rs = ps.executeQuery(sqlStatement);
			while (rs.next()) {
				weight.setWeight(rs.getString("weight"));
				weight.setUserid(rs.getString("userid"));
				weight.setDateinserted(rs.getDate("timeinserted").toString());
				weight.setTimeinserted(rs.getTime("timeinserted").toString());
				weight.setStatus(rs.getString("billstatus"));
				weight.setId(rs.getInt("id"));
				weight.setPrice(rs.getFloat("price"));
				weight.setToppings(rs.getString("toppings"));
				weight.setToppingsrate(rs.getFloat("toppingsrate"));
			}
			
		} catch (SQLException e) {
			logger.log(Level.SEVERE,e.toString());
		}
		logger.log(Level.INFO, weight.toString());
		return weight;
	}
	
	public List<Weight> getHistory(String username){
		String accepted="A";
		String canceled ="C";
		List<Weight> list = new ArrayList<Weight>();
		DataBaseConnection dbConnection = DataBaseSingleton.getInstance();
		Statement ps = null;
		ResultSet rs = null;
		try {
			ps = dbConnection.getConnection();
			String sqlStatement = "select * from billinfo where userid='"+username+"' and billstatus in ('"+accepted+"','"+canceled+"')";
			rs = ps.executeQuery(sqlStatement);
			
			while(rs.next()){
				Weight weight = new Weight();
				weight.setWeight(rs.getString("weight"));
				weight.setUserid(rs.getString("userid"));
				weight.setDateinserted(rs.getDate("timeinserted").toString());
				weight.setTimeinserted(rs.getTime("timeinserted").toString());
				weight.setStatus(rs.getString("billstatus"));
				weight.setId(rs.getInt("id"));
				weight.setPrice(rs.getFloat("price"));
				weight.setToppings(rs.getString("toppings"));
				weight.setToppingsrate(rs.getFloat("toppingsrate"));
				list.add(weight);
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE,e.toString());
		}
		return list;
	}
	
	public int updateUserStatus(String status,String username,String id,String toppings,float price){
		int id_int = Integer.parseInt(id);
		DataBaseConnection dbConnection = DataBaseSingleton.getInstance();
		Statement ps = null;
		int statusUpdate=0;
		try {
			ps = dbConnection.getConnection();
			String sqlStatement= "update billinfo set billstatus='"+status+"',toppings='"+toppings+"',price='"+price+"' where id = '"+id_int+"'";
			statusUpdate=ps.executeUpdate(sqlStatement);
			ps.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE,e.toString());
		}
		return statusUpdate;
	}
	
	public String checkSessionExists(String device,String status){
		DataBaseConnection dbConnection = DataBaseSingleton.getInstance();
		Statement ps = null;
		ResultSet rs = null;
		String session="";
		String userid="";
		try {
			ps = dbConnection.getConnection();
			String sqlStatement= "select * from session where deviceid='"+device+"' and session='"+status+"'";
			rs = ps.executeQuery(sqlStatement);
			while(rs.next()){
				 session = rs.getString("session");
				 userid=rs.getString("userid");
			}
			
			if(session.equals("Y")){
				return userid;
			}else{
				return "1";
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE,e.toString());
		}
		return "1";
	}
	
	
	public boolean isUserExists(String username){
		DataBaseConnection dbConnection = DataBaseSingleton.getInstance();
		String sqlStatement="";
		Statement ps = null;
		ResultSet rs = null;
		boolean check = false;
		
		
		try {
			
			int count =0;
			ps = dbConnection.getConnection();
			sqlStatement = "select count(userid) as count from userlogin where userid='"+username+"'";
			rs = ps.executeQuery(sqlStatement);
			
			while(rs.next()){
				count = rs.getInt("count");
			}
			ps.close();
			if(count > 1){
				check = true;
			}else{
				check = false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}
	
	public int toggleSession(String device,String session,String username){
		DataBaseConnection dbConnection = DataBaseSingleton.getInstance();
		String sqlStatement="";
		
		Statement ps = null;
		int statusUpdate=0;
		
		try {
			ps = dbConnection.getConnection();
			sqlStatement= "update session set session='"+session+"' where deviceid = '"+device+"' and userid='"+username+"'";
			statusUpdate=ps.executeUpdate(sqlStatement);
			
			if(statusUpdate==0){
				sqlStatement = "insert into session values('"+username+"','"+device+"','Y')";
			    ps.executeUpdate(sqlStatement);
			}
			
			ps.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE,e.toString());
		}
		logger.log(Level.INFO,"Update Query Result Value "+statusUpdate);
		
		
		return statusUpdate;
	}
	
	
	public int createSession(String userid,String device){
		String session="Y";
		DataBaseConnection dbConnection = DataBaseSingleton.getInstance();
		Statement ps = null;
		int statusUpdate=0;
		try {
			ps = dbConnection.getConnection();
			String sqlStatement= "insert into session (userid,deviceid,session) values ('"+userid+"','"+device+"','"+session+"')";
			statusUpdate=ps.executeUpdate(sqlStatement);
			ps.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE,e.toString());
		}
		return statusUpdate;
		
	}
	
}
