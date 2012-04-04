package com.epam.android.social;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.epam.android.social.adapter.TwitterMainPagerAdapter;
import com.viewpagerindicator.PageIndicator;

public class TwitterTimeLineFragmentActivity extends FragmentActivity{

	private ViewPager viewPager;
	private PageIndicator indicator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter_timeline_fragment);
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(new TwitterMainPagerAdapter(
				getSupportFragmentManager(),getApplicationContext()));
		indicator =  (PageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(viewPager);
		
	}
	

}
