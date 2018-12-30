package com.aut.watering.server.builder;

import com.aut.watering.server.dto.Location;

public class LocationBuilder {

	public Location location;

	public LocationBuilder(){
		location = new Location();
	}
	
	public LocationBuilder withLatitude(String latitude){
		location.setLatitude(Double.valueOf(latitude));
		return this;
	}	

	public LocationBuilder withLongitude(String logitude){
		location.setLongitude(Double.valueOf(logitude));
		return this;
	}	

	public LocationBuilder withCity(String city){
		location.setCity(city);
		return this;
	}	
	
	public LocationBuilder withCountry(String country){
		location.setCountry(country);
		return this;
	}
	
	public LocationBuilder withAddress(String address){
		location.setAddress(address);
		return this;
	}
	
	public Location build(){
		return location;
	}
}
