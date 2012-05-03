package com.epam.android.social;

import java.io.IOException;

import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.epam.android.social.constants.FacebookConstants;
import com.epam.android.social.fragments.AddAccountsFragment;
import com.epam.android.social.helper.FacebookOAuthHelper;

public class FacebookLoginActivity extends CommonLoginActivity<FacebookOAuthHelper> {
	private FacebookOAuthHelper helper;

	@Override
	void commonPageFinished(WebView view, String url) {
		Log.d(TAG, "page finished " + url);
		try {
			if (helper.isRedirectURL(url) && !isSave) {
				isSave = true;
				helper.saveToken(url);
				webView.setVisibility(WebView.INVISIBLE);
				AddAccountsFragment.getLogin().onSuccessLogin(
						helper.getUserName(), helper.getAvatarDrawable());

				Toast.makeText(FacebookLoginActivity.this, "OK", 1).show();
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

	@Override
	String commonDoInBackground(Void... params) {
		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.getSettings().setPluginsEnabled(true);
		webView.setWebViewClient(getWebViewClient());
		helper = (FacebookOAuthHelper) getApplicationContext()
				.getSystemService(FacebookOAuthHelper.FacebookOAuthHelper);
		// try {
		String lu = "https://www.facebook.com/dialog/oauth?client_id="
				+ FacebookConstants.APP_ID
				+ "&redirect_uri=fbconnect://success&scope=user_about_me&display=touch&type=user_agent";
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

}
