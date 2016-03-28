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

public class ToppingsDao {

	Logger logger = Logger.getLogger("ToppingsDao");

	public List<Toppings> getToppings() {
		List<Toppings> list = new ArrayList<Toppings>();
		DbConnection dbConnection = DataBaseSingleton.getInstance();
		Statement ps = null;
		ResultSet rs = null;

		ps = dbConnection.getConnection();
		String sqlStatement = "select * from toppings";
		try {
			rs = ps.executeQuery(sqlStatement);

			while (rs.next()) {
				Toppings toppings = new Toppings();
				toppings.setId(rs.getInt("id"));
				toppings.setName(rs.getString("name"));
				toppings.setPrice(rs.getFloat("price"));
				toppings.setUrl(rs.getString("imageurl"));
				list.add(toppings);

			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.toString());
		}
		return list;
	}

	public int saveToppings(int id, float price, String list) {
		logger.log(Level.INFO, list);
		int status = 0;
		try {
			DbConnection dbConnection = DataBaseSingleton.getInstance();
			Statement ps = null;
			ps = dbConnection.getConnection();
			String sql = "update billinfo set toppingsrate ='" + price + "', toppings='" + list + "' where id='" + id + "'";
			status = ps.executeUpdate(sql);
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

}
