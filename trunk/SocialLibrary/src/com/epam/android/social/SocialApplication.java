package com.epam.android.social;

import com.epam.android.common.CommonApplication;
import com.epam.android.common.task.AsyncTaskManager;
import com.epam.android.social.helper.OAuthHelper;

public class SocialApplication extends CommonApplication {

	private OAuthHelper oAuthHelper;
	
	@Override
	public void onCreate() {
		super.onCreate();
		oAuthHelper = OAuthHelper.getInstanse();
	}

	@Override
	public Object getSystemService(String name) {
		
		if (name.equals(OAuthHelper.OAuthHelper)) {
			return oAuthHelper;
		}
		
		return super.getSystemService(name);
	}

}
