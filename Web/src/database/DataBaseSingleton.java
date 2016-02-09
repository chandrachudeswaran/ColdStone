package database;

public class DataBaseSingleton {

	private static DbConnection instance;
	
	public static DbConnection getInstance(){
		
		if(instance ==null){
			instance = new DbConnection();
		}
		return instance;
	}
}
