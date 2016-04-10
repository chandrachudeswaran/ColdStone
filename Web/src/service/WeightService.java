package service;

import java.text.DecimalFormat;

import constants.EasyPayConstants;
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
		DecimalFormat decimalFormat = new DecimalFormat(".##");
		if (weight != null) {
			double weight_int = Double.valueOf(weight);
			double totalCost = weight_int * EasyPayConstants.PRICE_PER_GRAM;

			totalCost = totalCost / 100;
			if (totalCost > 1) {
				return "$" + decimalFormat.format(totalCost);
			} else {
				totalCost=Double.valueOf(decimalFormat.format(totalCost));
				return totalCost * 100 + " cents";
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
