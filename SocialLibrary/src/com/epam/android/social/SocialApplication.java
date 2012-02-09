package com.epam.android.social;

import com.epam.android.common.http.HttpClient;
import com.epam.android.common.model.Loader;
import com.google.android.imageloader.ImageLoader;

import android.app.Application;

public class SocialApplication extends Application {

	private HttpClient httpClient;
	private Loader loader;
	private ImageLoader imageLoader;

	@Override
	public void onCreate() {
		httpClient = new HttpClient();
		loader = new Loader(this);
		imageLoader = new ImageLoader();
	}

	@Override
	public Object getSystemService(String name) {
		if (name.equals(HttpClient.HTTP_CLIENT)) {
			return httpClient;
		}

		if (name.equals(ImageLoader.IMAGE_LOADER_SERVICE)) {
			return imageLoader;
		}

		if (name.equals(Loader.LOADER)) {
			return loader;
		}

		return super.getSystemService(name);
	}

}
