package com.epam.android.common;

import java.io.IOException;
import java.net.URLConnection;

import android.app.Application;
import android.graphics.Bitmap;

import com.epam.android.common.http.HttpClient;
import com.epam.android.common.http.Loader;
import com.epam.android.common.task.AsyncTaskManager;
import com.epam.android.common.utils.ImageManager;
import com.google.android.imageloader.BitmapContentHandler;
import com.google.android.imageloader.ImageLoader;

public class CommonApplication extends Application {

	private HttpClient mHttpClient;

	private Loader mLoader;

	private ImageLoader mImageLoader;

	private AsyncTaskManager mAsyncTaskManager;

	@Override
	public void onCreate() {
		mHttpClient = new HttpClient(this);
		mLoader = new Loader(mHttpClient);
		mImageLoader = new ImageLoader(new BitmapContentHandler() {

			@Override
			public Bitmap getContent(URLConnection connection)
					throws IOException {
				String absolutePath = getApplicationContext().getCacheDir()
						.getAbsolutePath();
				String path = connection.getURL().toString();
				Bitmap result = ImageManager.loadFromFile(absolutePath, path);
				if (result == null) {
					Bitmap content = super.getContent(connection);
					result = ImageManager.getRoundedCornersImage(content, 5);
					ImageManager.saveImageToFile(absolutePath, result,
							path);
				}
				return result;
			}

		}, null);

		mAsyncTaskManager = new AsyncTaskManager();

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
		if (name.equals(AsyncTaskManager.ASYNC_TASK_MANAGER)) {
			return mAsyncTaskManager;
		}

		return super.getSystemService(name);
	}

}
