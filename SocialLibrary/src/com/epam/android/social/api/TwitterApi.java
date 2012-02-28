package com.epam.android.social.api;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.content.Context;

public abstract class TwitterApi {

	private static final String TAG = TwitterApi.class.getSimpleName();
	
	private static final String CONSUMER_KEY = "wZI3cXjw1o8PsfDf7V9Rug";

	private static final String CONSUMER_SECRET = "kiWSez5HP42L4vsNmVIMIs7sz7svk0JEUSNQ6Mo3eV8";

	public static final String REDIRECT_URL = "http://mysite.ru";
	public static final String REQUEST_URL = "http://api.twitter.com/oauth/request_token";
	public static final String ACCESS_URL = "http://api.twitter.com/oauth/access_token";
	public static final String AUTHORIZE_URL = "http://api.twitter.com/oauth/authorize";
	
	
	private static OAuthConsumer consumer;
	private static OAuthProvider provider;
	
	
	public static boolean isLogin(){
		return false;
	}
	
	public static String getLoginUrl() throws OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthExpectationFailedException, OAuthCommunicationException{
		consumer = new DefaultOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		provider = new DefaultOAuthProvider(REQUEST_URL, ACCESS_URL,AUTHORIZE_URL);
		return provider.retrieveRequestToken(consumer, REDIRECT_URL);
	}
	
	public static boolean isRedirect(String url) {
		return url.startsWith(REDIRECT_URL);
	}
	
}
