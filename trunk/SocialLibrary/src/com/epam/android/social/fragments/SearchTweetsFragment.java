package com.epam.android.social.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.epam.android.common.BaseArrayModelByAnnotationFragment;
import com.epam.android.common.task.LoadArrayModelByAnnotationAsyncTask;
import com.epam.android.social.R;
import com.epam.android.social.adapter.TweetAdapter;
import com.epam.android.social.model.Tweet;

public class SearchTweetsFragment extends BaseArrayModelByAnnotationFragment<Tweet> implements OnClickListener{

	private static final String ARG_QUERY = "query";

	private static final String TAG = SearchTweetsFragment.class.getSimpleName();

	private static final String URL = "http://search.twitter.com/search.json?q=";

	private ListView mListView;

	private Button loadMore;
	
	private String delegateKey;
	
	private List<Tweet> currentList;
	
	private TweetAdapter adapter;

	
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
		mListView = (ListView) getView().findViewById(R.id.array_model_list);
		mListView.addFooterView(loadMore);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public String getUrl() {
		return URL + getArguments().getString(ARG_QUERY);
	}
	
	@Override
	public String getDelegateKey() {
		if(delegateKey == null){
			delegateKey = URL + getArguments().getString(ARG_QUERY);
		}
 		return delegateKey;
	}

	@Override
	protected void success(List<Tweet> result) {
		if (currentList == null) {
			currentList = new ArrayList<Tweet>(); 
			currentList.addAll(result);
			adapter = new TweetAdapter(getContext(),
					R.layout.tweet, currentList);
			mListView.setAdapter(adapter);
		}
		else{
//			adapter = new TweetAdapter(getContext(),
//					R.layout.tweet, currentList);
//			mListView.setAdapter(adapter);
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

}
