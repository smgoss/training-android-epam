package com.epam.android.social;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.constants.TwitterConstants;
import com.epam.android.social.helper.OAuthHelper;

public class TwitterLoginActivity extends Activity {

	private static final String TAG = TwitterLoginActivity.class
			.getSimpleName();

	private static final String TITLE = "Please wait";

	private static final String MSG = "Loading...";

	private Intent intent;

	private OAuthHelper helper;

	private WebView webView;

	private ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_webview);

		MyAsyncTask asyncTask = new MyAsyncTask();
		asyncTask.execute(null);

	}
	
	private WebViewClient getWebViewClient() {
		return new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				Log.d(TAG, "page started " + url);
				if (helper.isTokenSaved(url)) {
					webView.setVisibility(WebView.INVISIBLE);
				}
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				Log.d(TAG, "page finished " + url);
				if (helper.isTokenSaved(url)) {
					finish();
					startActivity(intent);

				}
			}

		};

	}

	private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			webView = (WebView) findViewById(R.id.webview);
			webView.getSettings().setJavaScriptEnabled(true);
			webView.getSettings()
					.setJavaScriptCanOpenWindowsAutomatically(true);
			webView.getSettings().setPluginsEnabled(true);
			webView.setWebViewClient(getWebViewClient());
			intent = new Intent(TwitterLoginActivity.this,
					TwitterTimeLineFragmentActivity.class);
			helper = (OAuthHelper) getApplicationContext().getSystemService(
					OAuthHelper.OAuthHelper);
			try {
				webView.loadUrl(helper.getLoginUrl());
			} catch (OAuthMessageSignerException e) {
				Log.e(TAG, "OAuth Message Signer error ", e);
			} catch (OAuthNotAuthorizedException e) {
				Log.e(TAG, "OAuth Not Authorized error", e);
			} catch (OAuthExpectationFailedException e) {
				Log.e(TAG, "OAuth Expectation error ", e);
			} catch (OAuthCommunicationException e) {
				Log.e(TAG, "OAuth Communication error ", e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			hideLoading();
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

}
