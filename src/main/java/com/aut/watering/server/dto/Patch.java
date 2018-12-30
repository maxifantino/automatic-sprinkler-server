package com.aut.watering.server.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="patch")
public class Patch implements Serializable{

	private static final long serialVersionUID = -7094207383081555850L;
	@Id
	@Column(name="id")
	private Integer id;
	private String patchCode;
	private float humidityThreshold;
	private float criticalHumidity;
	private float humidity;
	private String type;
	private long wateringTime;
	private int status;
		
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="garden_id")
	private Garden garden;
	
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
	public long getWateringTime() {
		return wateringTime;
	}
	public void setWateringTime(long wateringTime) {
		this.wateringTime = wateringTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public float getHumidity() {
		return humidity;
	}
	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}
}
