package com.epam.android.social.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.epam.android.common.BaseArrayModelByAnnotationFragment;
import com.epam.android.common.BaseArrayModelFragment;
import com.epam.android.social.R;
import com.epam.android.social.adapter.ReTweetAdapter;
import com.epam.android.social.adapter.TweetAdapter;
import com.epam.android.social.model.Tweet;
import com.epam.android.social.model.TweetNotLogin;

public class SearchTweetsFragment extends BaseArrayModelFragment<Tweet> implements OnClickListener{

	private static final String ARG_QUERY = "query";

	private static final String TAG = SearchTweetsFragment.class.getSimpleName();

	private static final String URL = "http://search.twitter.com/search.json?q=";
	
	private ProgressBar mProgressBar;

	private ListView mListView;

	private Button loadMore;
	
	private String delegateKey;
	
	private List<Tweet> currentList;
	
	private ReTweetAdapter adapter;

	private boolean setBottomButton = false;
	
	public static SearchTweetsFragment newInstance(String query) {
		Bundle bundle = new Bundle();
		SearchTweetsFragment fragment = new SearchTweetsFragment();
		bundle.putString(ARG_QUERY, query);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadMore = new Button(getContext());
		loadMore.setText("load more");
		loadMore.setOnClickListener(SearchTweetsFragment.this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (!setBottomButton) {
			mListView = (ListView) getView()
					.findViewById(R.id.array_model_list);
			mListView.addFooterView(loadMore);
			setBottomButton = true;
		}
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public String getUrl() {
		return getArguments().getString(ARG_QUERY);
	}
	
	@Override
	public String getDelegateKey() {
		if(delegateKey == null){
			delegateKey = getArguments().getString(ARG_QUERY);
		}
 		return delegateKey;
	}

	@Override
	protected void success(List<Tweet> result) {
		if (currentList == null) {
			currentList = new ArrayList<Tweet>(); 
			currentList.addAll(result);
			adapter = new ReTweetAdapter(getContext(),
					R.layout.tweet, currentList);
			mListView.setAdapter(adapter);
		}
		else{
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
	public void startTasks() {
		super.startTasks();
	}
	
	@Override
	public void showLoading() {
		if (mProgressBar == null) {
			mProgressBar = (ProgressBar) getView().findViewById(
					R.id.progress_bar_on_listView);
			mProgressBar.setVisibility(View.VISIBLE);
		} else if (mProgressBar.getVisibility() != View.VISIBLE) {
			mProgressBar.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void showProgress(String textMessage) {
		if (mProgressBar == null) { 
			mProgressBar = (ProgressBar) getView().findViewById(
					R.id.progress_bar_on_listView);
			mProgressBar.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void hideLoading() {
		if (mProgressBar != null && mProgressBar.getVisibility() == View.VISIBLE) {
			mProgressBar.setVisibility(View.INVISIBLE);
		}
	}

}
