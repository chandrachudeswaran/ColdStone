package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class LocalDbConnection implements DataBaseConnection{

	public Statement getConnection() {
		Statement ps = null;
		try {
			Connection con = null;			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://127.0.0.1:3306/coldstone";
			String user = "root";
			String pass = "root";
			con = DriverManager.getConnection(url, user, pass);
			ps = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ps;
	}

}
