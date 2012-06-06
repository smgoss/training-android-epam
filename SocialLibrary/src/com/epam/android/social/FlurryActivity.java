package com.epam.android.social;

import android.support.v4.app.FragmentActivity;

import com.epam.android.social.constants.ApplicationConstants;
import com.flurry.android.FlurryAgent;

public class FlurryActivity extends FragmentActivity {

	@Override
	protected void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(this, ApplicationConstants.FLURRY_KEY);
	}

	@Override
	protected void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(this);
	}

}
