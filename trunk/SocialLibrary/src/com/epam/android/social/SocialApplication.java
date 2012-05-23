package com.epam.android.social;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

import org.apache.http.client.methods.HttpUriRequest;

import android.util.Log;

import com.epam.android.common.CommonApplication;
import com.epam.android.common.http.Loader;
import com.epam.android.common.http.Loader.IRule;
import com.epam.android.social.helper.FacebookOAuthHelper;
import com.epam.android.social.helper.ImageManager;
import com.epam.android.social.helper.TwitterOAuthHelper;
import com.epam.android.social.prefs.AccountsListPrefs;

public class SocialApplication extends CommonApplication {

	private static final String TAG = SocialApplication.class.getSimpleName();

	private TwitterOAuthHelper twitterOAuthHelper;

	private FacebookOAuthHelper facebookOAuthHelper;

	@Override
	public void onCreate() {
		super.onCreate();
		twitterOAuthHelper = TwitterOAuthHelper
				.newInstanse(getApplicationContext());
		facebookOAuthHelper = FacebookOAuthHelper
				.newInstanse(getApplicationContext());
		ImageManager.newInstance(getApplicationContext());
		AccountsListPrefs.newInstanse(getApplicationContext());

		Loader.get(this).addRule(new IRule() {

			@Override
			public void applyRule(HttpUriRequest request) {
				if (request.getURI().toString().contains("twitter")) {
					try {
						twitterOAuthHelper.sign(request);
					} catch (OAuthMessageSignerException e) {
						Log.e(TAG, "error on signer", e);
					} catch (OAuthExpectationFailedException e) {
						Log.e(TAG, "expectation failed", e);
					} catch (OAuthCommunicationException e) {
						Log.e(TAG, "error on communication", e);
					} catch (OAuthNotAuthorizedException e) {
						Log.e(TAG, "not authrized error", e);
					}
				}

			}

		});
	}

	@Override
	public Object getSystemService(String name) {
		if (name.equals(TwitterOAuthHelper.OAuthHelper)) {
			return twitterOAuthHelper;
		}
		if (name.equals(FacebookOAuthHelper.OAuthHelper)) {
			return facebookOAuthHelper;
		}

		return super.getSystemService(name);
	}

}
