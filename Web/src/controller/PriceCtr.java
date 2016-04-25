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

		logger.log(Level.INFO, request.getServletPath());
		WeightService service = new WeightService();
		
		
		
		if (request.getServletPath().equals("/price")) {
			logger.log(Level.INFO,"Calling bill");
			String queryParameter = request.getQueryString();
			if(queryParameter!= null){
				logger.log(Level.INFO, queryParameter);
				request.setAttribute("status", "invalid");
				
			}
			
			String url = "price.jsp";
			Weight weight = service.getLatestWeight();
			String cost = service.getCost(weight.getWeight());
			request.setAttribute("Cost", cost);
			request.setAttribute("Weight", weight.getWeight());
			request.setAttribute("Id", String.valueOf(weight.getId()));
			if (cost == null) {
				url = "/nobill";
			}
			forwardRequest(request, response, url);
			
		} else if (request.getServletPath().equals("/user")) {
			
			String userid = request.getParameter("userid");
			if(service.isValidUser(userid)){
				logger.log(Level.INFO,"Valid user");
				String cost = request.getParameter("price");
				cost = cost.replace("$", "");
				cost = cost.replace("cents", "");
				float price = Float.valueOf(cost.trim());
				int id = Integer.valueOf(request.getParameter("id"));
				service.updateProcessedStatus(userid, "P", price, id);
				forwardRequest(request, response, "index.jsp");
				
			}else{
				logger.log(Level.INFO,"Invalid user ");
				forwardRequest(request, response, "/price?status=invalid");
			}
			
			
		} else if (request.getServletPath().equals("/nobill")) {
				request.setAttribute("billstatus", "none");
				forwardRequest(request, response, "index.jsp");
		} else if(request.getServletPath().equals("/weight")){
			String weight = request.getParameter("weight");
			logger.log(Level.INFO,"weight" + weight);
			if("".equals(weight)){
				request.setAttribute("weightstatus", "Enter Valid Weight");
				forwardRequest(request, response, "weight.jsp");
				logger.log(Level.INFO,"No weight Entered");
			}else{
				service.insertWeightInitial(weight);
				request.setAttribute("weightstatus", "Placed Order");
				forwardRequest(request, response, "index.jsp");	
			}
		}
	}

	public void forwardRequest(HttpServletRequest request,
			HttpServletResponse response, String url) {
		try {
			request.getRequestDispatcher(url).forward(request, response);
		} catch (ServletException e) {
			logger.log(Level.SEVERE, e.toString());
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.toString());
		}
	}

}
