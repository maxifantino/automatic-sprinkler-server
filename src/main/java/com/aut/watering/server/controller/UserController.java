package com.aut.watering.server.controller;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aut.watering.server.builder.HttpResponseBuilder;
import com.aut.watering.server.data.CreateUserRequest;
import com.aut.watering.server.data.LoginRequest;
import com.aut.watering.server.data.ServerMessages;
import com.aut.watering.server.dto.User;
import com.aut.watering.server.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping( consumes = "application/json", produces = "application/json", value = "/user/login")
	@ResponseBody
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
		HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
		String token;
		int status;
		if (userService.validateLogin(loginRequest)){
			token = userService.login(loginRequest);
			if (StringUtils.isNotBlank(token)){
				status = HttpStatus.SC_OK;
				responseBuilder.withHttpCode(HttpStatus.SC_OK)
						.withMessage(ServerMessages.OK_MESSAGE); 
			}
			else{
				status = HttpStatus.SC_FORBIDDEN;
				responseBuilder.withHttpCode(HttpStatus.SC_FORBIDDEN)
				.withMessage(ServerMessages.USERNAME_PWD_BLANK);
			}
		}
		else{
			status = HttpStatus.SC_BAD_REQUEST;
			responseBuilder.withHttpCode(HttpStatus.SC_BAD_REQUEST)
			.withMessage(ServerMessages.USERNAME_PWD_BLANK);	
		}
		
		return ResponseEntity.status(status).body(responseBuilder.toString());
	}

	@RequestMapping( consumes = "application/json", produces = "application/json", value = "/user/login")
	@ResponseBody
	public ResponseEntity<?> createUser(@RequestBody CreateUserRequest userRequest){
		HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
		int httpCode=200;
		String httpMessage = userService.validateUserRequest(userRequest);
		if (StringUtils.isEmpty(httpMessage)){
			if (userService.getUser(userRequest.getUsername()) != null){
				httpCode = HttpStatus.SC_CONFLICT;
				httpMessage = MessageFormat.format(ServerMessages.USER_ALREADY_CREATED, userRequest.getUsername());		
			}
			else{
				// creo al usuario
				User createdUser = userService.createUser(userRequest);
				populateCreateResponse (createdUser, httpCode, httpMessage);
			}
		}
		else{
			httpCode = HttpStatus.SC_BAD_REQUEST;
		}
		responseBuilder.withHttpCode(httpCode)
		.withMessage(httpMessage);	
		return ResponseEntity.status(httpCode).body(responseBuilder.toString());
	}

	private void populateCreateResponse (User user, int httpCode, String httpMessage){
		httpCode = user.getId()  == null ? HttpStatus.SC_INTERNAL_SERVER_ERROR : HttpStatus.SC_CREATED;
		httpMessage = user.getId() == null ? ServerMessages.USER_CREATION_ERROR : ServerMessages.USER_CREATED;
	}
	
}
