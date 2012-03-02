package com.epam.android.common.task;

import java.io.IOException;

import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public abstract class CommonAsyncTask<T> extends AsyncTask<String, String, T> {

	private static final String TAG = CommonAsyncTask.class.getSimpleName();

	public static final String ON_PRE_EXECUTE = "onPreExecute";

	public static final String ON_POST_EXECUTE = "onPostExecute";

	public static final String ON_PROGRESS_UPDATE = "onProgressUpdate";
	
	public static final String ON_ERROR = "onError";
	
	public static final String ERROR = "error";

	public static final String TEXT = "text";

	public static final String ACTIVITY_KEY = "activitykey";
	
	public static final String RESULT = "result";

	public static final String TASK_KEY = "taskKey";

	private Exception e;

	private String mUrl;
	
	private T mResult;
	
	private IDelegate mDelegate;
	
	private Context mContext;

	public CommonAsyncTask(String url, IDelegate delegate) {
		super();
		this.mDelegate = delegate;
		this.mContext = delegate.getContext();
		this.mUrl = url;
	}

	@Override
	protected void onPreExecute() {
		sendNotification(ON_PRE_EXECUTE);
		super.onPreExecute();
	}

	@Override
	protected T doInBackground(String... params) {
		try {
			return load();
		} catch (IOException e) {
			this.e = e;
			Log.e(TAG, "crash during loading data", e);
			return null;
		} catch (JSONException e1) {
			this.e = e1;
			Log.e(TAG, "crash during converting data", e1);
			return null;
		}
	}

	@Override
	protected void onPostExecute(T result) {
		super.onPostExecute(result);
		this.mResult = result;
		sendResult();
	}

	public void sendResult() {
		if (e == null) {
			sendNotification(ON_POST_EXECUTE, mResult);
		} else {
			sendNotification(ON_ERROR, e);
		}
	}

	@Override
	protected void onProgressUpdate(String... values) {
		sendNotification(ON_PROGRESS_UPDATE, values[0]);
		super.onProgressUpdate(values);
	}

	public abstract T load() throws IOException, JSONException;

//	public IDelegate getDelegate() {
//		return mDelegate;
//	}
	
	public Context getContext(){
		return mContext;
	}

	public String getUrl() {
		return mUrl;
	}
	
	public T getResult(){
		return mResult;
	}

	public void start() {
		execute();
	}

	public boolean isCancellableOnPause() {
		return true;
	}

	protected void sendNotification(String event) {
		mContext.sendBroadcast(createDefaultBroadcast(event));
	}

	protected void sendNotification(String event, String text) {
		Intent broadcast = createDefaultBroadcast(event);
		broadcast.putExtra(TEXT, text);
		mContext.sendBroadcast(broadcast);
	}

	protected void sendNotification(String event, T result) {
		Intent broadcast = createDefaultBroadcast(event);
		initIntentResult(broadcast, result);
		mContext.sendBroadcast(broadcast);
	}
	
	private void sendNotification(String event, Exception e2) {
		Intent broadcast = createDefaultBroadcast(event);
		broadcast.putExtra(ERROR, e2);
		mContext.sendBroadcast(broadcast);
	}

	private Intent createDefaultBroadcast(String event) {
		Intent broadcast = new Intent(event);
		broadcast.putExtra(TASK_KEY, mUrl);
		// TODO fix name
		broadcast.putExtra(ACTIVITY_KEY, mDelegate.getDelegateKey());
		return broadcast;
	}

	protected abstract void initIntentResult(Intent intent, T result);

}
