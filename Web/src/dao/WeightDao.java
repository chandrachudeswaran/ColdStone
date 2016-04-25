package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.DataBaseConnection;
import database.DataBaseSingleton;

public class WeightDao {
	
	Logger logger = Logger.getLogger("WeightDao");
	
	public void insertWeight(String weight,String time,String status){
		logger.log(Level.INFO,weight);
		DataBaseConnection dbConnection = DataBaseSingleton.getInstance();
		Statement ps = null;
		
		try {
			ps = dbConnection.getConnection();
			String sqlStatement = "insert into billinfo(weight,timeinserted,billstatus) "
					+ "values ('"+weight+"','"+time+"','"+status+"')";
			ps.executeUpdate(sqlStatement);
			ps.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE,e.toString());
		}
	}
	
	
	public Weight getLatestWeight(){
		Weight weight = new Weight();
		DataBaseConnection dbConnection = DataBaseSingleton.getInstance();
		Statement ps = null;
		ResultSet rs = null;
		String status ="N";
		try {
			ps = dbConnection.getConnection();
			String sqlStatement = "select * from billinfo where billstatus='"+status+"' order by timeinserted asc limit 1";
			System.out.println(sqlStatement);
			rs=ps.executeQuery(sqlStatement);
			
			while(rs.next()){
				weight.setWeight(rs.getString("weight"));
				weight.setDateinserted(rs.getDate("timeinserted").toString());
				weight.setTimeinserted(rs.getTime("timeinserted").toString());
				weight.setStatus(rs.getString("billstatus"));
				weight.setId(rs.getInt("id"));
			}
			ps.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE,e.toString());
		}
		
		return weight;
	}

	
	public boolean isValidUser(String username){
		DataBaseConnection dbConnection = DataBaseSingleton.getInstance();
		Statement ps = null;
		ResultSet rs = null;
		boolean check = true;
		int count=0;
		try {
			ps = dbConnection.getConnection();
			String sqlStatement = "select count(userid) as count from userlogin where userid='"+username+"'";
			rs= ps.executeQuery(sqlStatement);
			
			while(rs.next()){
				count = rs.getInt("count");
			}
			
			if(count==1){
				check = true;
			}else{
				check = false;
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}

	
	public void doProcessed(String username,String status,float price,int id){
		DataBaseConnection dbConnection = DataBaseSingleton.getInstance();
		Statement ps = null;
		try {
			ps = dbConnection.getConnection();
			String sqlStatement= "update billinfo set userid='"+username+"',billstatus='"+status+"',price='"+price+"' where id='"+id+"'";
			ps.executeUpdate(sqlStatement);
			ps.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE,e.toString());
		}
	}

}
