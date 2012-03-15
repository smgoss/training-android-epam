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

import android.content.Context;
import android.content.SharedPreferences;

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

	private OAuthHelper() {
		consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		provider = new CommonsHttpOAuthProvider(REQUEST_URL, ACCESS_URL,
				AUTHORIZE_URL);
		//TODO restore
	}

	public static OAuthHelper getInstanse() {
		if (instanse == null) {
			instanse = new OAuthHelper();
		}
		return instanse;
	}

	/* (non-Javadoc)
	 * @see com.mtvn.android.social.common.CommonShareClient#isLogged()
	 */
	public boolean isLogged() {
		return (consumer != null) && (consumer.getToken() != null)
				&& (consumer.getTokenSecret() != null);
	}

	
	public boolean isLogin(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				ApplicationConstants.SHARED_PREFERENSE, Context.MODE_PRIVATE);
		if (preferences.getString(TwitterConstants.TOKEN_SECRET, null) != null
				&& preferences.getString(TwitterConstants.TOKEN, null) != null) {
			consumer.setTokenWithSecret(
					preferences.getString(TwitterConstants.TOKEN, ""),
					preferences.getString(TwitterConstants.TOKEN_SECRET, ""));
			return true;
		}
		return false;

	}

	public OAuthConsumer getConsumer() {
		return consumer;
	}

	public String getLoginUrl() throws OAuthMessageSignerException,
			OAuthNotAuthorizedException, OAuthExpectationFailedException,
			OAuthCommunicationException {
		return provider.retrieveRequestToken(consumer, REDIRECT_URL);
	}

	public static boolean isRedirect(String url) {
		//TODO rename isTokenSaved, save token
		return url.startsWith(REDIRECT_URL);
	}

	public String sign(String request) throws OAuthMessageSignerException,
			OAuthExpectationFailedException, OAuthCommunicationException,
			OAuthNotAuthorizedException {
		return consumer.sign(request);
	}
	
	public void sign(HttpUriRequest request) throws OAuthMessageSignerException,
	OAuthExpectationFailedException, OAuthCommunicationException,
	OAuthNotAuthorizedException {
		consumer.sign(request);
	}

}
