package com.aut.watering.server.dto;


import java.util.List;

import com.aut.watering.server.data.Location;

public class Garden {
	
	private Integer id;
	private String name;
	private Location location;
	private List<Patch> patches;
	private String workingDays;
	private String workingTimeWindow;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public List<Patch> getPatches() {
		return patches;
	}
	public void setPatches(List<Patch> patches) {
		this.patches = patches;
	}
	public String getWorkingDays() {
		return workingDays;
	}
	public void setWorkingDays(String workingDays) {
		this.workingDays = workingDays;
	}
	public String getWorkingTimeWindow() {
		return workingTimeWindow;
	}
	public void setWorkingTimeWindow(String workingTimeWindow) {
		this.workingTimeWindow = workingTimeWindow;
	}
	
	
}
