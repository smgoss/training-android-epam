package com.epam.android.social.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.epam.android.layouts.adapter.CommonFragmentPagerAdapter;
import com.epam.android.social.fragments.MyTweetsFragment;

public class TwitterMainPagerAdapter extends CommonFragmentPagerAdapter{

	

	public TwitterMainPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public void initFragments() {
		Fragment fragment = new MyTweetsFragment();
		fragments.add(fragment);
		fragment = new MyTweetsFragment();
		fragments.add(fragment);
	}

}
