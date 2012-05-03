package com.epam.android.social.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.epam.android.social.R;
import com.epam.android.social.adapter.SearchAdapter;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.model.SearchResult;

public class SearchTweetsFragment extends
		BaseArrayModelByAnnotationFragmentWithCustomLoadAndSaveItems<SearchResult> {

	public static final String TAG = SearchTweetsFragment.class.getSimpleName();

	private List<SearchResult> currentList;

	private SearchAdapter adapter;

	private Button loadMore;

	private int loadedPage = 1;

	public static SearchTweetsFragment newInstance(String query) {
		Bundle bundle = new Bundle();
		SearchTweetsFragment fragment = new SearchTweetsFragment();
		bundle.putString(ApplicationConstants.ARG_QUERY, query);
		fragment.setArguments(bundle);
		return fragment;
	}

	private SearchTweetsFragment(){
		
	}
	
	@Override
	public String getUrl() {
		return getArguments().getString(ApplicationConstants.ARG_QUERY) + loadedPage;
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
