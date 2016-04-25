package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbConnection implements DataBaseConnection{

	public Statement getConnection() {
		Statement ps = null;
		try {
			Connection con = null;
			/*BasicDataSource source = new BasicDataSource();
			
			source.setDriverClassName("com.mysql.jdbc.Driver");
			source.setUsername("adminIJ3sSMw");
			source.setPassword("IYm1hrx8b_r4");
			source.setUrl("jdbc:mysql://127.12.24.130:3306/easypay");
			*/
			
			/*Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://127.0.0.1:3306/coldstone";
			String user = "root";
			String pass = "root";*/
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://127.12.24.130:3306/easypay";
			String user = "adminIJ3sSMw";
			String pass = "IYm1hrx8b_r4";
			con = DriverManager.getConnection(url, user, pass);
			//java.sql.Connection connection = source.getConnection();
			
			//con = DriverManager.getConnection(url, user, pass);
			ps = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ps;
	}

}
