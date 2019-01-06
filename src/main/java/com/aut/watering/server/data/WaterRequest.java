package com.aut.watering.server.data;

public class WaterRequest {
	
	public Integer patchId;
	public Float currentHumidity;
	public String nextDateTimeCheck;
	
	public Integer getPatchId() {
		return patchId;
	}
	public void setPatchId(Integer patchId) {
		this.patchId = patchId;
	}
	public Float getCurrentHumidity() {
		return currentHumidity;
	}
	public void setCurrentHumidity(Float currentHumidity) {
		this.currentHumidity = currentHumidity;
	}
	
	public String getNextDateTimeCheck() {
		return nextDateTimeCheck;
	}
	
	public void setNextDateTimeCheck(String nextDateTimeCheck) {
		this.nextDateTimeCheck = nextDateTimeCheck;
	}
	
}
