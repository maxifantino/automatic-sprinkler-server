package com.aut.watering.server.enums;

public enum AvailableSprinklerStatus {
	
	INACTIVE(0, "inactive"),
	ACTIVE(1, "active");

	public int statusCode;
	public String description;
	
	private AvailableSprinklerStatus(int status, String description)
	{
		this.statusCode = status;
		this.description = description;
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
	
	public String toString() {
		return this.description;
	}
	
	static public boolean validate(String status) {
		return INACTIVE.toString().equals(status) || ACTIVE.toString().equals(status);
	}
}
