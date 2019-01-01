package com.aut.watering.server.enums;

public enum AvailableSprinklerStatus {
	
	INACTIVE(0),
	ACTIVE(1);

	public int statusCode;
	
	private AvailableSprinklerStatus(int status)
	{
		this.statusCode = status;
	}
	
	public static AvailableSprinklerStatus getFromId(int id){
		return id == 0 ? INACTIVE : ACTIVE;
	}
	
	public static AvailableSprinklerStatus getFromDescription(String desc){
		return "active".equals(desc ) ? ACTIVE : INACTIVE;
	}
	
	public int getId(){
		return this.statusCode;
	}
	
}
