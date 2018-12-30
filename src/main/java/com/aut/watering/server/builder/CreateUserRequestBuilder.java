package com.aut.watering.server.builder;

import com.aut.watering.server.data.CreateUserRequest;

public class CreateUserRequestBuilder {
	CreateUserRequest request;
	
	public CreateUserRequestBuilder withEmail(String email) {
		request.setEmail(email);
		return this;
	}
	
	public CreateUserRequestBuilder withName(String name) {
		request.setName(name);
		return this;
	}

	public CreateUserRequestBuilder withPassword(String password) {
		request.setPassword(password);
		return this;
	}

	public CreateUserRequestBuilder withSurename(String surename) {
		request.setSurname(surename);
		return this;
	}
	
	public CreateUserRequestBuilder withUsername(String username) {
		request.setUsername(username);
		return this;
	}
	public CreateUserRequestBuilder() {
		request = new CreateUserRequest();
	}

	public CreateUserRequest build() {
		return request;
	}
}
