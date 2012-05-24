package com.epam.android.social.helper;

import java.io.IOException;
import java.io.Serializable;
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
import android.util.Log;
import android.widget.Toast;

import com.epam.android.common.http.Loader;
import com.epam.android.common.utils.ObjectSerializer;
import com.epam.android.social.R;
import com.epam.android.social.api.TwitterAPI;
import com.epam.android.social.constants.AccountType;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.constants.TwitterConstants;
import com.epam.android.social.model.Account;

public class TwitterOAuthHelper {

	private static final String TAG = TwitterOAuthHelper.class.getSimpleName();

	public static final String OAuthHelper = "++OAuthHelper++";

	private static final String CONSUMER_KEY = "Iu2RgC0vqbJ3hx8Bh5AfEQ";

	private static final String CONSUMER_SECRET = "xoUH0EZRCfBj1y1Om3DtxyYNs46lOwG6tbGyNDrEQo";

	private static final String REDIRECT_URL = "http://mysite.ru";
	private static final String REQUEST_URL = "https://api.twitter.com/oauth/request_token";
	private static final String ACCESS_URL = "https://api.twitter.com/oauth/access_token";
	private static final String AUTHORIZE_URL = "https://api.twitter.com/oauth/authorize";

	private OAuthConsumer consumer;

	private OAuthProvider provider;

	private static TwitterOAuthHelper instanse;

	private Context mContext;

	private List<Account> listUsers;

	private ObjectSerializer serializer;

	private String userInfoSerialized;

	private Account account;

	private TwitterOAuthHelper(Context context) {
		if (instanse == null) {
			consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY,
					CONSUMER_SECRET);
			provider = new CommonsHttpOAuthProvider(REQUEST_URL, ACCESS_URL,
					AUTHORIZE_URL);
			mContext = context;
			listUsers = new ArrayList<Account>();
			serializer = new ObjectSerializer();
		}
		// TODO restore
	}

	public static TwitterOAuthHelper getInstanse() {
		return instanse;
	}

	public static TwitterOAuthHelper newInstanse(Context context) {
		if (instanse == null) {
			instanse = new TwitterOAuthHelper(context);
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
			for (Account user : listUsers) {
				restoreToken(user);
			}
			// for (int j = 0; j < listUsers.size(); j++) {
			// if (listUsers.get(j).getUserName().equals(userName)) {
			// restoreToken(listUsers.get(j));
			// }
			// }
		}

	}

	private void restoreToken(Account user) throws IOException,
			ClassNotFoundException {
		consumer.setTokenWithSecret(user.getToken(), user.getTokenSecret());

	}

	public String getLoginUrl() throws OAuthMessageSignerException,
			OAuthNotAuthorizedException, OAuthExpectationFailedException,
			OAuthCommunicationException {
		return provider.retrieveRequestToken(consumer, REDIRECT_URL);
	}

	public boolean isRedirectURL(String url) {
		if (url.startsWith(REDIRECT_URL)) {
			return true;
		} else {
			return false;
		}
	}

	private String getOauthVerifierFromUrl(String url) {
		return url.substring(url.indexOf(TwitterConstants.OAUTH_VERIFIER)
				+ TwitterConstants.OAUTH_VERIFIER.length());
	}

	public void sign(HttpUriRequest request)
			throws OAuthMessageSignerException,
			OAuthExpectationFailedException, OAuthCommunicationException,
			OAuthNotAuthorizedException {
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

	public void saveToken(String url) throws IOException,
			ClassNotFoundException {
		String oauthVerifier = getOauthVerifierFromUrl(url);
		setRetrieveAccessToken(oauthVerifier);
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
			account.setToken(consumer.getToken());
			account.setTokenSecret(consumer.getTokenSecret());
			account.setAccountType(AccountType.TWITTER);
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
			Account user = new Account(loader.execute(TwitterAPI.getInstance()
					.verifyCredentials()), AccountType.TWITTER);
			return user;
		} catch (ClientProtocolException e) {
			Log.e(TAG, "error on HTTP protocol ", e);
		} catch (IOException e) {
			Log.e(TAG, "IOException when get user info ", e);
		}

		return null;
	}

	public Account getAccount() {
		if (!listContainUser(account.getUserName(), listUsers)) {
			return account;
		}
		return null;

	}

	public String getUserName() {
		return account.getUserName();
	}

	public String getAvatarDrawable() {
		return account.getProfileUrl();
	}

	private boolean listContainUser(String userName, List<Account> list) {
		if (userName != null) {
			for (Account account : list) {
				if (userName.equals(account.getUserName())) {
					return true;
				}
			}
		}
		return false;
		// for (int i = 0; i < list.size(); i++) {
		// if (list.get(i).getUserName().equals(userName)) {
		// return true;
		// }
		// }
		// return false;
	}

}
