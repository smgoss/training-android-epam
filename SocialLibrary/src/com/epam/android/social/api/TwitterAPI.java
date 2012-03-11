package com.epam.android.social.api;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

import com.epam.android.social.helper.OAuthHelper;

public abstract class TwitterAPI {
	private static OAuthHelper helper;
	
	static{
		helper = OAuthHelper.getInstanse();
	}

	public static String getRetweetedByMe() throws OAuthMessageSignerException,
			OAuthExpectationFailedException, OAuthCommunicationException,
			OAuthNotAuthorizedException {
		return helper
				.sign("https://api.twitter.com/1/statuses/retweeted_by_me.json?include_entities=true&count=20");
	}
	
	public static String getRetweetOfMe() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthNotAuthorizedException{
		return helper.sign("https://api.twitter.com/1/statuses/retweets_of_me.json?include_entities=true");
	}
}
