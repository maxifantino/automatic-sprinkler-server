package com.aut.watering.server.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.aut.watering.server.constants.Constants;

/***
 * Se encapsula el acceso a constantes a través de este servicio, para cuando
 * se ingresen las mismas en algun sistema como k/v zookeeper o similar
 *
 */

@Service
public class DynamicPropertiesService {

	private Map<String, String> constantProvider;
	
	public DynamicPropertiesService(){
		constantProvider = Constants.getInstance().getConstants();
	}
	
	public Integer getPropertyAsInteger(String key){
			// TODO: Exception management missing
		return Integer.valueOf(constantProvider.get(key));
	}
	
}
