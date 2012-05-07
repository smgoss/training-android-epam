package com.epam.android.social;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

import org.apache.http.client.methods.HttpUriRequest;

import com.epam.android.common.CommonApplication;
import com.epam.android.common.http.Loader;
import com.epam.android.common.http.Loader.IRule;
import com.epam.android.social.helper.FacebookOAuthHelper;
import com.epam.android.social.helper.ImageGetHelper;
import com.epam.android.social.helper.TwitterOAuthHelper;
import com.epam.android.social.prefs.AccountsListPrefs;

public class SocialApplication extends CommonApplication {

	private TwitterOAuthHelper twitterOAuthHelper;
	private FacebookOAuthHelper facebookOAuthHelper;

	@Override
	public void onCreate() {
		super.onCreate();
		twitterOAuthHelper = TwitterOAuthHelper.newInstanse(getApplicationContext());
		facebookOAuthHelper = FacebookOAuthHelper.newInstanse(getApplicationContext());
		ImageGetHelper.newInstance(getApplicationContext());
		AccountsListPrefs.newInstanse(getApplicationContext());
		
		Loader.get(this).addRule(new IRule() {

			@Override
			public void applyRule(HttpUriRequest request) {
				if (request.getURI().toString().contains("twitter")) {
					try {
						twitterOAuthHelper.sign(request);
					} catch (OAuthMessageSignerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (OAuthExpectationFailedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (OAuthCommunicationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (OAuthNotAuthorizedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (request.getURI().toString().contains("facebook")) {
//					try {
//						facebookOAuthHelper.sign(request);
//					} catch (OAuthMessageSignerException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (OAuthExpectationFailedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (OAuthCommunicationException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (OAuthNotAuthorizedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				}
			}
			
		});
	}

	@Override
	public Object getSystemService(String name) {
		if (name.equals(TwitterOAuthHelper.OAuthHelper)) {
			return twitterOAuthHelper;
		}
		if (name.equals(facebookOAuthHelper.FacebookOAuthHelper)) {
			return facebookOAuthHelper;
		}

		return super.getSystemService(name);
	}

}
