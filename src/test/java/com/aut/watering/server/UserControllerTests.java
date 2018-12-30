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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.aut.watering.server.builder.CreateUserRequestBuilder;
import com.aut.watering.server.controller.UserController;
import com.aut.watering.server.data.CreateUserRequest;
import com.aut.watering.server.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@EnableAutoConfiguration
//@PropertySource("classpath:application.properties")


// @ContextConfiguration(classes = {ApplicationConfiguration.class})
@AutoConfigureTestDatabase 
public class UserControllerTests {

	final static Logger log = LoggerFactory.getLogger(UserController.class);

	@LocalServerPort
	private int port;
	
	@Autowired
	private UserService userService;
	
	private TestRestTemplate restTemplate = new TestRestTemplate();
				
	@Test
	public void getNotFoundWhenLoginWithNonexistentUser() throws Exception {
		String jsonRequest = "{\"username:\"esteusuarioNoExiste\",\"password\":\"pwd\"}";
		String uri = "/user/login";
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		HttpStatus resultStatus = HttpStatus.INTERNAL_SERVER_ERROR;
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
	public void getForbiddenWhenLoginWithWrongPassword() throws Exception {
		String jsonRequest = "{\"username:\"elbardemoe\",\"password\":\"pwderronea\"}";
		String uri = "/user/login";
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		HttpStatus resultStatus = HttpStatus.INTERNAL_SERVER_ERROR;

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
	public void doSuccessfullLoginWithexistentUser() {
		// creo el usuario
		CreateUserRequestBuilder builder = new CreateUserRequestBuilder()
		.withUsername("elbardemoe")
		.withEmail("elbardemoe@gmail.com")
		.withPassword("pwd12345")
		.withSurename("surename")
		.withName("name");
		
		CreateUserRequest createRequest = builder.build();
		log.error("Por llamar a userService");
		userService.createUser(createRequest);
		
		String jsonRequest = "{\"username\":\"elbardemoe\",\"password\":\"pwd12345\"}";
		String uri = "/user/login";
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		HttpStatus resultStatus = HttpStatus.INTERNAL_SERVER_ERROR;

		try {
			log.error("About to login");
			ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort(uri), entity, String.class);
			resultStatus = response.getStatusCode();
			log.error("Status: " + resultStatus);
		} catch (Exception e) {
			log.error("No deberia pasar");
			log.error("Exception e", e);
		}
		assertTrue(resultStatus.is2xxSuccessful());			
	}
	 
	@Test
	public void cantCreateUserWithNoUsername() throws Exception{
		String jsonRequest = "{\"name\":\"juan\",\"surname\":\"perez\",\"password\":\"pwd12345\"}";
		String uri = "/user/create";
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		HttpStatus resultStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort(uri), entity, String.class);
			resultStatus = response.getStatusCode();
		} catch (Exception e) {
			log.error("No deberia pasar");
		}
		assertTrue (resultStatus.is4xxClientError());
	}
	
	@Test
	public void cantCreateUserWithNoMail() {
		String jsonRequest = "{\"username\":\"juancito213\",\"name\":\"juan\",\"surname\":\"perez\",\"password\":\"pwd12345\"}";
		String uri = "/user/create";
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		HttpStatus resultStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort(uri), entity, String.class);
			resultStatus = response.getStatusCode();
		} catch (Exception e) {
			log.error("No deberia pasar");
		}
		assertTrue (resultStatus.is4xxClientError());
	
	}

	@Test
	public void cantCreateUserWithNoName() {
		String jsonRequest = "{\"email\":\"juancitosemail@gmail.com\", \"username\":\"juancito213\",\"surname\":\"perez\",\"password\":\"pwd12345\"}";
		String uri = "/user/create";
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		HttpStatus resultStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort(uri), entity, String.class);
			resultStatus = response.getStatusCode();
		} catch (Exception e) {
			log.error("No deberia pasar");
		}
		assertTrue (resultStatus.is4xxClientError());
	
	}
	
	@Test
	public void cantCreateUserAlreadyRegisteredBefore() {
		// creo el usuario
		CreateUserRequestBuilder builder = new CreateUserRequestBuilder()
		.withUsername("elbardemoe")
		.withEmail("elbardemoe@gmail.com")
		.withPassword("pwd12345")
		.withSurename("surename")
		.withName("name");
		
		CreateUserRequest createRequest = builder.build();
		log.error("Por llamar a userService");
		userService.createUser(createRequest);

		String jsonRequest = "{\"email\":\"elbardemoe@gmail.com\", \"elbardemoe\":\"juancito213\",\"surname\":\"perez\",\"password\":\"pwd12345\"}";
		String uri = "/user/create";
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		HttpStatus resultStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort(uri), entity, String.class);
			resultStatus = response.getStatusCode();
		} catch (Exception e) {
			log.error("No deberia pasar");
		}
		assertTrue (resultStatus.is5xxServerError());
		
	}

	@Test
	public void canCreateUserNotRegisteredBefore() {
		String jsonRequest = "{\"email\":\"elbardemoe@gmail.com\", \"elbardemoe\":\"juancito213\",\"surname\":\"perez\",\"password\":\"pwd12345\"}";
		String uri = "/user/create";
		HttpEntity<String> entity = buildRequestEntity(jsonRequest);
		HttpStatus resultStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort(uri), entity, String.class);
			resultStatus = response.getStatusCode();
		} catch (Exception e) {
			log.error("No deberia pasar");
		}
		assertTrue (resultStatus.is5xxServerError());
		
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
