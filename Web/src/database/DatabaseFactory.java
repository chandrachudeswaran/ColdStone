package database;

import constants.EasyPayConstants;

public class DatabaseFactory {
	
	public DataBaseConnection getDatBaseObject(String connection){
		
		if(connection.equals(EasyPayConstants.OPEN_SHIFT_SERVER_NAME)){
			return new DbConnection();
		}else{
			return new LocalDbConnection();
		}
	}

}
