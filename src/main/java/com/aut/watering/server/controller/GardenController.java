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
import com.aut.watering.server.data.ModifyGardenRequest;
import com.aut.watering.server.data.ServerMessages;
import com.aut.watering.server.dto.Garden;
import com.aut.watering.server.dto.User;
import com.aut.watering.server.service.GardenService;
import com.aut.watering.server.service.UserService;

@Controller
public class GardenController {
	
	final static Logger log = LoggerFactory.getLogger(GardenController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private GardenService gardenService;
	
	@RequestMapping(produces = "application/json", value = "/garden/{gardenId}", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> deleteGarden(@PathVariable Integer gardenId, @RequestParam Integer userId){
		HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
		String token;
		int status;
		log.info (MessageFormat.format("Garden delete request received: userId:{1},gardenId:{2}", Integer.toString(userId), Integer.toString(gardenId)));
		Garden garden = gardenService.getGarden(gardenId); 
		if (garden == null){
			status = HttpStatus.SC_NOT_FOUND;
			responseBuilder.withHttpCode(HttpStatus.SC_NOT_FOUND)
					.withMessage(ServerMessages.GARDEN_NOT_FOUND); 
		}
		else if (!gardenService.validateGardenDelete(garden, userId)){
			status = HttpStatus.SC_FORBIDDEN;
			responseBuilder.withHttpCode(HttpStatus.SC_FORBIDDEN)
					.withMessage(""); 
		}
		else{
			gardenService.deleteGarden(garden);
			status = HttpStatus.SC_OK;
			responseBuilder.withHttpCode(HttpStatus.SC_OK)
					.withMessage(ServerMessages.GARDEN_ERASED); 
			// TODO Atrapar error por si falla la operacion, asi reintenta nuevamente.
		}
		log.info("Result: " + responseBuilder.toString());
		return ResponseEntity.status(status).body(responseBuilder.toString());	
	} 
	
	@RequestMapping(produces = "application/json", value = "/garden", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> addGarden(@RequestBody CreateGardenRequest request){
		HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
		String token;
		int status=200;
		log.info ("Creating garden with the following request: " + request.toString());
		User user = userService.getUser(request.getUserId());
		if (gardenService.validateCreateRequest(request)){
			Garden newGarden = gardenService.createGarden(request, user);
			if(newGarden == null){
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
		log.info("Result: " + responseBuilder.toString());
		return ResponseEntity.status(status).body(responseBuilder.toString());
	}

	@RequestMapping(produces = "application/json", value = "/garden/{gardenId}", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> modifyGarden(@PathVariable Integer gardenId, @RequestBody ModifyGardenRequest request, @RequestParam Integer userId){
		HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
		String token;
		int status=200;
		log.info ("Updating garden with the following request: " + request.toString());
		Garden garden = gardenService.getGarden(gardenId);
		if (garden != null && garden.getId() > 0){
			String validationMessage = gardenService.validateModifyRequest(request);
			if(garden.getUser().getId() != userId){
				status = HttpStatus.SC_UNAUTHORIZED;
				responseBuilder.withHttpCode(HttpStatus.SC_UNAUTHORIZED)
						.withMessage(ServerMessages.UNAUTHORIZED_GARDEN_MODIFICATION); 
				
			}
			else if(StringUtils.isNotBlank(validationMessage)){
				status = HttpStatus.SC_BAD_REQUEST;
				responseBuilder.withHttpCode(HttpStatus.SC_BAD_REQUEST)
						.withMessage(validationMessage); 			
			}
			else{
				gardenService.modifyGarden(request, garden);
				status = HttpStatus.SC_OK;
				responseBuilder.withHttpCode(HttpStatus.SC_OK)
						.withMessage(ServerMessages.GARDEN_MODIFIED); 
			}
		}
		else {
			status = HttpStatus.SC_NOT_FOUND;
			responseBuilder.withHttpCode(HttpStatus.SC_NOT_FOUND)
					.withMessage(ServerMessages.GARDEN_NOT_FOUND); 

		}
		log.info("Result:" + responseBuilder.toString());
		return ResponseEntity.status(status).body(responseBuilder.toString());
	}

}
