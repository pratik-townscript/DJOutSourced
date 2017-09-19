package com.townscript.demo.web;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
	private static final Logger logger = Logger.getLogger(UserController.class);
	protected static final String JSON_API_CONTENT_HEADER = "Content-type=application/json";
	
	@Autowired
    private UserService userService;
		
	@RequestMapping(value="/loginUser", method=RequestMethod.POST, headers = {JSON_API_CONTENT_HEADER})
	public @ResponseBody APIResponse loginUser(@RequestBody User user , HttpServletRequest request, HttpServletResponse response)
	{
		logger.info("Login User ...");
		User userRetrieved = userService.findByUsername(user.getUsername());
		
		HashMap<String, Object> authResp = new HashMap<String, Object>();
		
		if(userRetrieved == null 
		    || !userRetrieved.getPassword().equals(user.getPassword()))
		{
			logger.info("invalid credentials passed for user " + user.getUsername());
			return APIResponse.toErrorResponse("Invalid username/password combination");
		}
		authResp.put("user", userRetrieved);
		
		return APIResponse.toOkResponse(authResp);
	}

	@RequestMapping(value="/registerUser", method=RequestMethod.POST, headers = {JSON_API_CONTENT_HEADER})
	public @ResponseBody APIResponse registerUser(@RequestBody User user , HttpServletRequest request, HttpServletResponse response)
	{
		logger.info("Trying to register new user " + user.getUsername());
		User userRetrieved = userService.findByUsername(user.getUsername());
		
		HashMap<String, Object> authResp = new HashMap<String, Object>();
		
		if(userRetrieved != null)
		{
			String msg = "User Already Exists";
			logger.info(msg + " for " + user.getUsername());
			return APIResponse.toErrorResponse(msg);
		}
		else
		{
			logger.info("Saving User " + user.getUsername());
			userService.save(user);
		}
		userRetrieved = userService.findByUsername(user.getUsername());
		authResp.put("user", userRetrieved);
		return APIResponse.toOkResponse(authResp);
	}
}
