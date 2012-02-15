package com.epam.android.common;

import android.app.Application;

import com.epam.android.common.http.HttpClient;
import com.epam.android.common.http.Loader;
import com.epam.android.common.task.AsyncTaskManager;
import com.google.android.imageloader.ImageLoader;

public class CommonApplication extends Application {

	public static final String ON_PRE_EXECUTE = "onPreExecute";
	
	public static final String ON_POST_EXECUTE = "onPostExecute";
	
	public static final String ON_PROGRESS_UPDATE = "onProgressUpdate";
	
	public static final String TEXT = "text";
	
	public static final String RESULT = "result";

	
	private HttpClient mHttpClient;

	private Loader mLoader;

	private ImageLoader mImageLoader;
	
	private AsyncTaskManager mAsyncTaskManager;
    
	@Override
	public void onCreate() {
		mHttpClient = new HttpClient();
		mLoader = new Loader(mHttpClient);
		mImageLoader = new ImageLoader();
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
