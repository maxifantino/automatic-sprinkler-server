package com.aut.watering.server;

import static org.junit.Assert.assertTrue;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.aut.watering.server.dto.Garden;
import com.aut.watering.server.dto.User;
import com.aut.watering.server.service.GardenService;
import com.aut.watering.server.service.SprinklerService;
import com.aut.watering.server.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase 
public class SprinklerControllerTests extends WateringTests{
	
	final Logger log = LoggerFactory.getLogger(this.getClass());

	@LocalServerPort
	private int port;
	
	@Autowired
	private SprinklerService sprinklerService;

	private TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void testCantCreateSprinklerInNotFoundGarden(){
		
		// creo mock User
		Garden garden = createMockGarden();
		Integer userID = garden.getUser().getId();
		
		String jsonRequest =  "{\"patchCode\":\"testPatch\"," +
				"\"humidityThreshold\": 60 ," + 
				"\"humidityCritical\" : 40 ," +
				"\"wateringSeconds\" : 300 ," +
				"\"type\" : \"type\"," +
				"\"status\" : \"inactive\" ," +
				"\"humidity\" : 30}";
		
		
		String uri = "/garden/" + Integer.toString(garden.getId() + 2) + "/patch?userId=" + userID;
		log.error("Uri: " + uri);
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		HttpStatus resultStatus = HttpStatus.CONFLICT;
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort(uri, port), entity, String.class);
			resultStatus = response.getStatusCode();
		} catch (Exception e) {
			log.error("No deberia pasar");
			log.error("Exception e", e);
		}
		assertTrue(resultStatus.is4xxClientError());			
	}
	
	@Test
	public void testCantCreateSprinklerInNonAuthorizedGarden(){

		// creo mock User
		Garden garden = createMockGarden();
		Integer userID = garden.getUser().getId();
		
		String jsonRequest =  "{\"patchCode\":\"testPatch\"," +
				"\"humidityThreshold\": 60 ," + 
				"\"humidityCritical\" : 40 ," +
				"\"wateringSeconds\" : 300 ," +
				"\"type\" : \"type\"," +
				"\"status\" : \"inactive\" ," +
				"\"humidity\" : 30}";
		
		
		String uri = "/garden/" + garden.getId() + "/patch?userId=" + Integer.toString(userID + 1);
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		HttpStatus resultStatus = HttpStatus.CONFLICT;
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort(uri, port), entity, String.class);
			resultStatus = response.getStatusCode();
		} catch (Exception e) {
			log.error("No deberia pasar");
			log.error("Exception e", e);
		}
		assertTrue(resultStatus.is4xxClientError());			

	}


	@Test
	public void testCantCreateSprinklerWithInvalidHumidityLevel(){
		// creo mock User
		Garden garden = createMockGarden();
		Integer userID = garden.getUser().getId();
		
		String jsonRequest =  "{\"patchCode\":\"testPatch\"," +
				"\"humidityThreshold\": 60 ," + 
				"\"humidityCritical\" : 90 ," +
				"\"wateringSeconds\" : 300 ," +
				"\"type\" : \"type\"," +
				"\"status\" : \"inactive\" ," +
				"\"humidity\" : 30}";
		
		
		String uri = "/garden/" + garden.getId() + "/patch?userId=" + userID;
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		HttpStatus resultStatus = HttpStatus.CONFLICT;
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort(uri, port), entity, String.class);
			resultStatus = response.getStatusCode();
		} catch (Exception e) {
			log.error("No deberia pasar");
			log.error("Exception e", e);
		}
		assertTrue(resultStatus.is4xxClientError());			

	}
	
	@Test
	public void testCanCreateSprinklerWithNulldHumidityCriticalLevel(){
		// creo mock User
		Garden garden = createMockGarden();
		Integer userID = garden.getUser().getId();
		
		String jsonRequest =  "{\"patchCode\":\"testPatch\"," +
				"\"humidityThreshold\": 60 ," + 
				"\"wateringSeconds\" : 300 ," +
				"\"type\" : \"type\"," +
				"\"status\" : \"inactive\" ," +
				"\"humidity\" : 30}";
		
		
		String uri = "/garden/" + garden.getId() + "/patch?userId=" + userID;
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		HttpStatus resultStatus = HttpStatus.CONFLICT;
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort(uri, port), entity, String.class);
			resultStatus = response.getStatusCode();
		} catch (Exception e) {
			log.error("No deberia pasar");
			log.error("Exception e", e);
		}
		assertTrue(resultStatus.is2xxSuccessful());			
	}

	@Test
	public void testCanCreateSprinklerOnFullRequest(){
	// creo mock User
		Garden garden = createMockGarden();
		Integer userID = garden.getUser().getId();
		
		String jsonRequest =  "{\"patchCode\":\"testPatch\"," +
				"\"humidityThreshold\": 60 ," + 
				"\"humidityCritical\" : 30 ," +
				"\"wateringSeconds\" : 300 ," +
				"\"type\" : \"type\"," +
				"\"status\" : \"inactive\" ," +
				"\"humidity\" : 30}";
		
		
		String uri = "/garden/" + garden.getId() + "/patch?userId=" + userID;
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		HttpStatus resultStatus = HttpStatus.CONFLICT;
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort(uri, port), entity, String.class);
			resultStatus = response.getStatusCode();
		} catch (Exception e) {
			log.error("No deberia pasar");
			log.error("Exception e", e);
		}
		assertTrue(resultStatus.is2xxSuccessful());			

	}
	
	
}
