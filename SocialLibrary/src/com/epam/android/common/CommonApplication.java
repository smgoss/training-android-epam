package com.epam.android.common;

import android.app.Application;

import com.epam.android.common.http.HttpClient;
import com.epam.android.common.http.Loader;
import com.google.android.imageloader.ImageLoader;

public class CommonApplication extends Application {

	private HttpClient mHttpClient;
	
	private Loader mLoader;
	
	private ImageLoader mImageLoader;

	@Override
	public void onCreate() {
		mHttpClient = new HttpClient();
		mLoader = new Loader(mHttpClient);
		mImageLoader = new ImageLoader();
	}

	@Override
	public Object getSystemService(String name) {
		if (name.equals(HttpClient.HTTP_CLIENT)) {
			return mHttpClient;
		}

		if (name.equals(ImageLoader.IMAGE_LOADER_SERVICE)) {
			return mImageLoader;
		}

		if (name.equals(Loader.LOADER)) {
			return mLoader;
		}

		return super.getSystemService(name);
	}

}
