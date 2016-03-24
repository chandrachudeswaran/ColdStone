package api;


import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import service.ToppingService;
import service.UserService;
import dao.Toppings;
import dao.Weight;

@Path("/user")
public class UserApi {

	@Path("/login")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public int doLogin(@FormParam("username") String username,
			@FormParam("password") String password,@FormParam("device") String device) {
		UserService userService = new UserService();
		 if(userService.doLogin(username, password,device,"Y")){
			 return 0;
		 }else{
			 return 1;
		 }
	}
	
	
	@Path("/signup")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public int doSignup(@FormParam("username") String username,@FormParam("password") String password,@FormParam("device") String device){
		UserService userService = new UserService();
		if(userService.isUserExists(username)){
			return 0;
		}else{
			return userService.doSingup(username, password,device);
		}
		
	}

	@Path("/session")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String sessionExists(@FormParam("device") String device){
		UserService userService = new UserService();
		return userService.checkSessionExists(device);
	}
	
	@Path("/getBill")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Weight getBill(@FormParam("username") String username){
		UserService userService = new UserService();
		return userService.getBill(username);
	}
	
	
	@Path("/history")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<Weight> getHistoryBillDetails(@FormParam("username") String username){
		UserService userService = new UserService();
		return userService.getHistory(username);
	}
	
	@Path("/statusupdate")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public int updateUserStatus(@FormParam("username") String username,@FormParam("status") String status,@FormParam("id") String id){
		UserService userService = new UserService();
		return userService.updateUserStatus(status, username,id);
	}
	
	@Path("/logout")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public int logout(@FormParam("device") String device,@FormParam("username") String username){
		UserService userService = new UserService();
		return userService.toggleSession(device, "N",username);
	}
	
	@Path("/gettoppings")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Toppings> geToppings(){
		ToppingService toppingservice = new ToppingService();
		return toppingservice.getToppings();
	}
	
	
	
	
}
