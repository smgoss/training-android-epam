package com.epam.android.social.helper;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
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
import com.epam.android.social.model.AccountPref;

public class FacebookOAuthHelper {

	private static final String TAG = FacebookOAuthHelper.class.getSimpleName();

	public static final String FacebookOAuthHelper = "++FacebookOAuthHelper++";

	private static final String CONSUMER_KEY = "163500670443120";

	private static final String CONSUMER_SECRET = "7338c02df8add901b88562f436c0a335";

	private static final String REDIRECT_URL = "fbconnect://success";
	private static final String REQUEST_URL = "https://graph.facebook.com/oauth/request_token";
	private static final String ACCESS_URL = "https://graph.facebook.com/oauth/access_token";
	private static final String AUTHORIZE_URL = "https://graph.facebook.com/oauth/authorize";
	private OAuthConsumer consumer;

	private OAuthProvider provider;

	private static FacebookOAuthHelper instanse;

	private Context mContext;

	private List<AccountPref> listUsers;

	private ObjectSerializer serializer;

	private String userInfoSerialized;

	private AccountPref user;

	private String s;

	private FacebookOAuthHelper(Context context) {
		if (instanse == null) {
			consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY,
					CONSUMER_SECRET);
			provider = new CommonsHttpOAuthProvider(REQUEST_URL, ACCESS_URL,
					AUTHORIZE_URL);
			mContext = context;
			listUsers = new ArrayList<AccountPref>();
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
			listUsers = (List<AccountPref>) serializer
					.deserialize(userInfoSerialized);
			for (int j = 0; j < listUsers.size(); j++) {
				if (listUsers.get(j).getUserName().equals(userName)) {
					restoreToken(listUsers.get(j));
				}
			}
		}

	}

	private void restoreToken(AccountPref user) throws IOException,
			ClassNotFoundException {
		consumer.setTokenWithSecret(user.getToken(), user.getTokenSecret());

	}

	public String getLoginUrl() throws OAuthMessageSignerException,
			OAuthNotAuthorizedException, OAuthExpectationFailedException,
			OAuthCommunicationException {
		return "";// provider.retrieveRequestToken(consumer, REDIRECT_URL);
					// //TODO ADDDDDD
	}

	public boolean isRedirectURL(String url) {
		if (url.startsWith(REDIRECT_URL)) {
			return true;
		} else {
			return false;
		}
	}

	private String getOauthVerifierFromUrl(String url) {
		return url.substring(url.indexOf(FacebookConstants.OAUTH_VERIFIER)
				+ FacebookConstants.OAUTH_VERIFIER.length());
	}

	public void sign(HttpUriRequest request)
			throws OAuthMessageSignerException,
			OAuthExpectationFailedException, OAuthCommunicationException,
			OAuthNotAuthorizedException {
		request.setHeader("access_token", s);
		consumer.sign(request);
	}

	private void setRetrieveAccessToken(String oauthVerifier) {
		try {
			provider.retrieveAccessToken(consumer, oauthVerifier);
		} catch (OAuthMessageSignerException e) {
			Log.d(TAG, "OAuthMessageSignerException ", e);
		} catch (OAuthNotAuthorizedException e) {
			Log.d(TAG, "OAuthNotAuthorizedException", e);
		} catch (OAuthExpectationFailedException e) {
			Log.d(TAG, "OAuthExpectationFailedException ", e);
		} catch (OAuthCommunicationException e) {
			Log.d(TAG, "OAuthCommunicationException ", e);

		}
	}

	public static Bundle decodeUrl(String s) {
		Bundle params = new Bundle();
		if (s != null) {
			String array[] = s.split("&");
			for (String parameter : array) {
				String v[] = parameter.split("=");
				if (v.length == 2) {
					params.putString(URLDecoder.decode(v[0]),
							URLDecoder.decode(v[1]));
				}
			}
		}
		return params;
	}

	public void saveToken(String url) throws IOException,
			ClassNotFoundException {
		String oauthVerifier = getOauthVerifierFromUrl(url);
		// setRetrieveAccessToken(oauthVerifier);
		SharedPreferences preferences = mContext.getSharedPreferences(
				ApplicationConstants.SHARED_PREFERENSE, Context.MODE_PRIVATE);
		userInfoSerialized = preferences.getString(
				ApplicationConstants.ACCOUNT_LIST, null);
		if (userInfoSerialized != null) {
			listUsers = (List<AccountPref>) serializer
					.deserialize(userInfoSerialized);
		}
		s = decodeUrl(url).getString("fbconnect://success#access_token");
		user = getUser();
//		if (!listContainUser(user.getUserName(), listUsers)) {
//			user.setToken(s);
//			// user.setTokenSecret(consumer.getTokenSecret());
//			listUsers.add(user);
//
//			SharedPreferences.Editor editor = mContext.getSharedPreferences(
//					ApplicationConstants.SHARED_PREFERENSE,
//					Context.MODE_PRIVATE).edit();
//			editor.putString(ApplicationConstants.ACCOUNT_LIST,
//					serializer.serialize((Serializable) listUsers));
//			editor.commit();
//		} else {
//			Toast.makeText(
//					mContext,
//					mContext.getResources().getString(
//							R.string.you_loggined_on_this_account),
//					Toast.LENGTH_SHORT).show();
//		}

	}

	private AccountPref getUser() {
		Loader loader = Loader.get(mContext);
		try {
			AccountPref oneUser = new AccountPref(loader.execute(FacebookAPI
					.getInstance().verifyCredentials() + "access_token=" + s),
					"facebook");
			return oneUser;
		} catch (ClientProtocolException e) {
			Log.e(TAG, "error on HTTP protocol ", e);
		} catch (IOException e) {
			Log.e(TAG, "IOException when get user info ", e);
		}

		return null;
	}

	public String getUserName() {
		return user.getUserName();
	}

	public String getAvatarDrawable() {
		return user.getProfileUrl();
	}

	private boolean listContainUser(String userName, List<AccountPref> list) {

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getUserName().equals(userName)) {
				return true;
			}
		}
		return false;
	}
}
