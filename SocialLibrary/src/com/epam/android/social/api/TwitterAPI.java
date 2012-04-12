package com.epam.android.social.api;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import com.epam.android.social.constants.TwitterRequestParams;

public class TwitterAPI {
	private static final String TAG = TwitterAPI.class.getSimpleName();

	private static TwitterAPI twitterAPI;

	private Hashtable<String, String> requestParams;

	public static TwitterAPI getInstance() {
		if (twitterAPI == null) {
			twitterAPI = new TwitterAPI();
		}
		return twitterAPI;
	}

	public String getRetweetedByMe() {
		return "https://api.twitter.com/1/statuses/retweeted_by_me.json?page=";
	}

	public String getRetweetOfMe() {
		return "https://api.twitter.com/1/statuses/retweets_of_me.json?";
	}

	public String getHomeTimeLine() {
		return "https://api.twitter.com/1/statuses/home_timeline.json?page=";
	}

	public String getUserTimeLine() {
		return "https://api.twitter.com/1/statuses/user_timeline.json?";
	}

	public String getReetweetByUser() {
		return "https://api.twitter.com/1/statuses/retweeted_by_user.json?screen_name=episod&page=";
	}

	public String verifyCredentials() {
		return "https://api.twitter.com/1/account/verify_credentials.json";
	}

	public String fullProgileInfo(String profileName) {
		return "https://api.twitter.com/1/users/lookup.json?screen_name="
				+ profileName + "&include_entities=true";
	}
	
	public String directMessages(){
		return "https://api.twitter.com/1/direct_messages.json?count=1&page=1";
	}

	private HttpPost generatePostRequest(Hashtable<String, String> requetParams)
			throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(
				"https://api.twitter.com/1/statuses/update.json");
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		Enumeration<String> enumeration = requetParams.keys();
		String paramKey;
		while (enumeration.hasMoreElements()) {
			paramKey = enumeration.nextElement();
			pairs.add(new BasicNameValuePair(paramKey, requetParams
					.get(paramKey)));
		}
		httpPost.setEntity(new UrlEncodedFormEntity(pairs));
		return httpPost;
	}

	public HttpPost getUpdateStatusRequest(String status)
			throws UnsupportedEncodingException {
		requestParams = new Hashtable<String, String>();
		requestParams.put(TwitterRequestParams.STATUS, status);
		return generatePostRequest(requestParams);
	}
}
