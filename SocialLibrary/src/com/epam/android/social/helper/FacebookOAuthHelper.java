package com.epam.android.social.helper;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

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
import com.epam.android.social.constants.AccountType;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.constants.FacebookConstants;
import com.epam.android.social.model.Account;

public class FacebookOAuthHelper {

	private static final String TAG = FacebookOAuthHelper.class.getSimpleName();

	public static final String OAuthHelper = "++FacebookOAuthHelper++";

	private static FacebookOAuthHelper instanse;

	private Context mContext;

	private List<Account> listUsers;

	private ObjectSerializer serializer;

	private String userInfoSerialized;

	private Account account;

	private String token;

	private FacebookOAuthHelper(Context context) {
		if (instanse == null) {
			mContext = context;
			listUsers = new ArrayList<Account>();
			serializer = new ObjectSerializer();
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

	public void restoreToken(String userName) throws IOException,
			ClassNotFoundException {
		SharedPreferences preferences = mContext.getSharedPreferences(
				ApplicationConstants.SHARED_PREFERENSE, Context.MODE_PRIVATE);

		userInfoSerialized = preferences.getString(
				ApplicationConstants.ACCOUNT_LIST, null);

		if (userInfoSerialized != null) {
			listUsers = (List<Account>) serializer
					.deserialize(userInfoSerialized);
			for (int j = 0; j < listUsers.size(); j++) {
				if (listUsers.get(j).getUserName().equals(userName)) {
					restoreToken(listUsers.get(j));
				}
			}
		}

	}

	private void restoreToken(Account user) throws IOException,
			ClassNotFoundException {

		// consumer.setTokenWithSecret(user.getToken(), user.getTokenSecret());

	}

	public String getLoginUrl() {
		return "https://www.facebook.com/dialog/oauth?client_id="
				+ FacebookConstants.APP_ID
				+ "&redirect_uri="
				+ FacebookConstants.REDIRECT_URL
				+ "&scope=user_about_me,user_status,friends_status&display=touch&type=user_agent";
	}

	public boolean isRedirectURL(String url) {
		if (url.startsWith(FacebookConstants.REDIRECT_URL)) {
			return true;
		} else {
			return false;
		}
	}

	public void sign(HttpUriRequest request)
			throws OAuthMessageSignerException,
			OAuthExpectationFailedException, OAuthCommunicationException,
			OAuthNotAuthorizedException {
		// consumer.sign(request);
	}

	public void saveToken(String url) throws IOException,
			ClassNotFoundException {

		token = decodeUrl(url).getString("fbconnect://success#access_token");
		SharedPreferences preferences = mContext.getSharedPreferences(
				ApplicationConstants.SHARED_PREFERENSE, Context.MODE_PRIVATE);
		userInfoSerialized = preferences.getString(
				ApplicationConstants.ACCOUNT_LIST, null);
		if (userInfoSerialized != null) {
			listUsers = (List<Account>) serializer
					.deserialize(userInfoSerialized);
		}
		account = getUser();
		if (!listContainUser(account.getUserName(), listUsers)) {
			account.setToken(token);
			account.setTokenSecret(null);
			account.setAccountType(AccountType.FACEBOOK);
			listUsers.add(account);

			SharedPreferences.Editor editor = mContext.getSharedPreferences(
					ApplicationConstants.SHARED_PREFERENSE,
					Context.MODE_PRIVATE).edit();
			editor.putString(ApplicationConstants.ACCOUNT_LIST,
					serializer.serialize((Serializable) listUsers));
			editor.commit();
		} else {
			Toast.makeText(
					mContext,
					mContext.getResources().getString(
							R.string.you_loggined_on_this_account),
					Toast.LENGTH_SHORT).show();
		}

	}

	private Account getUser() {
		Loader loader = Loader.get(mContext);
		try {
			Account oneUser = new Account(loader.execute(FacebookAPI
					.getInstance().getUser() + token), AccountType.FACEBOOK);
			return oneUser;
		} catch (ClientProtocolException e) {
			Log.e(TAG, "error on HTTP protocol ", e);
		} catch (IOException e) {
			Log.e(TAG, "IOException when get user info ", e);
		}

		return null;
	}

	

	private boolean listContainUser(String userName, List<Account> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getUserName().equals(userName)) {
				return true;
			}
		}
		return false;
	}

	private Bundle decodeUrl(String str) {
		Bundle params = new Bundle();
		if (str != null) {
			String array[] = str.split("&");
			for (String parameter : array) {
				String sstr[] = parameter.split("=");
				if (sstr.length == 2) {
					params.putString(URLDecoder.decode(sstr[0]),
							URLDecoder.decode(sstr[1]));
				}
			}
		}
		return params;
	}

	public String getToken() {
		return token;
	}
	
	public Account getAccount() {
		return account;
	}
	
	public String getUserName() {
		return account.getUserName();
	}

	public String getAvatarDrawable() {
		return account.getProfileUrl();
	}
}
