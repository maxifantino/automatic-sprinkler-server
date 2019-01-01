package com.aut.watering.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import com.aut.watering.server.builder.CreateUserRequestBuilder;
import com.aut.watering.server.constants.SprinklerConstants;
import com.aut.watering.server.data.CreateGardenRequest;
import com.aut.watering.server.data.CreateUserRequest;
import com.aut.watering.server.dto.Garden;
import com.aut.watering.server.dto.User;
import com.aut.watering.server.service.GardenService;
import com.aut.watering.server.service.UserService;

public class WateringTests{
	@Autowired
	protected GardenService gardenService;
	
	@Autowired
	protected UserService userService;

	
	protected User createMockUser() {
		CreateUserRequestBuilder builder = new CreateUserRequestBuilder()
				.withUsername("elbardemoe")
				.withEmail("elbardemoe@gmail.com")
				.withPassword("pwd12345")
				.withSurename("surename")
				.withName("nombretest");
				
				CreateUserRequest createRequest = builder.build();
				User user = userService.findOrCreate
						(createRequest);
				return user;
	}

	protected Garden createMockGarden(){
		User user = createMockUser();
		Integer userId = user.getId();
		CreateGardenRequest request = getMockGardenRequest(userId);
		Garden garden = gardenService.createGarden(request, user);
		return garden;
	}

	protected CreateGardenRequest getMockGardenRequest(Integer userId) {
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

	protected HttpEntity<String> buildRequestEntity(String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		HttpEntity <String> entity= new HttpEntity<String>(json, headers);
		return entity;
	}
	

	protected String createURLWithPort(String uri, int port) {
		return "http://localhost:" + port + uri;
	}

}