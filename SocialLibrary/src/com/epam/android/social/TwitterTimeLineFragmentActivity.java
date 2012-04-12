package com.epam.android.social;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.epam.android.social.adapter.TwitterMainPagerAdapter;
import com.epam.android.social.constants.ApplicationConstants;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;

public class TwitterTimeLineFragmentActivity extends FragmentActivity {

	private static final String TAG = TwitterTimeLineFragmentActivity.class
			.getSimpleName();

	private ViewPager viewPager;

	private PageIndicator indicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter_timeline_fragment);
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(new TwitterMainPagerAdapter(
				getSupportFragmentManager(), getApplicationContext(),
				getIntent().getStringExtra(ApplicationConstants.USER_ID)));
		indicator = (PageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(viewPager);

	}

}
