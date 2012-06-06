package com.epam.android.social;

import java.io.IOException;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.epam.android.common.task.HttpExecuteAsyncTask;
import com.epam.android.social.api.TwitterAPI;
import com.epam.android.social.constants.AccountType;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.fragments.AccountsFragment;
import com.epam.android.social.helper.TwitterOAuthHelper;
import com.epam.android.social.model.Account;

public class TwitterLoginActivity extends FlurryActivity {

	private static final String TAG = TwitterLoginActivity.class
			.getSimpleName();

	private static final String TITLE = "Please wait";

	private static final String MSG = "Loading...";

	private TwitterOAuthHelper helper;

	private WebView webView;

	private ProgressDialog mProgressDialog;

	private boolean isSave = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_webview);
		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.getSettings().setPluginsEnabled(true);
		webView.setWebViewClient(getWebViewClient());
		helper = (TwitterOAuthHelper) getApplicationContext().getSystemService(
				TwitterOAuthHelper.OAuthHelper);
		GetWebViewAsyncTask asyncTask = new GetWebViewAsyncTask();
		asyncTask.execute();

	}

	private WebViewClient getWebViewClient() {
		return new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, final String url) {
				Log.d(TAG, "page finished " + url);
				if (helper.isRedirectURL(url) && !isSave) {
					isSave = true;
					webView.setVisibility(WebView.INVISIBLE);

					new HttpExecuteAsyncTask(TwitterLoginActivity.this) {

						@Override
						protected String doInBackground(String... params) {
							String oauthVerifier = TwitterOAuthHelper
									.getOauthVerifierFromUrl(url);
							TwitterOAuthHelper
									.setRetrieveAccessToken(oauthVerifier);
							return super.doInBackground(params);
						}

						@Override
						public void success(String result) {
							try {
								if (result != null) {
									Account account = new Account(result,
											AccountType.TWITTER);
									helper.setAccount(account);
									helper.saveToken(url);

									AccountsFragment.getLogin().onSuccessLogin(
											account);
									Intent intent = new Intent(
											getApplicationContext(),
											TwitterTimeLineFragmentActivity.class);
									intent.putExtra(
											ApplicationConstants.ARG_PROFILE_NAME,
											helper.getUserName());
									startActivity(intent);

								} else {
									Toast.makeText(
											getApplicationContext(),
											getApplicationContext()
													.getResources()
													.getString(
															R.string.error_on_login),
											Toast.LENGTH_SHORT).show();
								}

								finish();

							} catch (IOException e) {
								Log.e(TAG, "IOException", e);
							} catch (ClassNotFoundException e) {
								Log.e(TAG, "Class not found exception", e);
							}
						}

					}.execute(TwitterAPI.getInstance().verifyCredentials());

				}
			}

		};

	}

	private class GetWebViewAsyncTask extends AsyncTask<Void, Void, String> {

		private Exception e;

		@Override
		protected String doInBackground(Void... params) {

			try {
				return helper.getLoginUrl();
			} catch (OAuthMessageSignerException e) {
				Log.e(TAG, "OAuth Message Signer error ", e);
				this.e = e;
			} catch (OAuthNotAuthorizedException e) {
				Log.e(TAG, "OAuth Not Authorized error "
						+ getResources().getString(R.string.not_correct_data),
						e);
				this.e = e;
				return getResources().getString(R.string.not_correct_data);
			} catch (OAuthExpectationFailedException e) {
				Log.e(TAG, "OAuth Expectation error ", e);
				this.e = e;
			} catch (OAuthCommunicationException e) {
				Log.e(TAG, "OAuth Communication error ", e);
				this.e = e;
			}
			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			hideLoading();
			if (e == null) {
				webView.loadUrl(result);
			} else {
				Toast.makeText(TwitterLoginActivity.this, result,
						Toast.LENGTH_SHORT).show();
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

}
