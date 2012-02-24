package com.epam.android.layouts.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.epam.android.layouts.fragments.FragmentFour;
import com.epam.android.layouts.fragments.FragmentOne;
import com.epam.android.layouts.fragments.FragmentThree;
import com.epam.android.layouts.fragments.FragmentTwo;

public  class CommonFragmentPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;
	public CommonFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		Fragment fr1 = new FragmentOne();
		Fragment fr2 = new FragmentTwo();
		fragments = new ArrayList<Fragment>();
		fragments.add(fr1);
		fragments.add(fr2);
		String foo = "foo";
		fragments.add(new FragmentThree());
		String foo1 = "foo";
		fragments.add(new FragmentOne());
		String foo2 = "foo";
		fragments.add(new FragmentFour());
	}

	
	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

}
