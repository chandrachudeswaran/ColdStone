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
}
