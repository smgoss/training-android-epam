package com.epam.android.social;

import java.io.IOException;
import java.net.URLEncoder;

import android.app.Activity;
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

import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.constants.FacebookConstants;
import com.epam.android.social.fragments.AddAccountsFragment;
import com.epam.android.social.helper.FacebookOAuthHelper;

public class FacebookLoginActivity extends Activity {

	private static final String TAG = FacebookLoginActivity.class
			.getSimpleName();

	private static final String TITLE = "Please wait";

	private static final String MSG = "Loading...";

	private FacebookOAuthHelper helper;

	private WebView webView;

	private ProgressDialog mProgressDialog;

	private boolean isSave = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_webview);

		GetWebViewAsyncTask asyncTask = new GetWebViewAsyncTask();
		asyncTask.execute(null);

	}

	private WebViewClient getWebViewClient() {
		return new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				Log.d(TAG, "page finished " + url);
				try {
					if (helper.isRedirectURL(url) && !isSave) {
						isSave = true;
						helper.saveToken(url);
						webView.setVisibility(WebView.INVISIBLE);
						AddAccountsFragment.getLogin().onSuccessLogin(
								helper.getUserName(),
								helper.getAvatarDrawable());

						Toast.makeText(FacebookLoginActivity.this, "OK", 1)
								.show();
						// Intent intent = new Intent(getApplicationContext(),
						// TwitterTimeLineFragmentActivity.class);
						// intent.putExtra(ApplicationConstants.USER_NAME,
						// helper.getUserName());
						// startActivity(intent);
						// finish();

					}
				} catch (IOException e) {
					Log.e(TAG, "IOException", e);
				} catch (ClassNotFoundException e) {
					Log.e(TAG, "Class not found exception", e);
				}
			}

		};

	}

	private class GetWebViewAsyncTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			webView = (WebView) findViewById(R.id.webview);
			webView.getSettings().setJavaScriptEnabled(true);
			webView.getSettings()
					.setJavaScriptCanOpenWindowsAutomatically(true);
			webView.getSettings().setPluginsEnabled(true);
			webView.setWebViewClient(getWebViewClient());
			helper = (FacebookOAuthHelper) getApplicationContext()
					.getSystemService(FacebookOAuthHelper.FacebookOAuthHelper);
			// try {
			String lu = "https://www.facebook.com/dialog/oauth?client_id="+FacebookConstants.APP_ID+"&redirect_uri=fbconnect://success&scope=user_about_me&display=touch&type=user_agent";
			webView.loadUrl(lu);
			// webView.loadUrl(helper.getLoginUrl());
			// } catch (OAuthMessageSignerException e) {
			// Log.e(TAG, "OAuth Message Signer error ", e);
			// } catch (OAuthNotAuthorizedException e) {
			// Log.e(TAG, "OAuth Not Authorized error "
			// + getResources().getString(R.string.not_correct_data),
			// e);
			// return getResources().getString(R.string.not_correct_data);
			// } catch (OAuthExpectationFailedException e) {
			// Log.e(TAG, "OAuth Expectation error ", e);
			// } catch (OAuthCommunicationException e) {
			// Log.e(TAG, "OAuth Communication error ", e);
			// }
			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			hideLoading();
			if (result.length() != 0) {
				Toast.makeText(FacebookLoginActivity.this, result,
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

}