package com.aut.watering.server.data;

public class DeleteSprinklerRequest {
	
	private Integer patchId;
	private String userId;
	
	public Integer getPatchId() {
		return patchId;
	}
	public void setPatchId(Integer patchId) {
		this.patchId = patchId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
}
