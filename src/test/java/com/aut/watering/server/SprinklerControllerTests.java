package com.aut.watering.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import com.aut.watering.server.data.CreateGardenRequest;
import com.aut.watering.server.data.SprinklerRequest;
import com.aut.watering.server.dto.Garden;
import com.aut.watering.server.dto.Patch;
import com.aut.watering.server.dto.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase 
public class SprinklerControllerTests extends WateringTests{
	
	final Logger log = LoggerFactory.getLogger(this.getClass());

	@LocalServerPort
	private int port;
	
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
			log.error("Exception e", e);
		}
		assertTrue(resultStatus.is2xxSuccessful());			

	}
	
	
	@Test
	public void testGetSprinklerStatus() {
		Garden garden =createMockGardenWithSprinkler();
		String uri = "/garden/" + garden.getId() + "/patch?patchId=" + garden.getPatches().get(0).getId();
		String response="";
		try {
			URI getUri = new URI(createURLWithPort(uri, port));
		    response = restTemplate.getForObject(getUri, String.class);
		} catch (Exception e) {
			log.error("Exception e", e);
		}
		String expectedResponse = "{\"http_code\":200,\"http_message\":\"{\\\"response_code\\\":0,\\\"response_status\\\":\\\"inactive\\\"}\"}";
		assertEquals(expectedResponse, response);
	}
	
	
	@Test
	public void testShouldActivateSprinkler() {
		Garden garden =createMockGardenWithSprinkler();
		String uri = "/garden/" + garden.getId() + "/patch/checkActivation" ;
		ResponseEntity<String> response = null;
		Integer patchId = garden.getPatches().get(0).getId();
		String jsonRequest =  "{\"patchId\": " + patchId + "," +
				"\"currentHumidity\": 10 ," + 
				"\"nextDateTimeCheck\" : \"10-02-2019 22:10:00\"}";
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);

		try {
			response = restTemplate.postForEntity(createURLWithPort(uri, port), entity, String.class);
		} catch (Exception e) {
			log.error("Exception e", e);
		}
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("{\"http_code\":200,\"http_message\":\"{\\\"activate_sprinkler\\\":true}\"}",response.getBody());
	}
	
	@Test
	public void testShouldNotActivateSprinkler() {
		Garden garden =createMockGardenWithSprinkler();
		String uri = "/garden/" + garden.getId() + "/patch/checkActivation" ;
		ResponseEntity<String> response = null;
		Integer patchId = garden.getPatches().get(0).getId();
		String jsonRequest =  "{\"patchId\": " + patchId + "," +
				"\"currentHumidity\": 100 ," + 
				"\"nextDateTimeCheck\" : \"10-02-2019 22:10:00\"}";
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);

		try {
			response = restTemplate.postForEntity(createURLWithPort(uri, port), entity, String.class);
		} catch (Exception e) {
			log.error("Exception e", e);
		}
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("{\"http_code\":200,\"http_message\":\"{\\\"activate_sprinkler\\\":false}\"}",response.getBody());
	}

	@Test
	public void testShouldUpdateSprinklerAsInactive() {
		Garden garden =createMockGardenWithSprinkler();
		String uri = "/garden/" + garden.getId() + "/patch/checkActivation" ;
		ResponseEntity<String> response = null;
		Integer patchId = garden.getPatches().get(0).getId();
		String jsonRequest =  "{\"patchId\": " + patchId + "," +
				"\"currentHumidity\": 100 ," + 
				"\"nextDateTimeCheck\" : \"10-02-2019 22:10:00\"}";
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);

		try {
			response = restTemplate.postForEntity(createURLWithPort(uri, port), entity, String.class);
		} catch (Exception e) {
			log.error("Exception e", e);
		}
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("{\"http_code\":200,\"http_message\":\"{\\\"activate_sprinkler\\\":false}\"}",response.getBody());
	}
	
	@Test
	public void testShouldUpdateSprinklerAsActive() {
		Garden garden =createMockGardenWithSprinkler();
		String uri = "/garden/" + garden.getId() + "/patch/checkActivation" ;
		ResponseEntity<String> response = null;
		Integer patchId = garden.getPatches().get(0).getId();
		String jsonRequest =  "{\"patchId\": " + patchId + "," +
				"\"currentHumidity\": 100 ," + 
				"\"nextDateTimeCheck\" : \"10-02-2019 22:10:00\"}";
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);

		try {
			response = restTemplate.postForEntity(createURLWithPort(uri, port), entity, String.class);
		} catch (Exception e) {
			log.error("Exception e", e);
		}
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("{\"http_code\":200,\"http_message\":\"{\\\"activate_sprinkler\\\":false}\"}",response.getBody());
	}

	@Test
	public void testActivateSprinkler(){
		// creo mock User
		Garden garden = createMockGardenWithSprinkler("inactive");
		Integer userID = garden.getUser().getId();
		String jsonRequest =  "{\"patchId\": " + garden.getPatches().get(0).getId() + "," +
				"\"status\" : \"active\"}";
		
		String uri = "/garden/" + garden.getId() + "/patch?userId=" + userID;
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		try {
			URI putUrl = new URI(createURLWithPort(uri, port));
			restTemplate.put(putUrl, entity);
		} catch (Exception e) {
			log.error("Exception e", e);
		}
		Patch patch = gardenService.getGarden(garden.getId()).getPatches().get(0);
		assertEquals("active", patch.getStatus());		
	}

	@Test
	public void testCanChangeHumidity(){
		// creo mock User
		Garden garden = createMockGardenWithSprinkler("active");
		Integer userID = garden.getUser().getId();
		String jsonRequest =  "{\"patchId\": " + garden.getPatches().get(0).getId() + "," +
				"\"humidityThreshold\": 23}";
		
		String uri = "/garden/" + garden.getId() + "/patch?userId=" + userID;
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		try {
			URI putUrl = new URI(createURLWithPort(uri, port));
			restTemplate.put(putUrl, entity);
		} catch (Exception e) {
			log.error("Exception e", e);
		}
		Patch patch = gardenService.getGarden(garden.getId()).getPatches().get(0);
		assertEquals(23, patch.getHumidityThreshold().intValue());		
	}


	@Test
	public void testCantDeleteSprinklerFromAnotherUser(){
		// creo mock User
		Garden garden = createMockGardenWithSprinkler("active");
		Integer userID = garden.getUser().getId();
		
		String uri = "/garden/" + garden.getId() + "/patch?userId=" + userID +1 + "&patchId=" + garden.getPatches().get(0).getId();
		try {
			URI putUrl = new URI(createURLWithPort(uri, port));
			restTemplate.delete(putUrl);
		} catch (Exception e) {
			log.error("Exception e", e);
		}
		Garden updatedGarden = gardenService.getGarden(garden.getId());	
		assertFalse(CollectionUtils.isEmpty(updatedGarden.getPatches()));		
		assertEquals(1, updatedGarden.getPatches().size());
	}

	@Test
	public void testCanDeleteSprinkler(){
		// creo mock User
		Garden garden = createMockGardenWithSprinkler("active");
		Integer userID = garden.getUser().getId();
		String uri = "/garden/" + garden.getId() + "/patch?userId=" + userID + "&patchId=" + garden.getPatches().get(0).getId();
		try {
			URI putUrl = new URI(createURLWithPort(uri, port));
			restTemplate.delete(putUrl);
		} catch (Exception e) {
			log.error("Exception e", e);
		}
		Garden updatedGarden = gardenService.getGarden(garden.getId());
		assertTrue(CollectionUtils.isEmpty(updatedGarden.getPatches()));		
	}

	public Garden createMockGardenWithSprinkler() {
		return createMockGardenWithSprinkler("inactive");
	}
	
	public Garden createMockGardenWithSprinkler(String status) {
		Garden garden = createMockGarden();
		SprinklerRequest request = createMockSprinklerRequest(status);
		gardenService.addSprinkler(request, garden);
		// pruebo que el gardenService popule la tabla hija
		garden = gardenService.getGarden(garden.getId());
		return garden;
	}
	
	protected Garden createMockGarden(){
		User user = createMockUser();
		Integer userId = user.getId();
		CreateGardenRequest request = getMockGardenRequest(userId);
		Garden garden = gardenService.createGarden(request, user);
		return garden;
	}
	
	private SprinklerRequest createMockSprinklerRequest(String status) {
		SprinklerRequest request = new SprinklerRequest();
		request.setHumidity(50.0f);
		request.setHumidityCritical(30.0f);
		request.setHumidityThreshold(40.0f);
		request.setPatchCode("patch1234");
		request.setStatus(status);
		request.setType("not used");
		request.setWateringSeconds(200l);
		return request;
	}
	
}
