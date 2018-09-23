package com.aut.watering.server.dto;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.aut.watering.server.data.Location;

@Entity
@Table(name = "garden")
public class Garden {
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@Id
	@Column(name="id")
	private Integer id;
	private String name;

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
