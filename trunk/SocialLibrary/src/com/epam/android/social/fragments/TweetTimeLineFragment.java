package com.epam.android.social.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.epam.android.social.R;
import com.epam.android.social.adapter.TweetAdapter;
import com.epam.android.social.model.Tweet;

public class TweetTimeLineFragment extends
		BaseArrayModelFragmentWithCustomLoad<Tweet> {


	private static final String ARG_QUERY = "query";

	private static final String ARG_PROFILE_NAME = "profile_name";

	private static final String TAG = TweetTimeLineFragment.class
			.getSimpleName();

	private Button loadMore;

	private String delegateKey;

	private List<Tweet> currentList;

	private TweetAdapter adapter;

	private int loadedPage = 1;
	
	private ListView mListView;

	public static TweetTimeLineFragment newInstance(String query,
			String accountName) {
		Bundle bundle = new Bundle();
		TweetTimeLineFragment fragment = new TweetTimeLineFragment();
		bundle.putString(ARG_QUERY, query);
		bundle.putString(ARG_PROFILE_NAME, accountName);
		fragment.setArguments(bundle);
		return fragment;
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
	public String getUrl() {
		return getArguments().getString(ARG_QUERY) + loadedPage;
	}

	@Override
	public String getDelegateKey() {
		if (delegateKey == null) {
			delegateKey = getArguments().getString(ARG_QUERY)
					+ getArguments().getString(ARG_PROFILE_NAME);
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
		} else {
			currentList.addAll(result);
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public int getLayoutResource() {
		return R.layout.load_array_model;
	}

	public void setList(List<Tweet> list) {
		adapter = new TweetAdapter(getContext(), R.layout.tweet, list);
		mListView.addFooterView(loadMore);
		mListView.setAdapter(adapter);

	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (currentList != null && currentList.size() != 0) {
			outState.putParcelableArrayList(getDelegateKey(),
					(ArrayList<? extends Parcelable>) currentList);
		}

	}
	
	private void restoreFragment(Bundle savedInstanceState) {

		if (savedInstanceState != null) {
			currentList.clear();
			currentList = savedInstanceState
					.getParcelableArrayList(getDelegateKey());
			if (currentList != null && currentList.size() != 0) {
				setList(currentList);
			}
		}

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "TUT!!!!!");
		mListView = (ListView) getView().findViewById(R.id.array_model_list);
		if (savedInstanceState != null) {
			restoreFragment(savedInstanceState);
		}

	}

}
