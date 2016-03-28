package service;

import java.util.List;
import java.util.logging.Logger;

import dao.Toppings;
import dao.ToppingsDao;

public class ToppingService {

	Logger logger = Logger.getLogger("ToppingService");
	
	private ToppingsDao toppingsDao;
	
	
	public ToppingService(){
		toppingsDao = new ToppingsDao();
	}
	
	public List<Toppings> getToppings() {
		return toppingsDao.getToppings();
	}
	
	public int saveToppings(String id, String price, String  list) {
		return toppingsDao.saveToppings(Integer.valueOf(id), Float.valueOf(price), list);
	}
}
