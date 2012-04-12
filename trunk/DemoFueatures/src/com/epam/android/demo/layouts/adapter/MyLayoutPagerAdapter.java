package com.epam.android.demo.layouts.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.epam.android.demo.common.adapter.CommonFragmentPagerAdapter;
import com.epam.android.demo.layouts.fragments.FragmentFour;
import com.epam.android.demo.layouts.fragments.FragmentOne;
import com.epam.android.demo.layouts.fragments.FragmentThree;
import com.epam.android.demo.layouts.fragments.FragmentTwo;

public class MyLayoutPagerAdapter extends CommonFragmentPagerAdapter{

	public MyLayoutPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public void initFragments() {
		Fragment fr1 = new FragmentOne();
		Fragment fr2 = new FragmentTwo();
		getFragments().add(fr1);
		getFragments().add(fr2);
		String foo = "foo";
		getFragments().add(new FragmentThree());
		String foo1 = "foo";
		getFragments().add(new FragmentOne());
		String foo2 = "foo";
		getFragments().add(new FragmentFour());
	}

	
}
