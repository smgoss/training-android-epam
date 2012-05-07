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

	// https://graph.facebook.com/me
	public String getUser() {
		return "https://graph.facebook.com/me?fields=id,name,picture&access_token=";
	}

	public String getFullProfileInfo(String profileName) {
		return "";
	}

	public String getUserAvatar(String userScreenName) {
		return "";
	}

	// TODO it's method load only 100 users
	public String getShortProfileInfo(List<Integer> idsUser) {
		return "";
	}

	public String directMessages() {
		return "";
	}

	public String search(String searchQuery) {
		return "";
	}

	public String searchPeople(String name) {
		return "";
	}

	public String getStatuses() {
		return "https://graph.facebook.com/me/friends?access_token=";
	}

	private String updateStatus() {
		return "";
	}

	private String getUpdateProfileUrl() {
		return "";
	}

	private String getUpdateProfileAvatarUrl() {
		return "";
	}

	public HttpPost getUpdateStatusRequest(String status)
			throws UnsupportedEncodingException {
		return null;
	}

	public HttpPost getUpdateProfileRequest(String name, String description,
			String url, String location) throws UnsupportedEncodingException {
		return null;
	}

	public HttpPost getUpdateProfileAvatarRequest(Bitmap bitmap)
			throws UnsupportedEncodingException {
		return null;
	}
}
