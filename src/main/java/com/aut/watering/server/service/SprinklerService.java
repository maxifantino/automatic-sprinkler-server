package com.aut.watering.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.aut.watering.server.dto.Garden;
import com.aut.watering.server.dto.Patch;

public class SprinklerService {
	@Autowired
	private SprinklerDao dao;
		
	private Logger log = LoggerFactory.getLogger(UserService.class);
	
	public Patch getSprinkler(String sprinklerCode){
		return dao.getSprinkler(sprinklerCode);
	}

}
