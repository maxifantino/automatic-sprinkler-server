package com.aut.watering.server.service;

import org.springframework.stereotype.Service;

@Service
public class UserValidatorService {

	public boolean validateEmail(String email){
		       String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
	           java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
	           java.util.regex.Matcher m = p.matcher(email);
	           return m.matches();
	}

	public boolean validateUserName(String username){
	       String ePattern = "a-zA-Z0-9_-";
	       java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
           java.util.regex.Matcher m = p.matcher(username);
           return m.matches();
	}
	
	public boolean validatePassword(String password){
		   String ePattern = "a-zA-Z0-9_-.";
	       java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
           java.util.regex.Matcher m = p.matcher(password);
           return m.matches() && password.length() > 7;
	}

	public boolean validateName(String name){
		return name.length() > 3;
	}
}
