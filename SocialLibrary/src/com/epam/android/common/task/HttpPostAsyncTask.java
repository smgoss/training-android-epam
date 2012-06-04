package com.epam.android.common.task;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.epam.android.common.http.Loader;
import com.epam.android.social.R;

public abstract class HttpPostAsyncTask extends
		AsyncTask<HttpPost, Void, String> {

	private static final String TAG = HttpPostAsyncTask.class.getSimpleName();

	private Context mContext;

	public HttpPostAsyncTask(Context context) {
		mContext = context;
	}

	@Override
	protected String doInBackground(HttpPost... params) {

		for (int i = 0; i < params.length; i++) {
			try {
				return Loader.get(mContext).post(params[i]);
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
		if (result == null) {
			Toast.makeText(
					mContext,
					mContext.getResources().getString(
							R.string.error_on_send_data), Toast.LENGTH_SHORT)
					.show();
		}

	}

}
