package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		convertVoltageToWeight(weight);
		//service.insertWeightInitial(convertVoltageToWeight(weight), dateFormat.format(cal.getTime()));
		}else{
			logger.log(Level.INFO,"Invalid weight entered" + " "+ weight);
		}

	}
	
	
	public boolean checkforValidWeight(String weight){
		double weight_integer= Double.valueOf(weight);
		if(weight_integer==0.0 || weight_integer > 4.0){
			return false;
		}else{
			return true;
		}
	}
	
	
	public String convertVoltageToWeight(String voltage){
		
		DecimalFormat decimalFormat = new DecimalFormat(".##");
		double volt = Double.valueOf(voltage);
		
		double result =0.0;
		
		result = volt-EasyPayConstants.Y_INTERCEPT;
		logger.log(Level.INFO,String.valueOf(result));
		
		result = result/EasyPayConstants.X_COEFFICIENT;
		logger.log(Level.INFO,String.valueOf(result));
		logger.log(Level.INFO,decimalFormat.format(result));
		return decimalFormat.format(result);
		
	}
	
	
}
