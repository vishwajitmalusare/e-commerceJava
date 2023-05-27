package com.project.response;

import com.project.model.User;

import lombok.Data;

@Data
public class JwtAuthResponse {

	private String token;
	
	private User user;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
