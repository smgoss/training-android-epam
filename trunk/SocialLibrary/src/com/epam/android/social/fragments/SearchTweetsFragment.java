package com.epam.android.social.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.epam.android.common.BaseArrayModelByAnnotationFragment;
import com.epam.android.common.BaseArrayModelFragment;
import com.epam.android.social.R;
import com.epam.android.social.adapter.TweetAdapter;
import com.epam.android.social.adapter.TweetNotLoginAdapter;
import com.epam.android.social.model.Tweet;
import com.epam.android.social.model.TweetNotLogin;

public class SearchTweetsFragment extends BaseArrayModelFragment<Tweet>
		implements OnClickListener {

	private static final String ARG_QUERY = "query";
	
	private static final String ARG_USER_NAME = "user_name";

	private static final String TAG = SearchTweetsFragment.class
			.getSimpleName();

	private static final String URL = "http://search.twitter.com/search.json?q=";

	private ProgressBar mProgressBar;

	private ListView mListView;

	private Button loadMore;

	private String delegateKey;

	private List<Tweet> currentList;

	private TweetAdapter adapter;

	private boolean isLoading;

	public static SearchTweetsFragment newInstance(String query,String accountName) {
		Bundle bundle = new Bundle();
		SearchTweetsFragment fragment = new SearchTweetsFragment();
		bundle.putString(ARG_QUERY, query);
		bundle.putString(ARG_USER_NAME, accountName);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadMore = new Button(getContext());
		loadMore.setText("load more");
		loadMore.setOnClickListener(SearchTweetsFragment.this);
		Log.d(TAG, "onCreate");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mListView = (ListView) getView().findViewById(R.id.array_model_list);
		if (savedInstanceState != null) {
			restoreFragment(savedInstanceState);
		}

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(TAG, "onSaveInstanceState " + getDelegateKey());
		if (currentList != null && currentList.size() != 0) {
			outState.putParcelableArrayList(getDelegateKey(),
					(ArrayList<? extends Parcelable>) currentList);
		}
	}

	private void setList(List<Tweet> list) {
		adapter = new TweetAdapter(getContext(), R.layout.tweet, list);
		mListView.addFooterView(loadMore);
		mListView.setAdapter(adapter);
	}

	private void restoreFragment(Bundle savedInstanceState) {
		Log.d(TAG, "restoreFragmnet");

		if (savedInstanceState != null) {
			currentList = savedInstanceState
					.getParcelableArrayList(getDelegateKey());
			if (currentList != null && currentList.size() != 0) {
				setList(currentList);
			}
		}

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Log.d(TAG, "onViewCreated");
		if (isLoading) {
			showLoading();
		}
	}

	@Override
	public String getUrl() {
		return getArguments().getString(ARG_QUERY);
	}

	@Override
	public String getDelegateKey() {
		if (delegateKey == null) {
			delegateKey = getArguments().getString(ARG_QUERY) + getArguments().getString(ARG_USER_NAME);
			Log.d(TAG, "delegate key=" + delegateKey);
		}
 		return delegateKey;
	}

	@Override
	protected void success(List<Tweet> result) {
		if (currentList == null) {
			currentList = new ArrayList<Tweet>();
			currentList.addAll(result);
			setList(currentList);
			Log.d(TAG, "success = "  + result.size());
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
	public void onClick(View v) {
		getArguments().putString(ARG_QUERY, "HTC");
		startTasks();
	}

	@Override
	public void showLoading() {
		mProgressBar = (ProgressBar) getView().findViewById(
				R.id.progress_bar_on_listView);
		mProgressBar.setVisibility(View.VISIBLE);

		isLoading = true;
	}

	@Override
	public void showProgress(String textMessage) {
		mProgressBar = (ProgressBar) getView().findViewById(
				R.id.progress_bar_on_listView);
		mProgressBar.setVisibility(View.VISIBLE);
		isLoading = true;
	}

	@Override
	public void hideLoading() {
		if (mProgressBar != null
				&& mProgressBar.getVisibility() == View.VISIBLE) {
			mProgressBar.setVisibility(View.INVISIBLE);
		}
		isLoading = false;
	}

}