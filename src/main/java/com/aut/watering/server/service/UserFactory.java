package com.aut.watering.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aut.watering.server.dto.User;

@Service
public class UserFactory {
	
	@Autowired
	private UserDao userDao;
	
	/***
	 * Crea el enttorno de test.
	 */
	public void init(){
	
		User user1 = new User();
		user1.setName("Moe");
		user1.setSurname("Szizlak");
		user1.setUsername("elbardemoe");
		user1.setEmail("elbardemoe@gmail.com");
		user1.setPassword("pwd12345");
		userDao.saveUser(user1);

		User user2 = new User();
		user1.setName("Lisa");
		user1.setSurname("Simpsons");
		user1.setUsername("lisasimpson");
		user1.setEmail("lisasimpson@gmail.com");
		user1.setPassword("pwd12345");
		userDao.saveUser(user2);
	}
		
}
