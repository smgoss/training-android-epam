package com.epam.android.social;

import java.io.IOException;
import java.net.URLDecoder;

import org.apache.http.client.ClientProtocolException;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.epam.android.common.http.Loader;
import com.epam.android.social.api.FacebookAPI;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.constants.FacebookConstants;
import com.epam.android.social.facebook.ListStatusesActivity;
import com.epam.android.social.fragments.AddAccountsFragment;
import com.epam.android.social.helper.FacebookOAuthHelper;
import com.epam.android.social.model.Account;

public class FacebookLoginActivity extends
		CommonLoginActivity<FacebookOAuthHelper> {

	private static final String CONSUMER_KEY = "163500670443120";

	private static final String REDIRECT_URL = "fbconnect://success";

	private String s;

	@Override
	void commonPageFinished(WebView view, String url) {
		Log.d(TAG, "page finished " + url);
		try {
			if (isRedirectURL(url) && !isSave) {
				isSave = true;
				saveToken(url);
				webView.setVisibility(WebView.INVISIBLE);
				AddAccountsFragment.getLogin().onSuccessLogin(getUserName(),
						getAvatarDrawable(), getAccountType(), getToken());

				Toast.makeText(FacebookLoginActivity.this, "OK", 1).show();
				Intent intent = new Intent(getApplicationContext(),
						ListStatusesActivity.class);
				intent.putExtra("token", getToken());
				startActivity(intent);
				finish();

			}
		} catch (IOException e) {
			Log.e(TAG, "IOException", e);
		} catch (ClassNotFoundException e) {
			Log.e(TAG, "Class not found exception", e);
		}
	}

	@Override
	String commonDoInBackground(Void... params) {
		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.getSettings().setPluginsEnabled(true);
		webView.setWebViewClient(getWebViewClient());
		webView.loadUrl(getLoginUrl());
		return "";
	}

	public String getLoginUrl() {
		return "https://www.facebook.com/dialog/oauth?client_id="
				+ FacebookConstants.APP_ID + "&redirect_uri=" + REDIRECT_URL
				+ "&scope=user_about_me&display=touch&type=user_agent";
	}

	public boolean isRedirectURL(String url) {
		if (url.startsWith(REDIRECT_URL)) {
			return true;
		} else {
			return false;
		}
	}

	public static Bundle decodeUrl(String s) {
		Bundle params = new Bundle();
		if (s != null) {
			String array[] = s.split("&");
			for (String parameter : array) {
				String v[] = parameter.split("=");
				if (v.length == 2) {
					params.putString(URLDecoder.decode(v[0]),
							URLDecoder.decode(v[1]));
				}
			}
		}
		return params;
	}

	public void saveToken(String url) throws IOException,
			ClassNotFoundException {
		s = decodeUrl(url).getString("fbconnect://success#access_token");
		account = getUser();
		if (!accountsListPrefs.isContain(account)) {
			account.setToken(s);
			account.setTokenSecret(null);
			accountsListPrefs.addAccount(account);
		} else {
			Toast.makeText(
					this,
					this.getResources().getString(
							R.string.you_loggined_on_this_account),
					Toast.LENGTH_SHORT).show();
		}

	}

	private Account getUser() {
		Loader loader = Loader.get(this);
		try {
			Account oneUser = new Account(loader.execute(FacebookAPI
					.getInstance().getUser() + s),
					ApplicationConstants.FACEBOOK);
			return oneUser;
		} catch (ClientProtocolException e) {
			Log.e(TAG, "error on HTTP protocol ", e);
		} catch (IOException e) {
			Log.e(TAG, "IOException when get user info ", e);
		}

		return null;
	}

	public String getUserName() {
		return account.getUserName();
	}

	public String getAvatarDrawable() {
		return account.getProfileUrl();
	}

	public String getToken() {
		return account.getToken();
	}

	public String getAccountType() {
		return account.getAccountType();
	}

}
