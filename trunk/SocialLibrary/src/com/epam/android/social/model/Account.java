package com.epam.android.social.model;

import java.io.Serializable;

import org.json.JSONObject;

import android.os.Parcel;

import com.epam.android.common.model.BaseModel;
import com.epam.android.social.constants.AccountType;
import com.epam.android.social.constants.FacebookConstants;
import com.epam.android.social.constants.TwitterConstants;

public class Account extends BaseModel implements Serializable {
	private String id;
	private AccountType accountType;
	private String userName;
	private String profileUrl;
	private String token;
	private String tokenSecret;

	public Account(String type) {
		super();
	}

	public Account(JSONObject json, AccountType accountType) {
		super(json);
		this.accountType = accountType;
		setValuesFormJson();
	}

	public Account(Parcel in) {
		super(in);
		setValuesFormJson();
	}

	public Account(String json, AccountType accountType) {
		super(json);
		this.accountType = accountType;
		setValuesFormJson();
	}

	private void setValuesFormJson() {
		if (accountType == AccountType.FACEBOOK) {
			userName = getString(FacebookConstants.USER_NAME);
			profileUrl = getString(FacebookConstants.PROFILE_URL);
		} else if (accountType == AccountType.TWITTER) {
			userName = getString(TwitterConstants.USER_NAME);
			profileUrl = getString(TwitterConstants.PROFILE_URL);
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
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
