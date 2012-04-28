package com.epam.android.social.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.epam.android.common.adapter.CommonFragmentPagerAdapter;
import com.epam.android.social.R;
import com.epam.android.social.api.TwitterAPI;
import com.epam.android.social.fragments.FollowingFragment;
import com.epam.android.social.fragments.SearchFragment;
import com.epam.android.social.fragments.SearchPeopleFragment;
import com.viewpagerindicator.TitleProvider;

public class SearchPagerAdapter extends CommonFragmentPagerAdapter implements
		TitleProvider {

	private static final String TAG = SearchPagerAdapter.class.getSimpleName();

	private String[] titles;

	private String query;

	private FragmentManager fragmentManager;

	public SearchPagerAdapter(FragmentManager fm, Context context, String query) {
		super(fm);
		titles = context.getResources().getStringArray(R.array.search_titles);
		this.query = query;
		this.fragmentManager = fm;
		initFragments();
	}

	@Override
	public void initFragments() {
		getFragments().add(
				SearchFragment.newInstance(TwitterAPI.getInstance().search(
						query)));
		getFragments().add(
				SearchPeopleFragment.newInstance(TwitterAPI.getInstance()
						.searchPeople(query)));
	}

	@Override
	public String getTitle(int position) {
		return titles[position];
	}

}
