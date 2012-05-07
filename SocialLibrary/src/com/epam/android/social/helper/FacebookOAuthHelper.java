package com.epam.android.social.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.epam.android.common.utils.ObjectSerializer;
import com.epam.android.social.model.Account;
import com.epam.android.social.prefs.AccountsListPrefs;

public class FacebookOAuthHelper {

	private static final String TAG = FacebookOAuthHelper.class.getSimpleName();

	public static final String FacebookOAuthHelper = "++FacebookOAuthHelper++";

	private static final String CONSUMER_KEY = "163500670443120";

	private static final String REDIRECT_URL = "fbconnect://success";

	private static FacebookOAuthHelper instanse;

	private Context mContext;

	private List<Account> listUsers;

	private ObjectSerializer serializer;

	private String userInfoSerialized;

	private Account account;

	private String s;
	private AccountsListPrefs accountsListPrefs;

	private FacebookOAuthHelper(Context context) {
		if (instanse == null) {
			mContext = context;
			listUsers = new ArrayList<Account>();
			serializer = new ObjectSerializer();
			accountsListPrefs = AccountsListPrefs.getInstanse();
		}
	}

	public static FacebookOAuthHelper getInstanse() {
		return instanse;
	}

	public static FacebookOAuthHelper newInstanse(Context context) {
		if (instanse == null) {
			instanse = new FacebookOAuthHelper(context);
		}
		return instanse;
	}

	
}
