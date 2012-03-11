package com.epam.android.social.adapter;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.support.v4.app.FragmentManager;

import com.epam.android.layouts.adapter.CommonFragmentPagerAdapter;
import com.epam.android.social.api.TwitterAPI;
import com.epam.android.social.fragments.SearchTweetsFragment;

public class TwitterMainPagerAdapter extends CommonFragmentPagerAdapter{

	
	public TwitterMainPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public void initFragments() {
//		getFragments().add(SearchTweetsFragment.newInstance("twitter"));
//		getFragments().add(SearchTweetsFragment.newInstance("google"));
//		getFragments().add(SearchTweetsFragment.newInstance("dropbox"));
		try {
			getFragments().add(SearchTweetsFragment.newInstance(TwitterAPI.getRetweetedByMe()));
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
