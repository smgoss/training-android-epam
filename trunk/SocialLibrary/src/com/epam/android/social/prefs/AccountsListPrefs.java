package com.epam.android.social.prefs;

import com.epam.android.social.model.AccountPref;

import android.content.Context;
import android.content.SharedPreferences;

public class AccountsListPrefs {
	private final String PREFS_NAME = "++UserList++";
	private SharedPreferences settings;
	private Context context;
	private static AccountsListPrefs instance;
	private AccountPref account;

	private AccountsListPrefs() {
		if (this.context != null) {
			this.account.setId(settings.getString("id", ""));
			this.account.setUserName(settings.getString("name", ""));
			this.account.setUserProfileUrl(settings.getString("picture", ""));
			this.account.setType(settings.getString("type", ""));
		}
	}

	public synchronized static AccountsListPrefs getInstance() {
		if (instance == null) {
			instance = new AccountsListPrefs();
		}
		return instance;
	}

	public void commit() {
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("id", this.account.getId());
		editor.putString("name", this.account.getUserName());
		editor.putString("picture", this.account.getProfileUrl());
		editor.putString("type", this.account.getType());
		editor.commit();
	}
	
	public void commit(AccountPref account) {
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("id", account.getId());
		editor.putString("name", account.getUserName());
		editor.putString("picture", account.getProfileUrl());
		editor.putString("type", account.getType());
		editor.commit();
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
		settings = context.getSharedPreferences(PREFS_NAME, 0);
	}

}
