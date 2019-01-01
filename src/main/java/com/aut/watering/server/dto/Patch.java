package com.aut.watering.server.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="patch")
public class Patch implements Serializable{

	private static final long serialVersionUID = -7094207383081555850L;
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	private String patchCode;
	private float humidityThreshold;
	private Float criticalHumidity;
	private Float humidity;
	private String type;
	private long wateringTime;
	private String status;
		
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
	public void setCriticalHumidity(Float criticalHumidity) {
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Float getHumidity() {
		return humidity;
	}
	public void setHumidity(Float humidity) {
		this.humidity = humidity;
	}
}
