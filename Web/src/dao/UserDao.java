package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
			System.out.println(result);
			ps.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.toString());
		}
		return result;
	}
}
