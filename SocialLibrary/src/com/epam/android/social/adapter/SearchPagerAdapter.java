package com.epam.android.social.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.epam.android.common.adapter.CommonFragmentPagerAdapter;
import com.epam.android.social.R;
import com.epam.android.social.api.TwitterAPI;
import com.epam.android.social.fragments.SearchPeopleFragment;
import com.epam.android.social.fragments.SearchTweetsFragment;
import com.viewpagerindicator.TitleProvider;

public class SearchPagerAdapter extends CommonFragmentPagerAdapter implements
		TitleProvider {

	private static final String TAG = SearchPagerAdapter.class.getSimpleName();

	private String[] titles;

	private String query;

	public SearchPagerAdapter(FragmentManager fm, Context context, String query) {
		super(fm);
		titles = context.getResources().getStringArray(R.array.search_titles);
		this.query = query;
		initFragments();
	}

	@Override
	public void initFragments() {
		getFragments().add(
				SearchTweetsFragment.newInstance(TwitterAPI.getInstance().search(
						query)));
		getFragments().add(
				SearchPeopleFragment.newInstance(TwitterAPI.getInstance()
						.searchPeople(query)));
	}

	@Override
	public String getTitle(int position) {
		return titles[position];
	}
	
	public Fragment getSearchPeopleFragment(){
		return getFragments().get(1);
	}
}
