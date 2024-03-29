package com.epam.android.social.fragments;

import java.util.List;

import android.os.Bundle;
import android.widget.BaseAdapter;

import com.epam.android.common.model.BaseModel;
import com.epam.android.social.R;
import com.epam.android.social.adapter.FollowingAdapter;
import com.epam.android.social.common.fragments.CommonTwitterFragment;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.model.Following;

public class SearchPeopleFragment extends CommonTwitterFragment<Following> {

	public static final String TAG = SearchPeopleFragment.class.getSimpleName();

	private int loadedPage = 1;

	public static SearchPeopleFragment newInstance(String query) {
		Bundle bundle = new Bundle();
		SearchPeopleFragment fragment = new SearchPeopleFragment();
		bundle.putString(ApplicationConstants.ARG_QUERY, query);
		fragment.setArguments(bundle);
		return fragment;
	}

	private SearchPeopleFragment() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseAdapter createAdapter(List<? extends BaseModel> list) {
		return new FollowingAdapter(getContext(), R.layout.follow,
				(List<Following>) list);
	}

	@Override
	public int getProgressBarResource() {
		return R.id.progress_bar_on_listView;
	}

	@Override
	public String getUrl() {
		return getArguments().getString(ApplicationConstants.ARG_QUERY)
				+ loadedPage;
	}

	@Override
	protected void success(List<Following> result) {
		super.success(result);
		loadedPage++;
	}

}
