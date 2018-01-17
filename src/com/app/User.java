package com.app;

public class User {

	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	private String name;
	private String screenName; // @

	@Override
	public String toString() {
		return userId.toString();
	}

	@Override
	public int hashCode() {
		return userId.intValue();
	}

	public String print() {
		return userId + ": " + name + "<link>@" + screenName;
	}
}