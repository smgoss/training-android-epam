package com.epam.android.social.fragments;

import java.util.List;

import android.os.Bundle;
import android.widget.BaseAdapter;

import com.epam.android.common.model.BaseModel;
import com.epam.android.social.R;
import com.epam.android.social.adapter.SearchAdapter;
import com.epam.android.social.common.fragments.CommonTwitterByAnnotationFragment;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.model.SearchResult;

public class SearchTweetsFragment extends
		CommonTwitterByAnnotationFragment<SearchResult> {

	public static final String TAG = SearchTweetsFragment.class.getSimpleName();

	public static SearchTweetsFragment newInstance(String query) {
		Bundle bundle = new Bundle();
		SearchTweetsFragment fragment = new SearchTweetsFragment();
		bundle.putString(ApplicationConstants.ARG_QUERY, query);
		fragment.setArguments(bundle);
		return fragment;
	}

	private SearchTweetsFragment() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseAdapter createAdapter(List<? extends BaseModel> list) {
		return new SearchAdapter(getContext(), R.layout.tweet,
				(List<SearchResult>) list);
	}

	@Override
	public int getProgressBarResource() {
		return R.id.progress_bar_on_listView;
	}

}
