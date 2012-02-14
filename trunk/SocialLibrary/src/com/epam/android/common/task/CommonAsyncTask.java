package com.epam.android.common.task;

import java.io.IOException;

import org.json.JSONException;

import android.os.AsyncTask;
import android.util.Log;

public abstract class CommonAsyncTask<T> extends AsyncTask<String, String, T> {

	private static final String TAG = CommonAsyncTask.class.getSimpleName();

	private Exception e;

	private IDelegate mDelegate;

	private String mUrl;

	public CommonAsyncTask(String url, IDelegate delegate) {
		super();
		this.mDelegate = delegate;
		this.mUrl = url;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		//mDelegate.showProgress("Loading...");
	}

	@Override
	protected T doInBackground(String... params) {
		try {
			Log.d(TAG, "back");
			return load();
		} catch (IOException e) {
			this.e = e;
			Log.e(TAG, "crash during loading data", e);
			return null;
		} catch (JSONException e1) {
			this.e = e1;
			Log.e(TAG, "crash during loading data", e1);
			return null;
		}
	}

	@Override
	protected void onPostExecute(T result) {
		super.onPostExecute(result);
		Log.d("my", "postExecute");
		mDelegate.removeTask(this);
		mDelegate.hideLoading();
		if (e == null) {
			success(result);
		} else {
			mDelegate.handleError(this, e);
		}
	}

	@Override
	protected void onCancelled() {
//		mDelegate.hideLoading();
//		mDelegate.removeTask(this);
		super.onCancelled();
	}

	
	
	
	public abstract void success(T result);

	public abstract T load() throws IOException, JSONException;

	public IDelegate getDelegate() {
		return mDelegate;
	}

	public String getUrl() {
		return mUrl;
	}

	public void start() {
		execute();
	}
	
	public boolean isCancellableOnPause() {
		return false;
	}
	
}
