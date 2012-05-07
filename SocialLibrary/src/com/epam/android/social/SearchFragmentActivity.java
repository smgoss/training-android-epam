package com.epam.android.social;

import java.util.List;

import com.epam.android.social.adapter.SearchPagerAdapter;
import com.epam.android.social.adapter.TwitterMainPagerAdapter;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.fragments.BottomFragment;
import com.epam.android.social.fragments.FollowingFragment;
import com.epam.android.social.fragments.SearchPeopleFragment;
import com.epam.android.social.model.Following;
import com.viewpagerindicator.PageIndicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

public class SearchFragmentActivity extends FragmentActivity {
	private static final String TAG = SearchFragmentActivity.class
			.getSimpleName();

	private ViewPager viewPager;

	private PageIndicator indicator;

	private SearchPagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter_timeline_fragment);
		viewPager = (ViewPager) findViewById(R.id.pager);
		adapter = new SearchPagerAdapter(getSupportFragmentManager(),
				getApplicationContext(), getIntent().getStringExtra(
						ApplicationConstants.ARG_QUERY));
		viewPager.setAdapter(adapter);
		indicator = (PageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(viewPager);
		
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.add(
				R.id.twitterTimeLine_bottomFragment,
				BottomFragment.newInstance(getIntent().getStringExtra(
						ApplicationConstants.ARG_PROFILE_NAME)));
		transaction.commit();

	}

	public void onItemClick(View view) {
		//TODO say about find by id
//		SearchPeopleFragment fragment = (SearchPeopleFragment) adapter.getSearchPeopleFragment();
//		setIsFollow(fragment.getPeopleList(), view.getTag());
//		fragment.getAdapter().notifyDataSetChanged();
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
