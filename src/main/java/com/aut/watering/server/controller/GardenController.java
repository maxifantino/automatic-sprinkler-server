package com.aut.watering.server.controller;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aut.watering.server.builder.HttpResponseBuilder;
import com.aut.watering.server.data.CreateGardenRequest;
import com.aut.watering.server.data.ServerMessages;
import com.aut.watering.server.data.SprinklerRequest;
import com.aut.watering.server.dto.Garden;
import com.aut.watering.server.service.GardenService;

@Controller
public class GardenController {
	
	final static Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private GardenService gardenService;
	
	@RequestMapping(produces = "application/json", value = "/garden/{id}/", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> deleteGarden(@RequestBody SprinklerRequest request, @PathVariable Integer gardenId, @RequestParam Integer userId){
		HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
		String token;
		int status;
		log.info (MessageFormat.format("Garden delete request received: {userId:\"{1}\",gardenId:\"{2}\" ", userId, gardenId));
		Garden garden = gardenService.getGarden(gardenId); 
		
		if (garden == null){
			status = HttpStatus.SC_NOT_FOUND;
			responseBuilder.withHttpCode(HttpStatus.SC_NOT_FOUND)
					.withMessage(ServerMessages.GARDEN_NOT_FOUND); 
		}
		else if (garden != null && !gardenService.validateGardenDelete(garden, userId)){
			status = HttpStatus.SC_OK;
			responseBuilder.withHttpCode(HttpStatus.SC_FORBIDDEN)
					.withMessage(""); 
		}
		else{
			status = HttpStatus.SC_OK;
			responseBuilder.withHttpCode(HttpStatus.SC_OK)
					.withMessage(ServerMessages.GARDEN_ERASED); 
			// TODO Atrapar error por si falla la operacion, asi reintenta nuevamente.
		}
			
		return ResponseEntity.status(status).body(responseBuilder.toString());	
	} 
	
	@RequestMapping(produces = "application/json", value = "/garden/{id}/", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> addGarden(@RequestBody CreateGardenRequest request, @PathVariable Integer gardenId, @RequestParam Integer userId){
		HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
		String token;
		int status;
		log.info ("Creating garden with the following request: " + request.toString());
		if (gardenService.validateCreateRequest(request)){
			boolean result = gardenService.createGarden(request);
			if(!result){
				status = HttpStatus.SC_INTERNAL_SERVER_ERROR;
				responseBuilder.withHttpCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
						.withMessage(ServerMessages.INTERNAL_ERROR); 
				
			}
			else{
				status = HttpStatus.SC_CREATED;
				responseBuilder.withHttpCode(HttpStatus.SC_CREATED)
						.withMessage(ServerMessages.GARDEN_CREATED); 
			}
		}
		log.error(MessageFormat.format("Result:{1}", responseBuilder.toString()));
		return ResponseEntity.status(status).body(responseBuilder.toString());
	}
	
}
