package com.epam.android.social.api;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

public class TwitterAPI {
	private static final String TAG = TwitterAPI.class.getSimpleName();

	private static TwitterAPI twitterAPI;

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
		return "https://api.twitter.com/1/statuses/retweeted_by_user.json?screen_name=episod&include_entities=true";
	}

	public HttpPost updateStatus(String status)
			throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(
				"https://api.twitter.com/1/statuses/update.json");
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("status", status));
		httpPost.setEntity(new UrlEncodedFormEntity(pairs));
		return httpPost;
	}
}
