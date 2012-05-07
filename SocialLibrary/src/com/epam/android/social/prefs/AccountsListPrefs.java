package com.epam.android.social.prefs;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.epam.android.common.utils.ObjectSerializer;
import com.epam.android.social.constants.AccountType;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.model.Account;

public class AccountsListPrefs {
	private static final String TAG = AccountsListPrefs.class.getSimpleName();

	private List<Account> listAccounts;

	private Context mContext;

	private static AccountsListPrefs instanse;

	private AccountsListPrefs(Context context) {
		if (instanse == null) {
			mContext = context;
			listAccounts = new ArrayList<Account>();
			restoreFromPreference();
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

	private void restoreFromPreference() {
		SharedPreferences preferences = mContext.getSharedPreferences(
				ApplicationConstants.SHARED_PREFERENSE, Context.MODE_PRIVATE);
		String desirealizedAccountList = preferences.getString(
				ApplicationConstants.ACCOUNT_LIST, null);

		try {
			@SuppressWarnings("unchecked")
			List<Account> accounts = (List<Account>) ObjectSerializer
					.deserialize(desirealizedAccountList);
			setListAccounts(accounts);
		} catch (IOException e) {
			Log.e(TAG, "error when desirialize accountList", e);
		} catch (ClassNotFoundException e) {
			Log.e(TAG, "error when conver accountList", e);
		}

	}

	private void addAcountToPreference(Account currentAccount) {
		SharedPreferences preferences = mContext.getSharedPreferences(
				ApplicationConstants.SHARED_PREFERENSE, Context.MODE_PRIVATE);
		String desirealizedAccountList = preferences.getString(
				ApplicationConstants.ACCOUNT_LIST, null);

		try {
			@SuppressWarnings("unchecked")
			List<Account> accounts = (List<Account>) ObjectSerializer
					.deserialize(desirealizedAccountList);
			accounts.add(currentAccount);
			String serializedAccountList = ObjectSerializer
					.serialize((Serializable) accounts);
			Editor editor = preferences.edit();
			editor.putString(ApplicationConstants.ACCOUNT_LIST,
					serializedAccountList);
			editor.commit();

		} catch (IOException e) {
			Log.e(TAG, "error when desirialize accountList", e);
		} catch (ClassNotFoundException e) {
			Log.e(TAG, "error when conver accountList", e);
		}
	}

	private void removeAccountFromPrederence(Account currentAccount) {
		SharedPreferences preferences = mContext.getSharedPreferences(
				ApplicationConstants.SHARED_PREFERENSE, Context.MODE_PRIVATE);
		String desirealizedAccountList = preferences.getString(
				ApplicationConstants.ACCOUNT_LIST, null);

		try {
			@SuppressWarnings("unchecked")
			List<Account> accounts = (List<Account>) ObjectSerializer
					.deserialize(desirealizedAccountList);
			accounts.remove(currentAccount);
			String serializedAccountList = ObjectSerializer
					.serialize((Serializable) accounts);
			Editor editor = preferences.edit();
			editor.putString(ApplicationConstants.ACCOUNT_LIST,
					serializedAccountList);
			editor.commit();

		} catch (IOException e) {
			Log.e(TAG, "error when desirialize accountList", e);
		} catch (ClassNotFoundException e) {
			Log.e(TAG, "error when conver accountList", e);
		}
	}

	public void addAccount(Account account) {
		if (!isContain(account)) {
			listAccounts.add(account);
			addAcountToPreference(account);
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
			removeAccountFromPrederence(account);
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

}
