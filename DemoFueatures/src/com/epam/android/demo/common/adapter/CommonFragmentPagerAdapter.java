package com.epam.android.demo.common.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public abstract class CommonFragmentPagerAdapter extends FragmentStatePagerAdapter {

	private List<Fragment> fragments;
	
	public List<Fragment> getFragments() {
		return fragments;
	}

	public CommonFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		fragments = new ArrayList<Fragment>();
		initFragments();
	}
	
	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	public abstract void initFragments();
}
