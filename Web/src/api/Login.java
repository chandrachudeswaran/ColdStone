package api;


import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import service.UserService;

@Path("/login")
public class Login {

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String doLogin(@FormParam("username") String username,
			@FormParam("password") String password) {
		UserService userService = new UserService();
		 if(userService.doLogin(username, password)){
			 return "true";
		 }else{
			 return "false";
		 }
	}
}
