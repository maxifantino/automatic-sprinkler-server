package com.aut.watering.server.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aut.watering.server.data.CreateUserRequest;
import com.aut.watering.server.data.LoginRequest;
import com.aut.watering.server.data.ServerMessages;
import com.aut.watering.server.dto.User;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TokenGeneratorService tokenService;

	@Autowired
	private UserValidatorService validatorService;
	
	private Logger log = LoggerFactory.getLogger(UserService.class);
	
	public boolean validateLogin(LoginRequest request){ // la papota esta aca.
		log.error("validate: " + request.toString());
		return (StringUtils.isNotBlank(request.getUsername()) 
				&& StringUtils.isNotBlank(request.getPassword())) ? true : false;	
	}

	public String validateUserRequest(CreateUserRequest request){
		String message = null;
		log.info("getUsername: " + request.getUsername());
		if (!validatorService.validateEmail(request.getEmail())){
			message = ServerMessages.EMAIL_NOT_VALID;
		}
		else if (!validatorService.validateName(request.getName())){
			message = ServerMessages.NAME_NOT_VALID;
		}
		else if (!validatorService.validatePassword(request.getPassword())){
			message = ServerMessages.PASSWORD_NOT_VALID;
		}
		else if (!validatorService.validateUserName(request.getUsername())){
			message = ServerMessages.USERNAME_PWD_BLANK;
		}
		return message;
	}
	
	public String login (LoginRequest request){
		boolean result = false;
		log.error("doing login....." + request.toString());
		User populatedUser = userDao.getUser(request.getUsername());
		log.error("Populated user: " + populatedUser.toString());
		result = (populatedUser != null) ? 
				request.getPassword().equals(populatedUser.getPassword()) : false;		
		log.error("Result: " + result);
		return result ? tokenService.getToken() : StringUtils.EMPTY;
	}
	
	public User getUser (String username){
		User user = userDao.getUser(username);
		return user;
	}
	
	public User getUser (Integer userId){
		User user = userDao.getUser(userId);
		return user;
	}
	
	public User createUser (CreateUserRequest createRequest){
		// primero chequeo que el usuario no exista anteriormente
		log.error("CreateRequest: " + createRequest.toString());
		User user = new User();
		user.setEmail(createRequest.getEmail());
		user.setName(createRequest.getName());
		user.setPassword(createRequest.getPassword());
		user.setSurname(createRequest.getSurname());
		user.setUsername(createRequest.getUsername());
		log.error("Por salvar.... " + userDao);
		userDao.saveUser(user);
		return user;
	}

	public User findOrCreate(CreateUserRequest createRequest) {
		User user = null;
		try{
			user = getUser(createRequest.getUsername());
		}
		catch (Exception e){
			user = createUser(createRequest);
		}
		return user;
	}
}
