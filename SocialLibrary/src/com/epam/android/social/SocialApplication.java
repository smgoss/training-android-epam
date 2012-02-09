package com.epam.android.social;

import com.epam.android.common.http.HttpClient;

import android.app.Application;

public class SocialApplication extends Application {

	private HttpClient httpClient;

	@Override
	public void onCreate() {
		httpClient = new HttpClient();
	}

	@Override
	public Object getSystemService(String name) {
		if (name.equals(HttpClient.HTTP_CLIENT)) {
			return httpClient;
		}
		
		return super.getSystemService(name);
	}

}
