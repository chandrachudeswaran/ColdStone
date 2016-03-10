package service;

import dao.Weight;
import dao.WeightDao;

public class WeightService {

	private WeightDao weightDao;

	public WeightService() {
		this.weightDao = new WeightDao();
	}

	public WeightDao getWeightDao() {
		return weightDao;
	}

	public void insertWeightInitial(String weight, String time) {
		weightDao.insertWeight(weight, time, "N");
	}

	public Weight getLatestWeight() {
		return weightDao.getLatestWeight();
	}
	
	public boolean isValidUser(String username){
		return weightDao.isValidUser(username);
	}

	public String getCost(String weight) {
		if (weight != null) {
			double weight_int = Double.valueOf(weight);
			double totalCost = weight_int * 10;

			totalCost = totalCost / 100;
			if (totalCost > 1) {
				return "$" + totalCost;
			} else {
				return totalCost * 100 + "cents";
			}
		}else{
			return null;
		}

	}

	public void updateProcessedStatus(String username, String status,
			float price, int id) {
		weightDao.doProcessed(username, status, price, id);
	}
}
