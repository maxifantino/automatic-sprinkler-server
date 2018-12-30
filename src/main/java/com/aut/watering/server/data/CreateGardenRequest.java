package com.aut.watering.server.data;

public class CreateGardenRequest {
	
	private String gardenName;
	private String city;
	private String address;
	private String country;
	private String latitude;
	private String longitude;
	private String wateringTimeWindow;
	private String wateringWorkingDays;
	private String patchList;
	private Integer userId;
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("gardenName: " + this.gardenName + ",");
		builder.append("city: " + this.city + ",");
		builder.append("address: " + this.address + ",");
		builder.append("country: " + this.country + ",");
		builder.append("latitude: " + this.latitude + ",");
		
		builder.append("longitude: " + this.longitude + ",");
		builder.append("wateringTimeWindow: " + this.wateringTimeWindow + ",");
		builder.append("wateringWorkingDays: " + this.wateringWorkingDays + ",");
		builder.append("patchList: " + this.patchList + ",");
		builder.append("userId: " + this.userId);

		return builder.toString();
	}
}
