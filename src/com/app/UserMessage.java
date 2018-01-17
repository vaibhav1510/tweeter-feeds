package com.app;

import java.sql.Timestamp;

public class UserMessage {

	private User u;
	private String message;
	private Timestamp createdAt;

	public UserMessage(User u) {
		this.u = u;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getMessage() {
		return message;
	}

	public User getU() {
		return u;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}
}
