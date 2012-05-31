package com.epam.android.common.task;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.epam.android.common.http.Loader;

public abstract class HttpExecuteAsyncTask extends
		AsyncTask<String, Void, String> {

	private static final String TAG = HttpExecuteAsyncTask.class
			.getSimpleName();

	private Context mContext;

	private ProgressDialog mProgressDialog;

	private static final String TITLE = "Please wait";

	private static final String MSG = "Loading...";

	public HttpExecuteAsyncTask(Context context) {
		mContext = context;
	}

	@Override
	protected String doInBackground(String... params) {
		for (int i = 0; i < params.length; i++) {
			try {
				return Loader.get(mContext).execute(params[i]);
			} catch (ClientProtocolException e) {
				Log.e(TAG, "Error on HTTP protocol", e);
			} catch (IOException e) {
				Log.e(TAG, "crash during loading data", e);
			}
		}
		return null;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		showLoading();
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		success(result);
		hideLoading();
	}

	public abstract void success(String result);

	public void showLoading() {

		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(mContext);
			Log.d("dialog", "create" + this.toString());
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(true);

		} else {
			Log.d("dialog", "not null" + this.toString());
		}
		if (!mProgressDialog.isShowing()) {
			mProgressDialog.setTitle(TITLE);
			mProgressDialog.setMessage(MSG);
			mProgressDialog.show();
			Log.d("dialog", "show" + this.toString());
		}
	}

	public void showProgress(String textMessage) {
		if (mProgressDialog == null) {
			Log.d("dialog", "progress " + this.toString());
			showLoading();
		}
		mProgressDialog.setMessage(textMessage);
	}

	public void hideLoading() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
			Log.d("dialog", "dismiss " + this.toString());
		}
	}
}
