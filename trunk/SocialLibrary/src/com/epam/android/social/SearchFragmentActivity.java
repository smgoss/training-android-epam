package com.epam.android.social;

import java.util.List;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.epam.android.social.adapter.SearchPagerAdapter;
import com.epam.android.social.common.providers.SearchProvider;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.model.Following;
import com.viewpagerindicator.PageIndicator;

public class SearchFragmentActivity extends FlurryActivity {
	private static final String TAG = SearchFragmentActivity.class
			.getSimpleName();

	private ViewPager viewPager;

	private PageIndicator indicator;

	private SearchPagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
			String query = getIntent().getStringExtra(SearchManager.QUERY);
			SearchRecentSuggestions suggestions = new SearchRecentSuggestions(
					this, SearchProvider.AUTHORITY, SearchProvider.MODE);
			suggestions.saveRecentQuery(query, null);
			setContentView(R.layout.twitter_timeline_fragment);
			viewPager = (ViewPager) findViewById(R.id.pager);
			adapter = new SearchPagerAdapter(getSupportFragmentManager(),
					getApplicationContext(), getIntent().getStringExtra(
							SearchManager.QUERY).trim());
			viewPager.setAdapter(adapter);
			indicator = (PageIndicator) findViewById(R.id.indicator);
			indicator.setViewPager(viewPager);

			

		}
	}

	public void onItemClick(View view) {
		// TODO say about find by id
		// SearchPeopleFragment fragment = (SearchPeopleFragment)
		// adapter.getSearchPeopleFragment();
		// setIsFollow(fragment.getPeopleList(), view.getTag());
		// fragment.getAdapter().notifyDataSetChanged();
	}

	private void setIsFollow(List<Following> list, Object itemTag) {
	
	}
}
