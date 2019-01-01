package com.aut.watering.server.dto;


import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.aut.watering.server.helper.GardenComparator;


@Entity
@Table(name = "garden")
public class Garden implements Serializable{

	private static final long serialVersionUID = 3790456144218880847L;

	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Id
	@Column(name="id")
	@GeneratedValue
	private Integer id;
	private String name;

	@ManyToOne(fetch=FetchType.LAZY, targetEntity=Location.class)
	@JoinColumn(name="location_id")
	private Location location;
	
	@OneToMany(mappedBy = "garden")
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
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("id:" +this.getId() + ", ");
		builder.append("userId:" +this.getUser().getId() + ", ");
		builder.append("name:" +this.getName() + ", ");
		builder.append("location:" +this.getLocation().toString() + ", ");
		builder.append("workingDays:" +this.getWorkingDays() + ", ");
		builder.append("timeWindow:" +this.getWorkingTimeWindow() + ", ");
		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		Garden garden = (Garden) obj;
		
		return GardenComparator.compare(garden, this);
	}
}
