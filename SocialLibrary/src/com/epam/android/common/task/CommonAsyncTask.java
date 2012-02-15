package com.epam.android.common.task;

import java.io.IOException;

import org.json.JSONException;

import com.epam.android.common.CommonApplication;

import android.content.Intent;
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
		Log.d("my", "preExecute");
		super.onPreExecute();
		sendNotification(CommonApplication.ON_PRE_EXECUTE);

		// mDelegate.showProgress("Loading...");
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
		// mDelegate.removeTask(this);
		// mDelegate.hideLoading();
		if (e == null) {
			sendNotification(CommonApplication.ON_POST_EXECUTE, result);
			// success(result);
		} else {
			mDelegate.handleError(this, e);
		}
	}

	@Override
	protected void onCancelled() {
		// mDelegate.hideLoading();
		// mDelegate.removeTask(this);
		super.onCancelled();
	}

	@Override
	protected void onProgressUpdate(String... values) {
		sendNotification(CommonApplication.ON_PROGRESS_UPDATE,values[0]);
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
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

	protected void sendNotification(String event) {
		Intent broadcast = new Intent();
		broadcast.setAction(event);
		mDelegate.getContext().sendBroadcast(broadcast);
	}

	protected void sendNotification(String event, String text) {
		Intent broadcast = new Intent();
		broadcast.setAction(event);
		broadcast.putExtra(CommonApplication.TEXT, text);
		mDelegate.getContext().sendBroadcast(broadcast);
	}
	
	protected void sendNotification(String event, T result) {
		Intent broadcast = new Intent();
		broadcast.setAction(event);
//		broadcast.putExtra(CommonApplication.RESULT, result);
		mDelegate.getContext().sendBroadcast(broadcast);
	}

}
