package com.aut.watering.server.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.aut.watering.server.dto.Patch;

@Service
public class SprinklerActivationService {

	public boolean checkActivation(Patch sprinkler, float currentHumidity, Date nextCheck){
		boolean result= false;
		// por ahora solo chequeamos que caiga por debajo del nivel limite.
		// TODO: IMplementar riego inteligente
		result = sprinkler.getHumidityThreshold()> currentHumidity;
		
		return result;
	}
}
