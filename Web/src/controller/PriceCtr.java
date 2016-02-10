package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.WeightService;
import dao.Weight;

public class PriceCtr extends HttpServlet {

	Logger logger = Logger.getLogger("PriceCtr");
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		logger.log(Level.INFO,request.getServletPath());
		WeightService service = new WeightService();
		if(request.getServletPath().equals("/price")){
		
		Weight weight = service.getLatestWeight();
		String cost = service.getCost(weight.getWeight());
		request.setAttribute("Cost", cost);
		request.setAttribute("Weight", weight.getWeight());
		request.setAttribute("Id", String.valueOf(weight.getId()));
		try {
			request.getRequestDispatcher("price.jsp").forward(request,response);
		} catch (ServletException e) {
			logger.log(Level.SEVERE, e.toString());
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.toString());
		}
		}else if(request.getServletPath().equals("/user")){
			String userid = request.getParameter("userid");
			String cost = request.getParameter("cost");
			float price = Float.valueOf(cost);
			int id = Integer.valueOf(request.getParameter("id"));
			service.updateProcessedStatus(userid, "P", price, id);
			request.getRequestDispatcher("index.jsp").forward(request,response);
		}
	}

}
