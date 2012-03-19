package com.epam.android.social.helper;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

import org.apache.http.client.methods.HttpUriRequest;

import android.R.color;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.constants.TwitterConstants;

public class OAuthHelper {

	private static final String TAG = OAuthHelper.class.getSimpleName();

	public static final String OAuthHelper = "++OAuthHelper++";

	private static final String CONSUMER_KEY = "Iu2RgC0vqbJ3hx8Bh5AfEQ";

	private static final String CONSUMER_SECRET = "xoUH0EZRCfBj1y1Om3DtxyYNs46lOwG6tbGyNDrEQo";

	private static final String REDIRECT_URL = "http://mysite.ru";
	private static final String REQUEST_URL = "https://api.twitter.com/oauth/request_token";
	private static final String ACCESS_URL = "https://api.twitter.com/oauth/access_token";
	private static final String AUTHORIZE_URL = "https://api.twitter.com/oauth/authorize";

	private OAuthConsumer consumer;
	private OAuthProvider provider;

	private static OAuthHelper instanse;

	private Context context;

	private OAuthHelper() {
		consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		provider = new CommonsHttpOAuthProvider(REQUEST_URL, ACCESS_URL,
				AUTHORIZE_URL);
		// TODO restore
	}

	public static OAuthHelper getInstanse() {
		if (instanse == null) {
			instanse = new OAuthHelper();
		}
		return instanse;
	}

	public boolean isLogged(Context context) {
		this.context = context;
		SharedPreferences preferences = context.getSharedPreferences(
				ApplicationConstants.SHARED_PREFERENSE, Context.MODE_PRIVATE);
		if (preferences.getString(TwitterConstants.TOKEN, null) != null
				&& preferences.getString(TwitterConstants.TOKEN_SECRET, null) != null) {
			restoreToken();
			return true;
		}
		return false;
	}

	public String getLoginUrl() throws OAuthMessageSignerException,
			OAuthNotAuthorizedException, OAuthExpectationFailedException,
			OAuthCommunicationException {
		return provider.retrieveRequestToken(consumer, REDIRECT_URL);
	}

	public boolean isTokenSaved(String url) {
		// TODO rename isTokenSaved, save token
		if (url.startsWith(REDIRECT_URL)) {
			String oauthVerifier = getOauthVerifierFromUrl(url);
			setRetrieveAccessToken(oauthVerifier);
			saveToken(oauthVerifier);
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
			Log.d(TAG, "OAuthNotAuthorizedException ", e);
		} catch (OAuthExpectationFailedException e) {
			Log.d(TAG, "OAuthExpectationFailedException ", e);
		} catch (OAuthCommunicationException e) {
			Log.d(TAG, "OAuthCommunicationException ", e);
		}
	}

	private void restoreToken() {
		SharedPreferences preferences = context.getSharedPreferences(
				ApplicationConstants.SHARED_PREFERENSE, Context.MODE_PRIVATE);
		consumer.setTokenWithSecret(
				preferences.getString(TwitterConstants.TOKEN, ""),
				preferences.getString(TwitterConstants.TOKEN_SECRET, ""));
	}

	private void saveToken(String oauthVerifier) {
		SharedPreferences.Editor editor = context.getSharedPreferences(
				ApplicationConstants.SHARED_PREFERENSE, Context.MODE_PRIVATE)
				.edit();
		editor.putString(TwitterConstants.TOKEN, consumer.getToken());
		editor.putString(TwitterConstants.TOKEN_SECRET,
				consumer.getTokenSecret());
		editor.commit();

	}

}
