package com.aut.watering.server.data;

public class WaterRequest {
	
	public String patchCode;
	public Float currentHumidity;
	public Integer nextSchedulledCheck;
	public String getPatchCode() {
		return patchCode;
	}
	public void setPatchCode(String patchCode) {
		this.patchCode = patchCode;
	}
	public Float getCurrentHumidity() {
		return currentHumidity;
	}
	public void setCurrentHumidity(Float currentHumidity) {
		this.currentHumidity = currentHumidity;
	}
	public Integer getNextSchedulledCheck() {
		return nextSchedulledCheck;
	}
	public void setNextSchedulledCheck(Integer nextSchedulledCheck) {
		this.nextSchedulledCheck = nextSchedulledCheck;
	}
	
	
}
