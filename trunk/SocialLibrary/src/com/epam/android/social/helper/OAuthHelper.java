package com.epam.android.social.helper;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;


public class OAuthHelper {

	private static final String TAG = OAuthHelper.class.getSimpleName();
	
	public static final String OAuthHelper = "++OAuthHelper++";
	
	private static final String CONSUMER_KEY = "wZI3cXjw1o8PsfDf7V9Rug";

	private static final String CONSUMER_SECRET = "kiWSez5HP42L4vsNmVIMIs7sz7svk0JEUSNQ6Mo3eV8";

	private static final String REDIRECT_URL = "http://mysite.ru";
	private static final String REQUEST_URL = "http://api.twitter.com/oauth/request_token";
	private static final String ACCESS_URL = "http://api.twitter.com/oauth/access_token";
	private static final String AUTHORIZE_URL = "http://api.twitter.com/oauth/authorize";
	
	
	private OAuthConsumer consumer;
	private OAuthProvider provider;
	
	
	public OAuthHelper(){
		consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		provider = new CommonsHttpOAuthProvider(REQUEST_URL, ACCESS_URL,AUTHORIZE_URL);
	}
	
	public static boolean isLogin(){
		return false;
	}
	
	public String getLoginUrl() throws OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthExpectationFailedException, OAuthCommunicationException{
		
		return provider.retrieveRequestToken(consumer, REDIRECT_URL);
	}
	
	public static boolean isRedirect(String url) {
		return url.startsWith(REDIRECT_URL);
	}
	
	public String sign(String request) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthNotAuthorizedException{
		consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		provider = new CommonsHttpOAuthProvider(REQUEST_URL, ACCESS_URL,AUTHORIZE_URL);
		return consumer.sign(request);
	}
	
}
