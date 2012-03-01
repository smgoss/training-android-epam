package com.epam.android.social;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.epam.android.social.adapter.TwitterMainPagerAdapter;

public class TwitterMainFragmentActivity extends FragmentActivity{

	private ViewPager viewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_main_fragment);
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(new TwitterMainPagerAdapter(
				getSupportFragmentManager()));
		viewPager.setCurrentItem(0);
	}
	

}
