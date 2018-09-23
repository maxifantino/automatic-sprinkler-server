package com.aut.watering.server.controller;

import java.text.MessageFormat;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aut.watering.server.builder.HttpResponseBuilder;
import com.aut.watering.server.data.ServerMessages;
import com.aut.watering.server.data.SprinklerRequest;
import com.aut.watering.server.dto.Patch;
import com.aut.watering.server.service.SprinklerService;

@Controller
public class SprinkerController {

	final static Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private SprinklerService sprinklerService;
	
	@RequestMapping( consumes = "application/json", produces = "application/json", value = "/garden/{id}/patch")
	@ResponseBody
	public ResponseEntity<?> createSprinkler(@RequestBody SprinklerRequest request, @PathVariable Integer gardenId, @RequestParam Integer patchId){
		HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
		String token;
		int status;
		log.info ("Creating sprinkler with the following request: " + request.toString());
		Patch sprinkler = sprinklerService.getSprinkler(request.getPatchCode());
		if (sprinkler){
			
		}
		else{
			responseBuilder
			.withHttpCode(HttpStatus.SC_UNAUTHORIZED)
			.withMessage(ServerMessages.SPRINKLER_NOT_FOUND);
			status = HttpStatus.SC_UNAUTHORIZED;
		}
		
		
		log.error(MessageFormat.format("Result:{1}", responseBuilder.toString()));
		return ResponseEntity.status(status).body(responseBuilder.toString());

		
	}
}
