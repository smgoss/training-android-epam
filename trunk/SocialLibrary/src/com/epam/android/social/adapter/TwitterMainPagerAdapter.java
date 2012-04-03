package com.epam.android.social.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.epam.android.layouts.adapter.CommonFragmentPagerAdapter;
import com.epam.android.social.R;
import com.epam.android.social.api.TwitterAPI;
import com.epam.android.social.fragments.SearchTweetsFragment;
import com.viewpagerindicator.TitleProvider;

public class TwitterMainPagerAdapter extends CommonFragmentPagerAdapter implements TitleProvider{

		
	private static final String TAG = TwitterMainPagerAdapter.class.getSimpleName();
	private String [] titles ;
	public TwitterMainPagerAdapter(FragmentManager fm,Context context) {
		super(fm);
		titles = context.getResources().getStringArray(R.array.titles);
	}

	@Override
	public void initFragments() {
			getFragments().add(SearchTweetsFragment.newInstance(TwitterAPI.getInstance().getHomeTimeLine()));
			getFragments().add(SearchTweetsFragment.newInstance(TwitterAPI.getInstance().getRetweetedByMe()));
			getFragments().add(SearchTweetsFragment.newInstance(TwitterAPI.getInstance().getReetweetByUser()));
	}

	@Override
	public String getTitle(int position) {
		return titles[position%3];
	}

}
