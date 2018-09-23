package com.aut.watering.server.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.aut.watering.server.enums.AvailableLanguages;

@Entity
@Table(name = "user")
public class User {
	
	@Id
	@Column(name = "id")
	private Integer id;
	private String username;
	private String password;

	private String email;
	private String name;
	private String surname;
	private AvailableLanguages language;

	@OneToMany(mappedBy = "garden")
	private List<Garden> gardens;
  
	@Id
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public AvailableLanguages getLanguage() {
		return language;
	}
	public void setLanguage(AvailableLanguages language) {
		this.language = language;
	}

	public List<Garden> getGardens() {
		return gardens;
	}
	public void setGardens(List<Garden> gardens) {
		this.gardens = gardens;
	}
	
}
