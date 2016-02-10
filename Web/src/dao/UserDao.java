package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.DataBaseSingleton;
import database.DbConnection;

public class UserDao {
	Logger logger = Logger.getLogger("UserDao");

	public boolean doLogin(String username, String password) {
		boolean result = false;
		DbConnection dbConnection = DataBaseSingleton.getInstance();
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
			}
			
			ps.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.toString());
		}
		return result;
	}
	
	public int doSingup(String username,String password){
		DbConnection dbConnection = DataBaseSingleton.getInstance();
		Statement ps = null;
		int status=0;
		
		try {
			ps = dbConnection.getConnection();
			String sqlStatement = "insert into userlogin(userid,password) "
					+ "values ('"+username+"','"+password+"')";
			
			 status= ps.executeUpdate(sqlStatement);
			ps.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE,e.toString());
		}
		return status;
	}
	
	public Weight getBill(String username){
		String status="P";
		Weight weight = new Weight();
		DbConnection dbConnection = DataBaseSingleton.getInstance();
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
			}
			
		} catch (SQLException e) {
			logger.log(Level.SEVERE,e.toString());
		}
		return weight;
	}
	
	public List<Weight> getHistory(String username){
		String accepted="A";
		String canceled ="C";
		List<Weight> list = new ArrayList<Weight>();
		DbConnection dbConnection = DataBaseSingleton.getInstance();
		Statement ps = null;
		ResultSet rs = null;
		try {
			ps = dbConnection.getConnection();
			String sqlStatement = "select * from billinfo where userid='"+username+"' and billstatus='"+accepted+"' or '"+canceled+"'";
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
				list.add(weight);
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE,e.toString());
		}
		return list;
	}
	
	public int updateUserStatus(String status,String username){
		DbConnection dbConnection = DataBaseSingleton.getInstance();
		Statement ps = null;
		int statusUpdate=0;
		try {
			ps = dbConnection.getConnection();
			String sqlStatement= "update billinfo set billstatus='"+status+"' where id = (select b.id from  (select * from billinfo) b where b.userid='"+username+"')";
			statusUpdate=ps.executeUpdate(sqlStatement);
			ps.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE,e.toString());
		}
		return statusUpdate;
	}
	
	public String checkSessionExists(String device){
		DbConnection dbConnection = DataBaseSingleton.getInstance();
		Statement ps = null;
		ResultSet rs = null;
		String session="";
		String userid="";
		try {
			ps = dbConnection.getConnection();
			String sqlStatement= "select * from session where deviceid='"+device+"'";
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
	
	public int logout(String device){
		String session="N";
		DbConnection dbConnection = DataBaseSingleton.getInstance();
		Statement ps = null;
		int statusUpdate=0;
		try {
			ps = dbConnection.getConnection();
			String sqlStatement= "update session set session='"+session+"' where deviceid = '"+device+"'";
			statusUpdate=ps.executeUpdate(sqlStatement);
			ps.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE,e.toString());
		}
		return statusUpdate;
	}
	
	
}
