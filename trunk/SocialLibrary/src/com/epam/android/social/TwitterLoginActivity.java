package com.epam.android.social;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.epam.android.social.helper.OAuthHelper;

public class TwitterLoginActivity extends Activity {

	private static final String TAG = TwitterLoginActivity.class
			.getSimpleName();

	private Intent intent;

	private OAuthHelper oAuthHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		WebView webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.getSettings().setPluginsEnabled(true);
		webView.setWebViewClient(getWebViewClient());
		intent = new Intent(this, TwitterActivity.class);
		oAuthHelper = (OAuthHelper) getApplicationContext().getSystemService(
				OAuthHelper.OAuthHelper);
		try {
			webView.loadUrl(oAuthHelper.getLoginUrl());
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
				if (OAuthHelper.isRedirect(url)) {
					finish();
					startActivity(intent);
					
				}
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				Log.d(TAG, "page finished " + url);

				if (OAuthHelper.isRedirect(url)) {
					finish();
					startActivity(intent);
					
				}
			}

		};
	}

}
