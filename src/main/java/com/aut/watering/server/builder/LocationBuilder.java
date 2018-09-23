package com.aut.watering.server.builder;

import com.aut.watering.server.data.Location;

public class LocationBuilder {

	public Location location;

	public LocationBuilder(){
		location = new Location();
	}
	
	public LocationBuilder withLatitude(String latitude){
		location.setLatitude(Float.valueOf(latitude));
		return this;
	}	

	public LocationBuilder withLongitude(String logitude){
		location.setLongitude(Float.valueOf(logitude));
		return this;
	}	

	public LocationBuilder withState(String state){
		location.setState(state);
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
	
	public Location build(){
		return location;
	}
}
