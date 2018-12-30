package com.aut.watering.server.data;

public class DeleteSprinklerRequest {
	
	private String patchId;
	private String userId;
	
	public String getPatchId() {
		return patchId;
	}
	public void setPatchId(String patchId) {
		this.patchId = patchId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
}
