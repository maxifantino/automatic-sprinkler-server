package com.aut.watering.server.controller;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
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
import com.aut.watering.server.constants.SprinklerConstants;
import com.aut.watering.server.data.DeleteSprinklerRequest;
import com.aut.watering.server.data.ServerMessages;
import com.aut.watering.server.data.SprinklerRequest;
import com.aut.watering.server.data.WaterRequest;
import com.aut.watering.server.dto.Garden;
import com.aut.watering.server.dto.Patch;
import com.aut.watering.server.enums.AvailableSprinklerStatus;
import com.aut.watering.server.service.DynamicPropertiesService;
import com.aut.watering.server.service.GardenService;
import com.aut.watering.server.service.SprinklerService;
import com.google.gson.JsonObject;

@Controller
public class SprinkerController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SprinklerService sprinklerService;

	@Autowired
	private GardenService gardenService;
	
	@Autowired
	private DynamicPropertiesService propertyService;
	
	@RequestMapping( consumes = "application/json", produces = "application/json", value = "/garden/{gardenId}/patch")
	@ResponseBody
	public ResponseEntity<?> createSprinkler(@RequestBody SprinklerRequest request, @PathVariable Integer gardenId, @RequestParam Integer userId){
		HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
		String token;
		int status;
		log.info ("Creating sprinkler with the following request: " + request.toString());
		Garden garden = gardenService.getGarden(gardenId);
		if (garden!=null && garden.getUser().getId() == userId){
			if (sprinklerService.validate(request)){
				status = HttpStatus.SC_CREATED;
				sprinklerService.save(request);
				responseBuilder
				.withHttpCode(HttpStatus.SC_CREATED)
				.withMessage(ServerMessages.SPRINKLER_CREATED);
			}
			else{
				responseBuilder
				.withHttpCode(HttpStatus.SC_BAD_REQUEST)
				.withMessage(MessageFormat.format( ServerMessages.SPRINKLER_BAD_REQUEST,
						propertyService.getPropertyAsString("max.watering.time")));			
				status = HttpStatus.SC_BAD_REQUEST;
			}
		}
		else{
			responseBuilder
			.withHttpCode(HttpStatus.SC_UNAUTHORIZED)
			.withMessage(ServerMessages.GARDEN_NOT_FOUND);
			status = HttpStatus.SC_UNAUTHORIZED;
		}
		
		log.info("Result: " + responseBuilder.toString());
		return ResponseEntity.status(status).body(responseBuilder.toString());
	}

	@RequestMapping( produces = "application/json", value = "/garden/{gardenId}/patch", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> deleteSprinkler( @PathVariable Integer gardenId, @RequestParam Integer patchId, @RequestParam Integer userId){
		HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
		DeleteSprinklerRequest request = null;
		String token;
		int status;
		log.info ("Deleting sprinkler with the following request: patchId: " + patchId + " - userId: " + userId + " - gardenId: " + gardenId);
		Integer requestPatchId = request != null ? request.getPatchId() : patchId;
		Patch sprinkler = sprinklerService.getSprinkler(requestPatchId);

		if  (sprinklerService.validateDeleteRequest(patchId, gardenId, userId)){
			status = HttpStatus.SC_OK;
			sprinklerService.delete(sprinkler);
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
		
		log.info("Result: " + responseBuilder.toString());
		return ResponseEntity.status(status).body(responseBuilder.toString());
		
	}

	@RequestMapping( consumes = "application/json", produces = "application/json", value = "/garden/{gardenId}/patch", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> modifySprinkler(@RequestBody SprinklerRequest request, @PathVariable Integer gardenId){
		HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
		String token;
		int status;
		log.info ("Modifying sprinkler with the following request: " + request.toString());
		Patch sprinkler;
		if (StringUtils.isNotBlank(request.getPatchCode())) {
			sprinkler = sprinklerService.getSprinkler(request.getPatchCode());	
		}
		else {
			sprinkler = sprinklerService.getSprinkler(request.getPatchId());
		}
		
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
				
		log.info("Result: " + responseBuilder.toString());
		return ResponseEntity.status(status).body(responseBuilder.toString());
	}	
	
	@RequestMapping(produces = "application/json", value = "/garden/{gardenId}/patch", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getSprinklerStatus(@RequestParam Integer patchId,  @PathVariable Integer gardenId){
		HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
		String token;
		int status;
		log.info ("Getting sprinkler with the following request: " + patchId);
		Patch sprinkler = sprinklerService.getSprinkler(patchId);
		if (sprinkler != null){
			AvailableSprinklerStatus sprinklerStatus = AvailableSprinklerStatus.getFromDescription(sprinkler.getStatus());
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
		log.info(MessageFormat.format("Result: patchCode: {1}- response: ", patchId)  + responseBuilder.toString());
		return ResponseEntity.status(status).body(responseBuilder.toString());
	}
	
	@RequestMapping( consumes = "application/json", produces = "application/json", value = "/garden/{gardenId}/patch/checkActivation", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> shouldActivateSprinkler(@RequestBody WaterRequest request,  @PathVariable Integer gardenId){
		HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
		String token;
		int status;
		boolean activationResult = false;
		log.info ("Check sprinklier activation with following request: " + request.toString());
		Patch sprinkler = sprinklerService.getSprinkler(request.getPatchId());
		if (sprinkler != null){
			DateTime nextSchedulledDate = DateTime.parse(request.getNextDateTimeCheck(), DateTimeFormat.forPattern(SprinklerConstants.SPRINKLER_DATE_FORMAT));
			activationResult = sprinklerService.shouldActivateSprinkler(sprinkler, request.getCurrentHumidity(), nextSchedulledDate.toDate());
			responseBuilder
			.withHttpCode(HttpStatus.SC_OK)
			.withMessage(getStatusMesage(activationResult) );			
			status = HttpStatus.SC_OK;
		}
		else{
			responseBuilder
			.withHttpCode(HttpStatus.SC_NOT_FOUND)
			.withMessage( ServerMessages.SPRINKLER_NOT_FOUND);			
			status = HttpStatus.SC_NOT_FOUND;
		}
		log.info(MessageFormat.format("Activation Result: patchCode: {1} - response: ", activationResult) + responseBuilder.toString());
		return ResponseEntity.status(status).body(responseBuilder.toString());
	}
	private String getStatusMesage(boolean status){
		JsonObject response = new JsonObject();
		response.addProperty("activate_sprinkler", status);
		return response.toString();
	}
}
