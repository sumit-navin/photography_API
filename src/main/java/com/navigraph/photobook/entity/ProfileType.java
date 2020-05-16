package com.navigraph.photobook.entity;

import java.io.Serializable;

public enum ProfileType implements Serializable {
	USER("USER"), DBA("DBA"), ADMIN("ADMIN"), PHOTOGRAPHER("PHOTOGRAPHER");

	String userProfileType;

	private ProfileType(String userProfileType) {
		this.userProfileType = userProfileType;
	}

	public String getUserProfileType() {
		return userProfileType;
	}

}