package com.epam.android.social.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.epam.android.common.BaseArrayModelByAnnotationFragment;
import com.epam.android.social.R;
import com.epam.android.social.adapter.SearchAdapter;
import com.epam.android.social.adapter.TweetAdapter;
import com.epam.android.social.model.SearchResult;
import com.epam.android.social.model.Tweet;

public class SearchFragment extends
		BaseArrayModelByAnnotationFragmentWithCustomLoadAndSaveItems<SearchResult> {

	private static final String TAG = SearchFragment.class.getSimpleName();

	private static final String ARG_QUERY = "query";

	private List<SearchResult> currentList;

	private SearchAdapter adapter;

	private Button loadMore;

	private int loadedPage = 1;

	public static SearchFragment newInstance(String query) {
		Bundle bundle = new Bundle();
		SearchFragment fragment = new SearchFragment();
		bundle.putString(ARG_QUERY, query);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public String getUrl() {
		return getArguments().getString(ARG_QUERY) + loadedPage;
	}

	@Override
	protected void success(List<SearchResult> result) {
		if (currentList == null) {
			currentList = new ArrayList<SearchResult>();
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
		Log.d(TAG, "onCreate");
	}

	@Override
	public void setList(List<SearchResult> list) {
		adapter = new SearchAdapter(getContext(), R.layout.tweet, list);
		getListView().addFooterView(loadMore);
		getListView().setAdapter(adapter);

	}
}
