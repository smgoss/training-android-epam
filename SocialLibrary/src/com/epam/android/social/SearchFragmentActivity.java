package com.epam.android.social;

import java.util.List;

import com.epam.android.social.adapter.SearchPagerAdapter;
import com.epam.android.social.adapter.TwitterMainPagerAdapter;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.fragments.FollowingFragment;
import com.epam.android.social.fragments.SearchPeopleFragment;
import com.epam.android.social.model.Following;
import com.viewpagerindicator.PageIndicator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

public class SearchFragmentActivity extends FragmentActivity {
	private static final String TAG = SearchFragmentActivity.class
			.getSimpleName();

	private ViewPager viewPager;

	private PageIndicator indicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter_timeline_fragment);
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(new SearchPagerAdapter(
				getSupportFragmentManager(), getApplicationContext(),
				getIntent().getStringExtra(ApplicationConstants.QUERY)));
		indicator = (PageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(viewPager);

	}
	
	public void onItemClick(View view) {
		SearchPeopleFragment fragment = ((SearchPeopleFragment) getSupportFragmentManager()
				.findFragmentByTag(SearchPeopleFragment.TAG));
		setIsFollow(fragment.getFollowingList(), view.getTag());
		fragment.getAdapter().notifyDataSetChanged();

	}

	private void setIsFollow(List<Following> list, Object itemTag) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getIdUser().equals(itemTag)) {
				list.get(i).setIsFollow(!list.get(i).isFollow());
				break;
			}
		}
	}
}
