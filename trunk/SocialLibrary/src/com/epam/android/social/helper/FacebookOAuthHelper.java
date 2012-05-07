package com.epam.android.social.helper;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.epam.android.common.http.Loader;
import com.epam.android.common.utils.ObjectSerializer;
import com.epam.android.social.R;
import com.epam.android.social.api.FacebookAPI;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.constants.FacebookConstants;
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
			accountsListPrefs = AccountsListPrefs.newInstanse(context);
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
