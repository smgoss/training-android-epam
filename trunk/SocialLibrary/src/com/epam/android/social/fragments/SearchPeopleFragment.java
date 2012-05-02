package com.epam.android.social.fragments;

import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.epam.android.common.task.CommonAsyncTask;
import com.epam.android.social.R;
import com.epam.android.social.adapter.FollowingAdapter;
import com.epam.android.social.model.Following;

public class SearchPeopleFragment extends
		BaseArrayModelFragmentWithCustomLoad<Following> {

	public static final String TAG = SearchPeopleFragment.class.getSimpleName();
	
	private static final String ARG_QUERY = "query";

	private FollowingAdapter adapter;
	
	private List<Following> peopleList;

	public static SearchPeopleFragment newInstance(String query) {
		Bundle bundle = new Bundle();
		SearchPeopleFragment fragment = new SearchPeopleFragment();
		bundle.putString(ARG_QUERY, query);
		fragment.setArguments(bundle);
		fragment.setTargetFragment(fragment, 100);
		return fragment;
	}

	@Override
	public String getUrl() {
		return getArguments().getString(ARG_QUERY);
	}

	@Override
	protected void success(List<Following> result) {
		peopleList = result;
		adapter = new FollowingAdapter(getView().getContext(), R.layout.follow,
				peopleList);
		ListView listView = (ListView) getView().findViewById(
				R.id.array_model_list);
		listView.setAdapter(adapter);

	}

	@Override
	public int getLayoutResource() {
		return R.layout.load_array_model;
	}
	
	public List<Following> getPeopleList(){
		return peopleList;
	}
	
	public FollowingAdapter getAdapter(){
		return adapter;
	}
}
