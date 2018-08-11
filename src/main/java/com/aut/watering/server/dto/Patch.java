package com.aut.watering.server.dto;

public class Patch {
	
	private Integer id;
	private String patchCode;
	private float humidityThreshold;
	private float criticalHumidity;
	private String type;
	private int wateringTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPatchCode() {
		return patchCode;
	}
	public void setPatchCode(String patchCode) {
		this.patchCode = patchCode;
	}
	public float getHumidityThreshold() {
		return humidityThreshold;
	}
	public void setHumidityThreshold(float humidityThreshold) {
		this.humidityThreshold = humidityThreshold;
	}
	public float getCriticalHumidity() {
		return criticalHumidity;
	}
	public void setCriticalHumidity(float criticalHumidity) {
		this.criticalHumidity = criticalHumidity;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getWateringTime() {
		return wateringTime;
	}
	public void setWateringTime(int wateringTime) {
		this.wateringTime = wateringTime;
	}
	
	
	
	
}
