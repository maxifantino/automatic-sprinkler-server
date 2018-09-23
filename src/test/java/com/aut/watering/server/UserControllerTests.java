package com.aut.watering.server;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.RequestHeadersSpec;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.aut.watering.server.controller.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserControllerTests {

	final static Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private WebTestClient webClient;
		
	@Test
	public void getNotFoundWhenLoginWithNonexistentUser() {
		log.info("Holissss");
		String jsonRequest = "{\"username:\"esteusuarioNoExiste\",\"password\":\"pwd\"}";
		log.info("Holissss2");
		String uri = "/user/login";
		this.webClient.post().uri(uri).contentType(MediaType.APPLICATION_JSON).
		body(Mono.just(jsonRequest), String.class).exchange().expectStatus().is4xxClientError();		
		log.info("Holissss 3");
		
	}
/*
	@Test
	public void getForbiddenWhenLoginWithWrongPassword() {
		String jsonRequest = "{\"username:\"elbardemoe\",\"password\":\"pwderronea\"}";
	
	}
	
	@Test
	public void doSuccessfullLoginWithexistentUser() {
		String jsonRequest = "{\"username:\"elbardemoe\",\"password\":\"pwd12345\"}";
	
	}
	
	
	@Test
	public void cantCreateUserWithNoUsername() {
	
	}
	
	@Test
	public void cantCreateUserWithNoMail() {
	
	}

	@Test
	public void cantCreateUserWithNoName() {
	
	}
	
	@Test
	public void cantCreateUserAlreadyRegisteredBefore() {
	
	}
	
	@Test
	public void cantCreateUserWithFullRequest() {
	
	}

	@Test
	public void cantCreateUserWithNoSurname() {
	
	} */
}
