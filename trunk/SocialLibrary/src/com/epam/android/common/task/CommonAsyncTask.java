package com.epam.android.common.task;

import java.io.IOException;

import android.os.AsyncTask;
import android.util.Log;

public abstract class CommonAsyncTask<T> extends AsyncTask<String, Void, T>
		implements IDelegate {

	private static final String TAG = CommonAsyncTask.class.getSimpleName();

	private IOException e;

	
	public CommonAsyncTask() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		showloading();
	}

	@Override
	protected T doInBackground(String... params) {
		try {
			return load(params[0]);
		} catch (IOException e) {
			this.e = e;
			Log.d(TAG, "crash odnako");
			return null;
		}
	}

	@Override
	protected void onPostExecute(T result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		hideloading();
		if (e == null) {
			success(result);
			Log.d(TAG, "html content:" + result);
		} else {

			handleError(e);
		}
	}

	@Override
	protected void onCancelled() {
		hideloading();
		super.onCancelled();
	}

	public abstract void success(T result);

	public abstract T load(String url) throws IOException;

	

}
