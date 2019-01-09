package com.aut.watering.server.controller;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aut.watering.server.builder.HttpResponseBuilder;
import com.aut.watering.server.data.CreateUserRequest;
import com.aut.watering.server.data.LoginRequest;
import com.aut.watering.server.data.ServerMessages;
import com.aut.watering.server.dto.Garden;
import com.aut.watering.server.dto.User;
import com.aut.watering.server.service.GardenService;
import com.aut.watering.server.service.UserService;
import com.google.gson.Gson;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GardenService gardenService;
	
	final static Logger log = LoggerFactory.getLogger(UserController.class);

	@RequestMapping( consumes = "application/json", produces = "application/json", value = "/user/login", method=RequestMethod.POST)
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
		log.info("Result:" + responseBuilder.toString());
			
		return ResponseEntity.status(status).body(responseBuilder.toString());
	}

	@RequestMapping( consumes = "application/json", produces = "application/json", value = "/user/create", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> createUser(@RequestBody CreateUserRequest userRequest){
		HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
		int httpCode=200;
		log.info("Creating userRequest:" + userRequest.toString());

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
		log.info("Result:" + responseBuilder.toString());
		return ResponseEntity.status(httpCode).body(responseBuilder.toString());
	}

	@RequestMapping( produces = "application/json", value = "/user/{userId}/gardens", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getGardens(@PathVariable Integer userId){
		HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
		int httpCode=200;
		log.info("Getting gardens from:" + userId);
		List<Garden> gardens = gardenService.getGardenByUserId(userId);
		String httpMessage;
		ResponseEntity<?> response;
		if(CollectionUtils.isEmpty(gardens)){
			httpCode = HttpStatus.SC_NOT_FOUND;
			httpMessage = ServerMessages.USER_DOESNOT_HAVE_GARDENS;		
			responseBuilder.withHttpCode(httpCode)
			.withMessage(httpMessage);	
			log.info("Result:" + responseBuilder.toString());
			response = ResponseEntity.status(httpCode).body(responseBuilder.toString());
		}
		else{				
			Gson gson = new Gson();
			response = ResponseEntity.status(HttpStatus.SC_OK).body(gson.toJson(gardens));
		}
		return response;
	}

	
	private void populateCreateResponse (User user, int httpCode, String httpMessage){
		httpCode = user.getId()  == null ? HttpStatus.SC_INTERNAL_SERVER_ERROR : HttpStatus.SC_CREATED;
		httpMessage = user.getId() == null ? ServerMessages.USER_CREATION_ERROR : ServerMessages.USER_CREATED;
	}
	
}
