package com.aut.watering.server.data;

public class LoginRequest {
	private String username;
	private String password;

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
	
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("username:");
		output.append(username);
		output.append(",");
		output.append("password:");
		output.append(password);
		return output.toString();
	}

}
