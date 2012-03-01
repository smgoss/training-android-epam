package com.epam.android.layouts.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.epam.android.layouts.fragments.FragmentFour;
import com.epam.android.layouts.fragments.FragmentOne;
import com.epam.android.layouts.fragments.FragmentThree;
import com.epam.android.layouts.fragments.FragmentTwo;

public class MyLayoutPagerAdapter extends CommonFragmentPagerAdapter{

	public MyLayoutPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public void initFragments() {
		Fragment fr1 = new FragmentOne();
		Fragment fr2 = new FragmentTwo();
		fragments.add(fr1);
		fragments.add(fr2);
		String foo = "foo";
		fragments.add(new FragmentThree());
		String foo1 = "foo";
		fragments.add(new FragmentOne());
		String foo2 = "foo";
		fragments.add(new FragmentFour());
	}

	
}
