package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {

	public Statement getConnection() {
		Statement ps = null;
		try {
			Connection con = null;

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://127.5.207.130:3306/coldstone";
			String user = "admin9BlkRZG";
			String pass = "e6iIYbplZ5Mq";
			con = DriverManager.getConnection(url, user, pass);
			ps = con.createStatement();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}

}
