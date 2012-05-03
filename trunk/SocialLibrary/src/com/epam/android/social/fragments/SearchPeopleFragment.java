package com.epam.android.social.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.epam.android.social.R;
import com.epam.android.social.adapter.FollowingAdapter;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.model.Following;

public class SearchPeopleFragment extends
		BaseArrayModelFragmentWithCustomLoadAndSaveItems<Following> {

	public static final String TAG = SearchPeopleFragment.class.getSimpleName();

	private FollowingAdapter adapter;

	private List<Following> currentList;
	
	private Button loadMore;
	
	private int loadedPage = 1;

	public static SearchPeopleFragment newInstance(String query) {
		Bundle bundle = new Bundle();
		SearchPeopleFragment fragment = new SearchPeopleFragment();
		bundle.putString(ApplicationConstants.ARG_QUERY, query);
		fragment.setArguments(bundle);
		fragment.setTargetFragment(fragment, 100);
		return fragment;
	}

	private SearchPeopleFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadMore = new Button(getContext());
		loadMore.setText("load more");
		loadMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loadedPage++;
				startTasks();

			}
		});
	}

	@Override
	public String getUrl() {
		return getArguments().getString(ApplicationConstants.ARG_QUERY) + loadedPage;
	}

	@Override
	protected void success(List<Following> result) {
		if (currentList == null) {
			currentList = new ArrayList<Following>();
			currentList.addAll(result);
			setList(currentList);
		} else {
			currentList.addAll(result);
			adapter.notifyDataSetChanged();
		}
		

	}

	@Override
	public int getLayoutResource() {
		return R.layout.load_array_model;
	}

	public List<Following> getPeopleList() {
		return currentList;
	}

	public FollowingAdapter getAdapter() {
		return adapter;
	}

	@Override
	public void setList(List<Following> list) {
		adapter = new FollowingAdapter(getContext(), R.layout.follow, list);
		getListView().addFooterView(loadMore);
		getListView().setAdapter(adapter);		
	}

	@Override
	public List<Following> getCurrentList() {
		return currentList;
	}
}
