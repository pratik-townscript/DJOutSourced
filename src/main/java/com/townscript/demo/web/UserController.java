package com.townscript.demo.web;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.townscript.demo.api.APIResponse;
import com.townscript.demo.model.User;
import com.townscript.demo.service.UserService;

@Controller
public class UserController {
	protected static final String JSON_API_CONTENT_HEADER = "Content-type=application/json";
	
	@Autowired
    private UserService userService;
	
	@RequestMapping(value="/loginUser", method=RequestMethod.POST, headers = {JSON_API_CONTENT_HEADER})
	public @ResponseBody APIResponse loginUser(@RequestBody User user , HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("main index of controller called");
		System.out.println("ot is " + user.getPassword());
		System.out.println("ot is " + user.getUsername());
		
		User userRetrieved = userService.findByUsername(user.getUsername());
		
		HashMap<String, Object> authResp = new HashMap<String, Object>();
		
		if(userRetrieved == null 
		    || !userRetrieved.getPassword().equals(user.getPassword()))
		{
			System.out.println("invalid credentials are passed");
			//throw new ServletException("");
			return APIResponse.toErrorResponse("Invalid username/password combination");
		}
		System.out.println("userretrieved is " + userRetrieved);
		
		authResp.put("user", userRetrieved);
		
		return APIResponse.toOkResponse(authResp);
	}

	@RequestMapping(value="/registerUser", method=RequestMethod.POST, headers = {JSON_API_CONTENT_HEADER})
	public @ResponseBody APIResponse registerUser(@RequestBody User user , HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("register method called");
		
		User userRetrieved = userService.findByUsername(user.getUsername());
		
		HashMap<String, Object> authResp = new HashMap<String, Object>();
		
		if(userRetrieved != null)
		{
			return APIResponse.toErrorResponse("User Already Exists");
		}
		else
		{
			System.out.println("Saving User " + user);
			userService.save(user);
		}
		userRetrieved = userService.findByUsername(user.getUsername());
		authResp.put("user", userRetrieved);
		return APIResponse.toOkResponse(authResp);
	}
}
