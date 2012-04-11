package com.epam.android.common.task;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.epam.android.common.http.Loader;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public abstract class HttpExecuteAsyncTask extends AsyncTask<String, Void, String> {

	private static final String TAG = HttpExecuteAsyncTask.class
			.getSimpleName();

	private Context mContext;

	public HttpExecuteAsyncTask(Context context) {
		mContext = context;
	}

	@Override
	protected String doInBackground(String... params) {
		for (int i = 0; i < params.length; i++) {
			try {
				Loader.get(mContext).execute(params[i]);
			} catch (ClientProtocolException e) {
				Log.e(TAG, "Error on HTTP protocol", e);
			} catch (IOException e) {
				Log.e(TAG, "crash during loading data", e);
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		success(result);
	}

	public abstract void success(String result);
}
