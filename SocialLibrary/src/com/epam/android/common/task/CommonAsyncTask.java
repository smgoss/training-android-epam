package com.epam.android.common.task;

import java.io.IOException;

import android.os.AsyncTask;
import android.util.Log;

public abstract class CommonAsyncTask<T> extends AsyncTask<String, Void, T> {

	private static final String TAG = CommonAsyncTask.class.getSimpleName();

	private IOException e;

	private IDelegate mDelegate;
	
	
	public CommonAsyncTask(IDelegate delegate) {
		super();
		this.mDelegate = delegate;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mDelegate.showloading();
	}

	@Override
	protected T doInBackground(String... params) {
		try {
			return load(params[0]);
		} catch (IOException e) {
			this.e = e;
			Log.e(TAG, "crash during loading data", e);
			return null;
		}
	}

	@Override
	protected void onPostExecute(T result) {
		super.onPostExecute(result);
		mDelegate.hideloading();
		if (e == null) {
			success(result);
		} else {
			mDelegate.handleError(this, e);
		}
	}

	@Override
	protected void onCancelled() {
		mDelegate.hideloading();
		super.onCancelled();
	}

	public abstract void success(T result);

	public abstract T load(String url) throws IOException;

	

}
