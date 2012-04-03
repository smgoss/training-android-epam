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

import android.content.Context;

import com.epam.android.common.task.HttpPostAsyncTask;
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
		return "https://api.twitter.com/1/statuses/retweeted_by_me.json?include_entities=true&count=20";
	}

	public String getRetweetOfMe() {
		return "https://api.twitter.com/1/statuses/retweets_of_me.json?";
	}

	public String getHomeTimeLine() {
		return "https://api.twitter.com/1/statuses/home_timeline.json?";
	}

	public String getUserTimeLine() {
		return "https://api.twitter.com/1/statuses/user_timeline.json?include_entities=true&include_rts=true&screen_name=twitterapi&count=2";
	}

	public String getReetweetByUser() {
		return "https://api.twitter.com/1/statuses/retweeted_by_user.json?screen_name=episod&include_entities=true&count=19";
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
