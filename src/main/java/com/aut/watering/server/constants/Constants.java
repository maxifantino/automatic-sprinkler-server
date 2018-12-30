package com.aut.watering.server.constants;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	
	private static Constants instance;
	private static Map<String, String> constantsMap;
	private Constants(){}
	
	public static Constants getInstance(){
		if (instance == null){
			instance = new Constants();
			buildConstantsMap();
		}
		return instance;
	}
	
	private static void buildConstantsMap(){
		constantsMap = new HashMap<String, String>();
		constantsMap.put("max.watering.time", "40");
	}
	
	public Map<String, String> getConstants(){
		return constantsMap;
	}

	
}
