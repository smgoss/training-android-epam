package com.epam.android.social.fragments;

import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.epam.android.common.BaseArrayModelByAnnotationFragment;
import com.epam.android.social.R;
import com.epam.android.social.adapter.TweetAdapter;
import com.epam.android.social.model.Tweet;

public class SearchTweetsFragment extends BaseArrayModelByAnnotationFragment<Tweet>{

	private static final String ARG_QUERY = "query";

	private static final String TAG = SearchTweetsFragment.class.getSimpleName();

	private static final String URL = "http://search.twitter.com/search.json?q=";

	private ListView mListView;

	public static SearchTweetsFragment newInstance(String query) {
		Bundle bundle = new Bundle();
		SearchTweetsFragment fragment = new SearchTweetsFragment();
		bundle.putString(ARG_QUERY, query);
		fragment.setArguments(bundle);
		return fragment;
	}
	

	@Override
	public String getUrl() {
		return URL + getArguments().getString(ARG_QUERY);
	}
	
	@Override
	public String getDelegateKey() {
		return URL + getArguments().getString(ARG_QUERY);
	}

	@Override
	protected void success(List<Tweet> result) {
		mListView = (ListView) getView().findViewById(R.id.array_model_list);
		mListView.setAdapter(new TweetAdapter(getActivity(),
				R.layout.tweet, result));

	}

	@Override
	public int getLayoutResource() {
		return R.layout.load_array_model;
	}

}
