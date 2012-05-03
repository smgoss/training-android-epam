package com.epam.android.social.model;

import java.io.Serializable;

import org.json.JSONObject;

import android.os.Parcel;

import com.epam.android.common.model.BaseModel;

public class FacebookUserInfo extends BaseModel implements Serializable {
	private String token;
	private String tokenSecret;
	private String userName;
	private String userProfileUrl;

	public FacebookUserInfo() {
		super();
	}

	public FacebookUserInfo(JSONObject json) {
		super(json);
		setValuesFormJson();
	}

	public FacebookUserInfo(Parcel in) {
		super(in);
		setValuesFormJson();
	}

	public FacebookUserInfo(String json) {
		super(json);
		setValuesFormJson();
	}

	public String getUserName() {
		return userName;
	}

	public String getProfileUrl() {
		return userProfileUrl;
	}

	private void setValuesFormJson() {
		userName = getString("name");
		userProfileUrl = getString("picture");
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}
}
