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
		fragments.add(SearchTweetsFragment.newInstance("twitter"));
		fragments.add(SearchTweetsFragment.newInstance("google"));
		fragments.add(SearchTweetsFragment.newInstance("dropbox"));
	}

}
