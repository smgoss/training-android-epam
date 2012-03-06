package com.epam.android.social.adapter;

import android.support.v4.app.FragmentManager;

import com.epam.android.layouts.adapter.CommonFragmentPagerAdapter;
import com.epam.android.social.fragments.SearchTweetsFragment;

public class TwitterMainPagerAdapter extends CommonFragmentPagerAdapter{

	public TwitterMainPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public void initFragments() {
		getFragments().add(SearchTweetsFragment.newInstance("twitter"));
		getFragments().add(SearchTweetsFragment.newInstance("google"));
		getFragments().add(SearchTweetsFragment.newInstance("dropbox"));
	}

}
