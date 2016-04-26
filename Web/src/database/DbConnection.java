package database;

import java.sql.Statement;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class DbConnection implements DataBaseConnection{

	public Statement getConnection() {
		Statement ps = null;
		try {
			
			BasicDataSource source = new BasicDataSource();
			source.setDriverClassName("com.mysql.jdbc.Driver");
			source.setUsername("adminwilyNBC");
			source.setPassword("dGlutp6EMy4Q");
			source.setUrl("jdbc:mysql://127.7.184.130:3306/easypay");
			
			java.sql.Connection connection = source.getConnection();
		
			ps = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ps;
	}

}
