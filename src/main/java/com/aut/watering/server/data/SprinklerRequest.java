package com.aut.watering.server.data;

public class SprinklerRequest {
	
	private String patchCode;
	private Float humidityThreshold;
	private Float humidityCritical;
	private Long wateringSeconds;
	private String type;
	private String status;
	private Float humidity;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPatchCode() {
		return patchCode;
	}
	public void setPatchCode(String patchCode) {
		this.patchCode = patchCode;
	}
	public Float getHumidityThreshold() {
		return humidityThreshold;
	}
	public void setHumidityThreshold(Float humidityThreshold) {
		this.humidityThreshold = humidityThreshold;
	}
	public Float getHumidityCritical() {
		return humidityCritical;
	}
	public void setHumidityCritical(Float humidityCritical) {
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
	public Float getHumidity() {
		return humidity;
	}
	public void setHumidity(Float humidity) {
		this.humidity = humidity;
	}

	public String toString() {
	
		return "patchCode: " + patchCode +
		",humidityThreshold: " + humidityThreshold+
		",humidityCritical: " + humidityCritical+
		",wateringSeconds: " + wateringSeconds+
		",type: " +type+
		",status: " + status+
		",humidity: "+humidity;
		
	}
}
