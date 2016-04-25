package controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.EasyPayConstants;
import service.WeightService;

public class HomeCtr extends HttpServlet {

	Logger logger = Logger.getLogger("HomeCtr");
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.log(Level.INFO, request.getServletPath());

		String weight = request.getParameter("weight");
		logger.log(Level.INFO, "Weight entered is  " + weight);
		weight = weight.replace("\"", "").trim().toString();
		
		if(checkforValidWeight(weight)){
		WeightService service = new WeightService();
		service.insertWeightInitial(convertVoltageToWeight(weight));
		}else{
			logger.log(Level.INFO,"Invalid weight entered" + " "+ weight);
		}

	}
	
	
	public boolean checkforValidWeight(String weight){
		double weight_integer= Double.valueOf(weight);
		if(weight_integer<0.75 || weight_integer > 3.5){
			return false;
		}else{
			return true;
		}
	}
	
	
	public String convertVoltageToWeight(String voltage){
		
		DecimalFormat decimalFormat = new DecimalFormat(".##");
		double result =0.0;	
		result = (Math.exp(EasyPayConstants.A_VALUE))*(Math.exp(EasyPayConstants.B_VALUE*Double.valueOf(voltage)));
		logger.log(Level.INFO,String.valueOf(result));
		if(result > 200 && result <250){
			result = result + 26.9;
		}else if(result>=250 && result<=400){
			result = result+46.4;
		}else if(result >400 && result<=450 ){
			result = result + 12.5;
		}else if(result >450 && result <500){
			result = result - 11.3;
		}else if(result >= 500 && result <900){
			result = result - 60;
		}else if(result > 900){
			result = result -80;
		}
		
		logger.log(Level.INFO,decimalFormat.format(result));
		return decimalFormat.format(result);
		
	}
	
	
}
