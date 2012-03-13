package com.epam.android.social;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

import org.apache.http.client.methods.HttpUriRequest;

import com.epam.android.common.CommonApplication;
import com.epam.android.common.http.Loader;
import com.epam.android.common.http.Loader.IRule;
import com.epam.android.social.helper.OAuthHelper;

public class SocialApplication extends CommonApplication {

	private OAuthHelper oAuthHelper;
	
	@Override
	public void onCreate() {
		super.onCreate();
		oAuthHelper = OAuthHelper.getInstanse();
		Loader.get(this).addRule(new IRule() {
			
			@Override
			public void applyRule(HttpUriRequest request) {
				if (request.getURI().toString().contains("twitter")) {
					try {
						oAuthHelper.sign(request);
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
			}
			
		});
	}

	@Override
	public Object getSystemService(String name) {
		if (name.equals(OAuthHelper.OAuthHelper)) {
			return oAuthHelper;
		}
		
		return super.getSystemService(name);
	}

}
