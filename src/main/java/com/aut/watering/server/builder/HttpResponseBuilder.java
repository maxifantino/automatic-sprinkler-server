package com.aut.watering.server.builder;

import org.json.JSONObject;

import com.aut.watering.server.data.ServerConstants;

public class HttpResponseBuilder{
	
	private String message;
	private int httpCode;
	
	public HttpResponseBuilder withMessage(String message){
		this.message = message;
		return this;
	}
	
	public HttpResponseBuilder withHttpCode(int httpCode){
		this.httpCode = httpCode;
		return this;
	}
	
	public String toString(){
		JSONObject response = new JSONObject();
		response.put(ServerConstants.HTTP_RESPONSE_CODE, httpCode);
		response.put(ServerConstants.HTTP_RESPONSE_MESSAGE, message);
		return response.toString();
	}
	
}