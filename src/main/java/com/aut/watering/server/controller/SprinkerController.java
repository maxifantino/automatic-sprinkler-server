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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aut.watering.server.builder.HttpResponseBuilder;
import com.aut.watering.server.data.DeleteSprinklerRequest;
import com.aut.watering.server.data.ServerMessages;
import com.aut.watering.server.data.SprinklerRequest;
import com.aut.watering.server.dto.Patch;
import com.aut.watering.server.enums.AvailableSprinklerStatus;
import com.aut.watering.server.service.DynamicPropertiesService;
import com.aut.watering.server.service.SprinklerService;

@Controller
public class SprinkerController {

	final static Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private SprinklerService sprinklerService;
	
	@Autowired
	private DynamicPropertiesService propertyService;
	
	@RequestMapping( consumes = "application/json", produces = "application/json", value = "/garden/{id}/patch")
	@ResponseBody
	public ResponseEntity<?> createSprinkler(@RequestBody SprinklerRequest request, @PathVariable Integer gardenId, @RequestParam Integer patchId){
		HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
		String token;
		int status;
		log.info ("Creating sprinkler with the following request: " + request.toString());
		Patch sprinkler = sprinklerService.getSprinkler(request.getPatchCode());
		if (sprinkler!=null){
			if (sprinklerService.validate(request)){
				status = HttpStatus.SC_BAD_REQUEST;
				sprinklerService.save(request);
				responseBuilder
				.withHttpCode(HttpStatus.SC_CREATED)
				.withMessage(ServerMessages.SPRINKLER_CREATED);
			}
			else{
				responseBuilder
				.withHttpCode(HttpStatus.SC_BAD_REQUEST)
				.withMessage(MessageFormat.format( ServerMessages.SPRINKLER_BAD_REQUEST,
						propertyService.getPropertyAsInteger("max.watering.time")));			
				status = HttpStatus.SC_BAD_REQUEST;
			}
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

	@RequestMapping( consumes = "application/json", produces = "application/json", value = "/garden/{id}/patch", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> deleteSprinkler(@RequestBody DeleteSprinklerRequest request, @PathVariable Integer gardenId){
		HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
		String token;
		int status;
		log.info ("Deleting sprinkler with the following request: " + request.toString());
		if (sprinklerService.validateDeleteRequest(request, gardenId)){
			status = HttpStatus.SC_OK;
			sprinklerService.delete(request);
			responseBuilder
			.withHttpCode(HttpStatus.SC_OK)
			.withMessage(ServerMessages.SPRINKLER_DELETED);
		}
		else{
			responseBuilder
			.withHttpCode(HttpStatus.SC_NOT_FOUND)
			.withMessage( ServerMessages.SPRINKLER_NOT_FOUND);			
			status = HttpStatus.SC_NOT_FOUND;
		}
		
		log.error(MessageFormat.format("Result:{1}", responseBuilder.toString()));
		return ResponseEntity.status(status).body(responseBuilder.toString());
		
	}

	@RequestMapping( consumes = "application/json", produces = "application/json", value = "/garden/{id}/patch", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> modifySprinkler(@RequestBody SprinklerRequest request, @PathVariable Integer gardenId){
		HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
		String token;
		int status;
		log.info ("Modifying sprinkler with the following request: " + request.toString());
		Patch sprinkler = sprinklerService.getSprinkler(request.getPatchCode());
		
		if (sprinkler != null){
			if (sprinklerService.validateModify(request)){
				status = HttpStatus.SC_OK;
				sprinklerService.modify(request, sprinkler);
				responseBuilder
				.withHttpCode(HttpStatus.SC_OK)
				.withMessage(ServerMessages.SPRINKLER_DELETED);
			}
			else{
				responseBuilder
				.withHttpCode(HttpStatus.SC_BAD_REQUEST)
				.withMessage( ServerMessages.SPRINKLER_BAD_REQUEST);			
				status = HttpStatus.SC_BAD_REQUEST;
			}	
		}else{
			responseBuilder
			.withHttpCode(HttpStatus.SC_NOT_FOUND)
			.withMessage( ServerMessages.SPRINKLER_NOT_FOUND);			
			status = HttpStatus.SC_NOT_FOUND;
		}
				
		log.error(MessageFormat.format("Result:{1}", responseBuilder.toString()));
		return ResponseEntity.status(status).body(responseBuilder.toString());
	}	
	
	@RequestMapping(produces = "application/json", value = "/garden/$id/patch?patchId=parchId", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getSprinklerStatus(@RequestParam String patchCode,  @PathVariable Integer gardenId){
		HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
		String token;
		int status;
		log.info ("Getting sprinkler with the following request: " + patchCode);
		Patch sprinkler = sprinklerService.getSprinkler(patchCode);
		if (sprinkler != null){
			AvailableSprinklerStatus sprinklerStatus = AvailableSprinklerStatus.getFromId(sprinkler.getStatus());
			String message = sprinklerService.getSprinklerStatusResponse(sprinklerStatus);
			responseBuilder.withHttpCode(HttpStatus.SC_OK)
			.withMessage(message);
			status = HttpStatus.SC_OK;
		}
		else{
			responseBuilder
			.withHttpCode(HttpStatus.SC_NOT_FOUND)
			.withMessage( ServerMessages.SPRINKLER_NOT_FOUND);			
			status = HttpStatus.SC_NOT_FOUND;
		}
		log.error(MessageFormat.format("Status Result: patchCode: {1}- response: {2}", patchCode, responseBuilder.toString()));
		return ResponseEntity.status(status).body(responseBuilder.toString());
	}
}
