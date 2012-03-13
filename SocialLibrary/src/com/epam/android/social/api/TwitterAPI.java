package com.epam.android.social.api;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.util.Log;

import com.epam.android.social.helper.OAuthHelper;

public class TwitterAPI {
	private static final String TAG = TwitterAPI.class.getSimpleName();
	
	private static OAuthHelper helper;
	
	private static TwitterAPI twitterAPI; 
	
	private TwitterAPI(){
		helper = OAuthHelper.getInstanse();
	}
	
	public static TwitterAPI getInstance() {
		if (twitterAPI == null) {
			twitterAPI = new TwitterAPI();
		}
		return twitterAPI;
	}

	public String getRetweetedByMe() throws OAuthMessageSignerException,
			OAuthExpectationFailedException, OAuthCommunicationException,
			OAuthNotAuthorizedException {
		Log.d(TAG, "token = " + helper.getConsumer().getToken());
		Log.d(TAG, "token secret = " + helper.getConsumer().getTokenSecret());
		return "https://api.twitter.com/1/statuses/retweeted_by_me.json?include_entities=true&count=20"; //helper.sign("https://api.twitter.com/1/statuses/retweeted_by_me.json?include_entities=true&count=20");
	}
	
	public String getRetweetOfMe() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthNotAuthorizedException{
		return helper.sign("https://api.twitter.com/1/statuses/retweets_of_me.json?include_entities=true");
	}
	
	public String getHomeTimeLine() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthNotAuthorizedException{
		return helper.sign("https://api.twitter.com/1/statuses/home_timeline.json?include_entities=true");
	}
	
	public String getUserTimeLine() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthNotAuthorizedException{
		return helper.sign("https://api.twitter.com/1/statuses/user_timeline.json?include_entities=true&include_rts=true&screen_name=twitterapi&count=20");
	}
}
