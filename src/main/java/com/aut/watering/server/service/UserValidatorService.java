package com.aut.watering.server.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class UserValidatorService {

	public boolean validateEmail(String email){
       String pattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
       return StringUtils.isNotBlank(email) && validateRegexp(pattern, email);
	}

	public boolean validateUserName(String username){
       return StringUtils.isNotBlank(username) && validateRegexp( "[a-zA-Z0-9_-]{8,16}", username);
	}
	
	public boolean validatePassword(String password){
		return StringUtils.isNotBlank(password) && validateRegexp ( "[a-zA-Z0-9_-]{8,16}", password) && password.length() > 7;
	}

	public boolean validateName(String name){
		return StringUtils.isNotBlank(name)&& name.length() > 3;
	}
	
	private boolean validateRegexp(String pattern, String input) {
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(input);
        return m.lookingAt();
	}
}
