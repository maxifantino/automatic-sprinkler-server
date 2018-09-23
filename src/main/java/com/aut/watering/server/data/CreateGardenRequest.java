package com.aut.watering.server.data;

public class CreateGardenRequest {
	
	private String userId;
	private String gardenName;
	private String city;
	private String state;
	private String country;
	private String latitude;
	private String longitude;
	private String wateringTimeWindow;
	private String wateringWorkingDays;
	private String patchList;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGardenName() {
		return gardenName;
	}
	public void setGardenName(String gardenName) {
		this.gardenName = gardenName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getWateringTimeWindow() {
		return wateringTimeWindow;
	}
	public void setWateringTimeWindow(String wateringTimeWindow) {
		this.wateringTimeWindow = wateringTimeWindow;
	}
	public String getWateringWorkingDays() {
		return wateringWorkingDays;
	}
	public void setWateringWorkingDays(String wateringWorkingDays) {
		this.wateringWorkingDays = wateringWorkingDays;
	}
	
	public String getPatchList() {
		return patchList;
	}
	public void setPatchList(String patchList) {
		this.patchList = patchList;
	}
	
}
