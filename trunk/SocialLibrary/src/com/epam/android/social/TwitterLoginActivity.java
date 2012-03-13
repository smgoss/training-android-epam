package com.epam.android.social;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
	
	private Intent intent;

	private OAuthHelper helper;
	
	private WebView webView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.getSettings().setPluginsEnabled(true);
		webView.setWebViewClient(getWebViewClient());
		intent = new Intent(this, TwitterMainFragmentActivity.class);
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
	}

	private WebViewClient getWebViewClient() {
		return new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				Log.d(TAG, "page started " + url);
				if(OAuthHelper.isRedirect(url)){
					webView.setVisibility(WebView.INVISIBLE);
				}
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				Log.d(TAG, "page finished " + url);
				if (OAuthHelper.isRedirect(url)) {
					saveToken();
					finish();
					startActivity(intent);

				}
			}

		};

	}
	
	private void saveToken(){
		SharedPreferences.Editor editor = getSharedPreferences(ApplicationConstants.SHARED_PREFERENSE,Context.MODE_PRIVATE).edit();
		Log.d(TAG, "token = " + helper.getConsumer().getToken());
		Log.d(TAG, "token secret = " + helper.getConsumer().getTokenSecret());
		editor.putString(TwitterConstants.TOKEN, helper.getConsumer().getToken());
		editor.putString(TwitterConstants.TOKEN_SECRET, helper.getConsumer().getTokenSecret());
		Log.d(TAG, "editor commit = " + editor.commit());
		
		
	}
	
}
