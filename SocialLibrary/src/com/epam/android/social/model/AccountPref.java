package com.epam.android.social.model;

import java.io.Serializable;

import org.json.JSONObject;

import android.os.Parcel;

import com.epam.android.common.model.BaseModel;

public class AccountPref extends BaseModel implements Serializable {
	private String id;
	private String accountType;
	private String userName;
	private String userProfileUrl;
	private String token;
	private String tokenSecret;

	public AccountPref(String type) {
		super();
	}

	public AccountPref(JSONObject json, String accountType) {
		super(json);
		this.accountType = accountType;
		setValuesFormJson();
	}

	public AccountPref(Parcel in) {
		super(in);
		setValuesFormJson();
	}

	public AccountPref(String json, String type) {
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getUserProfileUrl() {
		return userProfileUrl;
	}

	public void setUserProfileUrl(String userProfileUrl) {
		this.userProfileUrl = userProfileUrl;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
