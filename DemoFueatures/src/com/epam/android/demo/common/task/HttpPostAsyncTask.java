package com.epam.android.demo.common.task;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.epam.android.demo.common.http.Loader;

public class HttpPostAsyncTask extends AsyncTask<HttpPost, Void, Void> {

	private static final String TAG = HttpPostAsyncTask.class.getSimpleName();

	private Context mContext;

	public HttpPostAsyncTask(Context context) {
		mContext = context;
	}

	@Override
	protected Void doInBackground(HttpPost... params) {

		for (int i = 0; i < params.length; i++) {
			try {
				Loader.get(mContext).post(params[i]);
			} catch (ClientProtocolException e) {
				Log.e(TAG, "Error on HTTP protocol", e);
			} catch (IOException e) {
				Log.e(TAG, "crash during loading data", e);
			}
		}
		return null;
	}

}
