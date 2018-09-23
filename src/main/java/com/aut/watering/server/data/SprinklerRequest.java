package com.aut.watering.server.data;

public class SprinklerRequest {
	
	private String patchCode;
	private Integer humidityThreshold;
	private Integer humidityCritical;
	private Long wateringSeconds;
	private String type;
	private Integer status;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPatchCode() {
		return patchCode;
	}
	public void setPatchCode(String patchCode) {
		this.patchCode = patchCode;
	}
	public Integer getHumidityThreshold() {
		return humidityThreshold;
	}
	public void setHumidityThreshold(Integer humidityThreshold) {
		this.humidityThreshold = humidityThreshold;
	}
	public Integer getHumidityCritical() {
		return humidityCritical;
	}
	public void setHumidityCritical(Integer humidityCritical) {
		this.humidityCritical = humidityCritical;
	}
	public Long getWateringSeconds() {
		return wateringSeconds;
	}
	public void setWateringSeconds(Long wateringSeconds) {
		this.wateringSeconds = wateringSeconds;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
