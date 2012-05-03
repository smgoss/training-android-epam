package com.epam.android.social.api;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Base64;

import com.epam.android.social.constants.TwitterRequestParams;

public class FacebookAPI {
	private static final String TAG = FacebookAPI.class.getSimpleName();

	private static FacebookAPI facebookAPI;

	private Hashtable<String, String> requestParams;

	public static FacebookAPI getInstance() {
		if (facebookAPI == null) {
			facebookAPI = new FacebookAPI();
		}
		return facebookAPI;
	}

	
//	https://graph.facebook.com/me
	public String verifyCredentials() {
		return "https://graph.facebook.com/me?fields=id,name,picture&";
	}

	public String getFullProfileInfo(String profileName) {
		return "https://api.twitter.com/1/users/lookup.json?screen_name="
				+ profileName + "&include_entities=true";
	}

	public String getUserAvatar(String userScreenName) {
		return "https://api.twitter.com/1/users/profile_image?screen_name="
				+ userScreenName + "&size=normal";
	}

	// TODO it's method load only 100 users
	public String getShortProfileInfo(List<Integer> idsUser) {

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < idsUser.size(); i++) {
			builder.append(idsUser.get(i));
			if (i + 1 != idsUser.size()) {
				builder.append(",");
			}
		}

		return "https://api.twitter.com/1/friendships/lookup.json?user_id="
				+ builder.toString();
	}

	public String directMessages() {
		return "https://api.twitter.com/1/direct_messages.json?count=1&page=1";
	}

	public String search(String searchQuery){
		return "http://search.twitter.com/search.json?q=" + searchQuery + "&rpp=20&result_type=mixed&page=";
	}
	
	public String searchPeople(String name){
		return "https://api.twitter.com/1/users/search.json?q=" + name + "&page=";
	}

	private HttpPost generatePostRequest(String request,
			Hashtable<String, String> requetParams)
			throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(request);
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

	private String updateStatus() {
		return "https://api.twitter.com/1/statuses/update.json";
	}

	private String getUpdateProfileUrl() {
		return "https://api.twitter.com/1/account/update_profile.json";
	}

	private String getUpdateProfileAvatarUrl() {
		return "https://api.twitter.com/1/account/update_profile_image.json";
	}

	public HttpPost getUpdateStatusRequest(String status)
			throws UnsupportedEncodingException {
		requestParams = new Hashtable<String, String>();
		requestParams.put(TwitterRequestParams.STATUS, status);
		return generatePostRequest(updateStatus(), requestParams);
	}

	public HttpPost getUpdateProfileRequest(String name, String description,
			String url, String location) throws UnsupportedEncodingException {
		requestParams = new Hashtable<String, String>();
		requestParams.put(TwitterRequestParams.NAME, name);
		requestParams.put(TwitterRequestParams.DESCRIPTION, description);
		requestParams.put(TwitterRequestParams.URL, url);
		requestParams.put(TwitterRequestParams.LOCATION, location);
		return generatePostRequest(getUpdateProfileUrl(), requestParams);
	}

	public HttpPost getUpdateProfileAvatarRequest(Bitmap bitmap)
			throws UnsupportedEncodingException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		if (bitmap.isRecycled())
			return null;
		bitmap.compress(CompressFormat.JPEG, 50 /*
												 * ignored for PNG
												 */, bos);
		final byte[] bitmapdata = bos.toByteArray();
		HttpPost httpPost = new HttpPost(getUpdateProfileAvatarUrl());
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair(TwitterRequestParams.IMAGE,
				Base64.encodeToString(bitmapdata, Base64.DEFAULT)));
		UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(
				nameValuePairs);
		httpPost.setEntity(urlEncodedFormEntity);

		return httpPost;
	}
}
