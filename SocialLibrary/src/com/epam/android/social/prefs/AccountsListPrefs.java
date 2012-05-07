package com.epam.android.social.prefs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

import com.epam.android.common.utils.ObjectSerializer;
import com.epam.android.social.constants.AccountType;
import com.epam.android.social.model.Account;

public class AccountsListPrefs {
	private final String PREFS_NAME = "++accounts++";
	private SharedPreferences settings;
	private Context context;
	private Account account;
	private Integer id;

	private List<Account> listAccounts;
	private Context mContext;

	private ObjectSerializer serializer;
	private String userInfoSerialized;
	private static AccountsListPrefs instanse;

	private AccountsListPrefs(Context context) {
		if (instanse == null) {
			mContext = context;
			listAccounts = new ArrayList<Account>();
			serializer = new ObjectSerializer();
		}
	}

	public static AccountsListPrefs getInstanse() {
		return instanse;
	}

	public static AccountsListPrefs newInstanse(Context context) {
		if (instanse == null) {
			instanse = new AccountsListPrefs(context);
		}
		return instanse;
	}

	private AccountsListPrefs() {
		if (this.context != null) {
			this.account.setId(settings.getString("id", ""));
			this.account.setUserName(settings.getString("name", ""));
			this.account.setProfileUrl(settings.getString("picture", ""));
			this.account.setToken(settings.getString("token", ""));
			this.account.setTokenSecret(settings.getString("token_secret", ""));
		}
	}

	public void commit() {
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("id", this.account.getId());
		editor.putString("name", this.account.getUserName());
		editor.putString("picture", this.account.getProfileUrl());
		editor.putString("token", this.account.getToken());
		editor.putString("token_secret", this.account.getTokenSecret());
		editor.commit();
	}

	public void commit(Account account) {
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("id", account.getId());
		editor.putString("name", account.getUserName());
		editor.putString("picture", account.getProfileUrl());
		editor.putString("token", account.getToken());
		editor.putString("token_secret", account.getTokenSecret());
		editor.commit();
	}

	public void addAccount(Account account) {
		if (!isContain(account)) {
			listAccounts.add(account);
		}
	}

	public Account getAccount(Integer id) {
		if (listAccounts.size() >= 0 || listAccounts.size() < id) {
			return listAccounts.get(id);
		}
		return null;
	}

	public void remAccount(Account account) {
		if (isContain(account)) {
			listAccounts.remove(account);
		}
	}

	public List<Account> getListAccounts() {
		return listAccounts;
	}

	public void setListAccounts(List<Account> listAccounts) {
		this.listAccounts = listAccounts;
	}

	public boolean isContain(Account account) {
		return listAccounts.contains(account);
	}

	public AccountType getAccountType(Account account) {
		return account.getAccountType();
	}

	public void restoreToken(String userName) throws IOException,
			ClassNotFoundException {

//		SharedPreferences preferences = mContext.getSharedPreferences(
//				ApplicationConstants.SHARED_PREFERENSE, Context.MODE_PRIVATE);
//
//		userInfoSerialized = preferences.getString(
//				ApplicationConstants.ACCOUNT_LIST, null);
//
//		if (userInfoSerialized != null) {
//			listUsers = (List<Account>) serializer
//					.deserialize(userInfoSerialized);
//			for (int j = 0; j < listUsers.size(); j++) {
//				if (listUsers.get(j).getUserName().equals(userName)) {
//					restoreToken(listUsers.get(j));
//				}
//			}
//		}

	}

//addACCOUNT	
//	SharedPreferences.Editor editor = mContext.getSharedPreferences(
//			ApplicationConstants.SHARED_PREFERENSE,
//			Context.MODE_PRIVATE).edit();
//	editor.putString(ApplicationConstants.ACCOUNT_LIST,
//			serializer.serialize((Serializable) listUsers));
//	editor.commit();
	
	
	
//	SharedPreferences preferences = mContext.getSharedPreferences(
//			ApplicationConstants.SHARED_PREFERENSE, Context.MODE_PRIVATE);
//	userInfoSerialized = preferences.getString(
//			ApplicationConstants.ACCOUNT_LIST, null);
//	if (userInfoSerialized != null) {
//		listUsers = (List<Account>) serializer
//				.deserialize(userInfoSerialized);
//}
	
	
	
	
//	public void restoreToken(String userName) throws IOException,
//	ClassNotFoundException {
//
//SharedPreferences preferences = mContext.getSharedPreferences(
//		ApplicationConstants.SHARED_PREFERENSE, Context.MODE_PRIVATE);
//
//userInfoSerialized = preferences.getString(
//		ApplicationConstants.ACCOUNT_LIST, null);
//
//if (userInfoSerialized != null) {
//	listUsers = (List<Account>) serializer
//			.deserialize(userInfoSerialized);
//	for (int j = 0; j < listUsers.size(); j++) {
//		if (listUsers.get(j).getUserName().equals(userName)) {
//			restoreToken(listUsers.get(j));
//		}
//	}
//}
//
//}
}
