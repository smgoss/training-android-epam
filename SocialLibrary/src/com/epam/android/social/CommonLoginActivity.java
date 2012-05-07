package com.epam.android.social;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.epam.android.social.constants.FacebookConstants;
import com.epam.android.social.fragments.AddAccountsFragment;
import com.epam.android.social.helper.FacebookOAuthHelper;
import com.epam.android.social.model.Account;
import com.epam.android.social.prefs.AccountsListPrefs;

public abstract class CommonLoginActivity<E> extends Activity {
	public static final String TAG = CommonLoginActivity.class.getSimpleName();

	private static final String TITLE = "Please wait";

	private static final String MSG = "Loading...";

	private FacebookOAuthHelper helper;

	protected WebView webView;

	private ProgressDialog mProgressDialog;

	protected boolean isSave = false;

	protected Account account;

	protected AccountsListPrefs accountsListPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_webview);
		accountsListPrefs = AccountsListPrefs.newInstanse(this);
		GetWebViewAsyncTask asyncTask = new GetWebViewAsyncTask();
		asyncTask.execute(null);

	}

	protected WebViewClient getWebViewClient() {
		return new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				commonPageFinished(view, url);
			}

		};

	}

	private class GetWebViewAsyncTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			return commonDoInBackground(params);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			hideLoading();
			if (result.length() != 0) {
				Toast.makeText(CommonLoginActivity.this, result,
						Toast.LENGTH_SHORT).show();
				finish();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showLoading();
		}

	}

	public void showLoading() {

		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(this);
			Log.d("dialog", "create" + this.toString());
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(true);
			mProgressDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					finish();
				}
			});
		} else {
			Log.d("dialog", "not null" + this.toString());
		}
		if (!mProgressDialog.isShowing() && this.getWindow() != null) {
			mProgressDialog.setTitle(TITLE);
			mProgressDialog.setMessage(MSG);
			mProgressDialog.show();
			Log.d("dialog", "show" + this.toString());
		}
	}

	public void hideLoading() {
		if (mProgressDialog != null && mProgressDialog.isShowing()
				&& !isFinishing() && this.getWindow() != null) {
			mProgressDialog.dismiss();
		}
	}

	abstract void commonPageFinished(WebView view, String url);

	abstract String commonDoInBackground(Void... params);
}
