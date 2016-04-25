package database;

public class DataBaseSingleton {

/*	private static DbConnection instance;
	
	public static DbConnection getInstance(){
		
		if(instance ==null){
			instance = new DbConnection();
		}
		return instance;
	}*/

	private static LocalDbConnection instance;
	
	public static LocalDbConnection getInstance(){
		
		if(instance ==null){
			instance = new LocalDbConnection();
		}
		return instance;
	}

}
