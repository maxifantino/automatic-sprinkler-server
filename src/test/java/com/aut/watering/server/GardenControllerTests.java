package com.aut.watering.server;

import static org.junit.Assert.assertEquals;
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
@AutoConfigureTestDatabase 
public class GardenControllerTests extends WateringTests{

	final static Logger log = LoggerFactory.getLogger(GardenController.class);

	@LocalServerPort
	private int port;
	
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
			ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort(uri, port), entity, String.class);
			resultStatus = response.getStatusCode();
		} catch (Exception e) {
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
			ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort(uri, port), entity, String.class);
			resultStatus = response.getStatusCode();
		} catch (Exception e) {
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
			ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort(uri, port), entity, String.class);
			resultStatus = response.getStatusCode();
		} catch (Exception e) {
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
		String uri = "/garden";
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		HttpStatus resultStatus = HttpStatus.CONFLICT;
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort(uri, port), entity, String.class);
			resultStatus = response.getStatusCode();
		} catch (Exception e) {
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
			ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort(uri, port), entity, String.class);
			resultStatus = response.getStatusCode();
		} catch (Exception e) {
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
			ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort(uri, port), entity, String.class);
			resultStatus = response.getStatusCode();
		} catch (Exception e) {
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

		URI deleteUrl = new URI(createURLWithPort(uri, port));
		try {
			restTemplate.delete (deleteUrl);
		} catch (Exception e) {
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
		URI deleteUrl = new URI(createURLWithPort(uri, port));
		try {
			restTemplate.delete (deleteUrl);
		} catch (Exception e) {
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
		URI deleteUrl = new URI(createURLWithPort(uri, port));
		try {
			restTemplate.delete (deleteUrl);
		} catch (Exception e) {
			log.error("Exception e", e);
		}
		assertNull(gardenService.getGarden(garden.getId()));			
	}

	// modification tests
	@Test
	public void testCantModifyNotFoundGarden(){
		// creo mock User
		User user = createMockUser();
		Integer userID = user.getId();
		String jsonRequest = "{\"gardenName\":\"testgarden\"," +				
			"\"address\": \" san martin 342  \" ,"+
			"\"city\": \" CABA \" ,"+
			"\"country\": \"Argentina \","+
			"\"latitude\": \"-34.7372732\","+
			"\"longitude\": \"-58.400216715\","+ 
			"\"wateringWorkingDays\": \"[1,2,3,4,5,6]\"}";		
		String uri = "/garden/222222?userId=" +userID;
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		try {
			URI putUrl = new URI(createURLWithPort(uri, port));
			restTemplate.put(putUrl, entity);
		} catch (Exception e) {
			log.error("Exception e", e);
		}
	
	}

	@Test
	public void testCantModifyUnauthorizedGarden(){
		// creo mock User
		Garden garden= createMockGarden();
		String jsonRequest = "{\"gardenName\":\"testgarden\"," +				
			"\"address\": \" san martin 342  \" ,"+
			"\"city\": \" CABA \" ,"+
			"\"country\": \"Argentina \","+
			"\"latitude\": \"-34.7372732\","+
			"\"longitude\": \"-58.400216715\","+ 
			"\"wateringWorkingDays\": \"[1,2,3,4,5,6]\"}";		
		String uri = "/garden/"+ garden.getId() + "?userId=" +garden.getUser().getId() + 1;
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		try {
			URI putUrl = new URI(createURLWithPort(uri, port));
			
			 restTemplate.put(putUrl, entity);
			
		} catch (Exception e) {
			log.error("Exception e", e);
		}
		Garden updatedGarden = gardenService.getGarden(garden.getId());
		assertTrue(garden.equals(updatedGarden));			
	}

	@Test
	public void testCanModifyWorkingDays(){
		// creo mock User
		Garden garden= createMockGarden();
		String jsonRequest = 
			"{\"wateringWorkingDays\": \"[1]\"}";		
		String uri = "/garden/"+ garden.getId() + "?userId=" +garden.getUser().getId();
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		try {
			URI putUrl = new URI(createURLWithPort(uri, port));	
			 restTemplate.put(putUrl, entity);
		} catch (Exception e) {
			log.error("Exception e", e);
		}
		Garden updatedGarden = gardenService.getGarden(garden.getId());
		assertEquals("[1]", updatedGarden.getWorkingDays());
	}

	@Test
	public void testCanModifyGardenName(){
		// creo mock User
		Garden garden= createMockGarden();
		String jsonRequest = 
			"{\"gardenName\": \"JardinActualizado\"}";		
		String uri = "/garden/"+ garden.getId() + "?userId=" +garden.getUser().getId();
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		try {			
			URI putUrl = new URI(createURLWithPort(uri, port));	
			restTemplate.put(putUrl, entity);
		} catch (Exception e) {
			log.error("Exception e", e);
		}
		Garden updatedGarden = gardenService.getGarden(garden.getId());
		assertEquals("JardinActualizado", updatedGarden.getName());
	}

	@Test
	public void testCanModifyCountryAndCity(){
		// creo mock User
		Garden garden= createMockGarden();
		String jsonRequest = 
			"{\"country\": \"Brasil\","
			+ "\"city\": \"Recife\""+"}";		
		String uri = "/garden/"+ garden.getId() + "?userId=" +garden.getUser().getId();
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		try {
			URI putUrl = new URI(createURLWithPort(uri, port));	
			restTemplate.put(putUrl, entity);
		} catch (Exception e) {
			log.error("Exception e", e);
		}
		Garden updatedGarden = gardenService.getGarden(garden.getId());
		assertEquals("Brasil", updatedGarden.getLocation().getCountry());
		assertEquals("Recife", updatedGarden.getLocation().getCity());
	}
	
	
}
