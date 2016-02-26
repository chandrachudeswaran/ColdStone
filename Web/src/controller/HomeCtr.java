package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		WeightService service = new WeightService();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		service.insertWeightInitial(weight, dateFormat.format(cal.getTime()));

	}
}
