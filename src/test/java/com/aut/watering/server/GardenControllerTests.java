package com.aut.watering.server;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.aut.watering.server.builder.CreateUserRequestBuilder;
import com.aut.watering.server.constants.SprinklerConstants;
import com.aut.watering.server.controller.GardenController;
import com.aut.watering.server.data.CreateGardenRequest;
import com.aut.watering.server.data.CreateUserRequest;
import com.aut.watering.server.dto.Garden;
import com.aut.watering.server.dto.User;
import com.aut.watering.server.service.GardenService;
import com.aut.watering.server.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@EnableAutoConfiguration
//@PropertySource("classpath:application.properties")


// @ContextConfiguration(classes = {ApplicationConfiguration.class})
@AutoConfigureTestDatabase 
public class GardenControllerTests {

	final static Logger log = LoggerFactory.getLogger(GardenController.class);

	@LocalServerPort
	private int port;
	
	@Autowired
	private GardenService gardenService;
	
	@Autowired
	private UserService userService;

	private TestRestTemplate restTemplate = new TestRestTemplate();
			
	@Test
	public void cantCreateGardenWithUnknownUserId() throws Exception {
		
		// creo mock User
		User user = createMockUser();
		Integer userID = user.getId();
		
		String jsonRequest = "{\"userId:" + Integer.toString(userID +1)  
		+",\"gardenName\":\"testgarden\"," +				
		"\"address\": \" san martin 342  \" ,"+
		"\"city\": \" CABA \" ,"+
		"\"country\": \"Argentina \","+
		"\"latitude\": \"-34.7372732\","+
		"\"longitude\": \"-58.400216715\","+ 
		"\"wateringTimeWindow\": \"{'from':10, 'to':18}\" ,"+
		"\"WateringTimeworkingDays\": \"[1,2,3,4,5,6]\"}";
		
		String uri = "/garden";
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		HttpStatus resultStatus = HttpStatus.CONFLICT;
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort(uri), entity, String.class);
			resultStatus = response.getStatusCode();
		} catch (Exception e) {
			log.error("No deberia pasar");
			log.error("Exception e", e);
		}
		assertTrue(resultStatus.is4xxClientError());			
	}

	@Test
	public void cantCreateGardenWithNoAddress() throws Exception {
		
		// creo mock User
		User user = createMockUser();
		Integer userID = user.getId();
		
		String jsonRequest = "{\"userId:" + Integer.toString(userID)  
		+",\"gardenName\":\"testgarden\"," +		
		"\"city\": \" CABA \" ,"+
		"\"country\": \"Argentina \","+
		"\"latitude\": \"-34.7372732\","+
		"\"longitude\": \"-58.400216715\","+ 
		"\"wateringTimeWindow\": \"{'from':10, 'to':18}\" ,"+
		"\"wateringTimeworkingDays\": \"[1,2,3,4,5,6]\"}";
		String uri = "/garden";
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		HttpStatus resultStatus = HttpStatus.CONFLICT;
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort(uri), entity, String.class);
			resultStatus = response.getStatusCode();
		} catch (Exception e) {
			log.error("No deberia pasar");
			log.error("Exception e", e);
		}
		assertTrue(resultStatus.is4xxClientError());			
	}

	@Test
	public void cantCreateGardenWithNoCountry() throws Exception {
		
		// creo mock User
		User user = createMockUser();
		Integer userID = user.getId();
		
		String jsonRequest = "{\"userId:" + Integer.toString(userID)  
		+",\"gardenName\":\"testgarden\"," +				
		"\"address\": \" san martin 342  \" ,"+
		"\"city\": \" CABA \" ,"+
		"\"latitude\": \"-34.7372732\","+
		"\"longitude\": \"-58.400216715\","+ 
		"\"wateringTimeWindow\": \"{'from':10, 'to':18}\" ,"+
		"\"wateringWorkingDays\": \"[1,2,3,4,5,6]\"}";
		String uri = "/garden";
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		HttpStatus resultStatus = HttpStatus.CONFLICT;
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort(uri), entity, String.class);
			resultStatus = response.getStatusCode();
		} catch (Exception e) {
			log.error("No deberia pasar");
			log.error("Exception e", e);
		}
		assertTrue(resultStatus.is4xxClientError());			
	}

	@Test
	public void canCreateGardenWithWholeData() throws Exception {
		
		// creo mock User
		User user = createMockUser();
		Integer userID = user.getId();
		
		String jsonRequest = "{\"userId\":" + Integer.toString(userID)  
		+",\"gardenName\":\"testgarden\"," +				
			"\"address\": \" san martin 342  \" ,"+
			"\"city\": \" CABA \" ,"+
			"\"country\": \"Argentina \","+
			"\"latitude\": \"-34.7372732\","+
			"\"longitude\": \"-58.400216715\","+ 
			"\"wateringTimeWindow\": \"{'from':10, 'to':18}\" ,"+
			"\"wateringWorkingDays\": \"[1,2,3,4,5,6]\"}";
		log.error("Request: " + jsonRequest);
		String uri = "/garden";
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		HttpStatus resultStatus = HttpStatus.CONFLICT;
		log.error("Por llamar al controller!!!!");
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort(uri), entity, String.class);
			resultStatus = response.getStatusCode();
		} catch (Exception e) {
			log.error("No deberia pasar");
			log.error("Exception e", e);
		}
		assertTrue(resultStatus.is2xxSuccessful());			
	}
	
	@Test
	public void canCreateGardenWithDefaultWorkingDays() throws Exception {
		
		// creo mock User
		User user = createMockUser();
		Integer userID = user.getId();		
		String jsonRequest = "{\"userId\":" + Integer.toString(userID)  
		+",\"gardenName\":\"testgarden\"," +				
			"\"address\": \" san martin 342  \" ,"+
			"\"city\": \" CABA \" ,"+
			"\"country\": \"Argentina \","+
			"\"latitude\": \"-34.7372732\","+
			"\"longitude\": \"-58.400216715\","+ 
			"\"wateringTimeWindow\": \"{'from':10, 'to':18}\"}";
	
		String uri = "/garden";
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		HttpStatus resultStatus = HttpStatus.CONFLICT;
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort(uri), entity, String.class);
			resultStatus = response.getStatusCode();
		} catch (Exception e) {
			log.error("No deberia pasar");
			log.error("Exception e", e);
		}
		assertTrue(resultStatus.is2xxSuccessful());			
	}
	
	@Test
	public void canCreateGardenWithDefaultTimeWindow() throws Exception {
		
		// creo mock User
		User user = createMockUser();
		Integer userID = user.getId();
		String jsonRequest = "{\"userId\":" + Integer.toString(userID)  
		+",\"gardenName\":\"testgarden\"," +				
			"\"address\": \" san martin 342  \" ,"+
			"\"city\": \" CABA \" ,"+
			"\"country\": \"Argentina \","+
			"\"latitude\": \"-34.7372732\","+
			"\"longitude\": \"-58.400216715\","+ 
			"\"wateringWorkingDays\": \"[1,2,3,4,5,6]\"}";		
		String uri = "/garden";
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		HttpStatus resultStatus = HttpStatus.CONFLICT;
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort(uri), entity, String.class);
			resultStatus = response.getStatusCode();
		} catch (Exception e) {
			log.error("No deberia pasar");
			log.error("Exception e", e);
		}
		assertTrue(resultStatus.is2xxSuccessful());			
	}

	@Test
	public void cantDeleteNonExistentGarden() throws Exception {
		// creo mock User
		User user = createMockUser();
		Integer userID = user.getId();

		CreateGardenRequest request = getMockGardenRequest(userID);
		Garden garden = gardenService.createGarden(request, user);		
		String uri = "/garden/" + garden.getId() + 10 +"?userId=" + userID;

		URI deleteUrl = new URI(createURLWithPort(uri));
		try {
			restTemplate.delete (deleteUrl);
		} catch (Exception e) {
			log.error("No deberia pasar");
			log.error("Exception e", e);
		}
		assertNotNull(gardenService.getGarden(garden.getId()));
	}

	@Test
	public void cantDeleteGardenFromOtherUser() throws Exception {
		// creo mock User
		User user = createMockUser();
		Integer userID = user.getId();

		CreateGardenRequest request = getMockGardenRequest(userID);
		Garden garden = gardenService.createGarden(request, user);		
		String uri = "/garden/" + garden.getId()  +"?userId=" + userID + 1;
		URI deleteUrl = new URI(createURLWithPort(uri));
		try {
			restTemplate.delete (deleteUrl);
		} catch (Exception e) {
			log.error("No deberia pasar");
			log.error("Exception e", e);
		}
		assertNotNull(gardenService.getGarden(garden.getId()));			
	}
	
	@Test
	public void canDeleteGarden() throws Exception {
		// creo mock User
		User user = createMockUser();
		Integer userID = user.getId();
		CreateGardenRequest request = getMockGardenRequest(userID);
		Garden garden = gardenService.createGarden(request, user);		
		String uri = "/garden/" + garden.getId() +"?userId=" + userID;
		URI deleteUrl = new URI(createURLWithPort(uri));
		try {
			log.error("Url: " + deleteUrl.toString());
			restTemplate.delete (deleteUrl);
		} catch (Exception e) {
			log.error("No deberia pasar");
			log.error("Exception e", e);
		}
		assertNull(gardenService.getGarden(garden.getId()));			
	}

	// modification tests
	
	public void cantModifyNotFoundGarden(){
		// creo mock User
		User user = createMockUser();
		Integer gardenId;
		Integer userID = user.getId();
		String jsonRequest = "{\"userId\":" + Integer.toString(userID)  
		+",\"gardenName\":\"testgarden\"," +				
			"\"address\": \" san martin 342  \" ,"+
			"\"city\": \" CABA \" ,"+
			"\"country\": \"Argentina \","+
			"\"latitude\": \"-34.7372732\","+
			"\"longitude\": \"-58.400216715\","+ 
			"\"wateringWorkingDays\": \"[1,2,3,4,5,6]\"}";		
		String uri = "/garden/gardenId?userId=userID";
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		HttpStatus resultStatus = HttpStatus.CONFLICT;
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort(uri), entity, String.class);
			resultStatus = response.getStatusCode();
		} catch (Exception e) {
			log.error("No deberia pasar");
			log.error("Exception e", e);
		}
		assertTrue(resultStatus.is5xxServerError());			
	}
	
	private User createMockUser() {
		CreateUserRequestBuilder builder = new CreateUserRequestBuilder()
				.withUsername("elbardemoe")
				.withEmail("elbardemoe@gmail.com")
				.withPassword("pwd12345")
				.withSurename("surename")
				.withName("nombretest");
				
				CreateUserRequest createRequest = builder.build();
				log.error("Por llamar a userService");
				User user = userService.findOrCreate
						(createRequest);
				return user;
	}

	private Garden createMockGarden(){
		User user = createMockUser();
		Integer userId = user.getId();
		CreateGardenRequest request = getMockGardenRequest(userId);
		Garden garden = gardenService.createGarden(request, user);
		return garden;
	}

	private CreateGardenRequest getMockGardenRequest(Integer userId) {
		CreateGardenRequest request = new CreateGardenRequest();
		request.setAddress("mockAddress");
		request.setCity("mockCity");
		request.setCountry("mockCountry");
		request.setGardenName("mockGarden");
		request.setLatitude("-34.7372732");
		request.setLongitude("-58.40002");
		request.setPatchList("");
		request.setUserId(userId);
		request.setWateringTimeWindow(SprinklerConstants.DEFAULT_WORKING_HOURS);
		request.setWateringWorkingDays(SprinklerConstants.DEFAULT_WORKING_DAYS);
		return request;
	}

	private HttpEntity<String> buildRequestEntity(String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		HttpEntity <String> entity= new HttpEntity<String>(json, headers);
		return entity;
	}
	

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	
	
}
