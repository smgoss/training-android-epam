package com.epam.android.social.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.epam.android.common.adapter.CommonFragmentPagerAdapter;
import com.epam.android.social.R;
import com.epam.android.social.TwitterTimeLineFragmentActivity;
import com.epam.android.social.api.TwitterAPI;
import com.epam.android.social.fragments.ProfileFragment;
import com.epam.android.social.fragments.SearchTweetsFragment;
import com.epam.android.social.fragments.SearchLineFragment;
import com.epam.android.social.fragments.TweetTimeLineFragment;
import com.viewpagerindicator.TitleProvider;

public class TwitterMainPagerAdapter extends CommonFragmentPagerAdapter
		implements TitleProvider {

	private static final String TAG = TwitterMainPagerAdapter.class
			.getSimpleName();

	private String[] titles;

	private String accountName;

	public TwitterMainPagerAdapter(FragmentManager fm, Context context,
			String accountName) {
		super(fm);
		titles = context.getResources().getStringArray(R.array.titles);
		this.accountName = accountName;
		initFragments();
	}

	@Override
	public void initFragments() {
		getFragments().add(
				TweetTimeLineFragment.newInstance(TwitterAPI.getInstance()
						.getHomeTimeLine(), accountName));

		getFragments().add(
				TweetTimeLineFragment.newInstance(TwitterAPI.getInstance()
						.getMentions(), accountName));

		getFragments().add(
				ProfileFragment.newInstance(accountName, accountName));

	}

	@Override
	public String getTitle(int position) {
		return titles[position];
	}

}
