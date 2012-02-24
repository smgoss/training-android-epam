package com.epam.android.social;

import java.util.List;

import android.widget.ListView;

import com.epam.android.common.BaseArrayModelActivity;
import com.epam.android.social.adapter.ArrayModelListAdapter;
import com.epam.android.social.adapter.TweetAdapter;
import com.epam.android.social.model.Tweet;

public class TweeterActivity extends BaseArrayModelActivity<Tweet>{

	private static final String URL = "http://search.twitter.com/search.json?q=samsung";
	
	private ListView mListView;
	
	@Override
	public String getUrl() {
		return URL;
	}

	@Override
	protected void success(List<Tweet> result) {
		mListView = (ListView) findViewById(R.id.array_model_list);
		mListView.setAdapter(new TweetAdapter(TweeterActivity.this, R.layout.tweet, result));
		
	}

	@Override
	public int getLayoutResource() {
		return R.layout.load_array_model;
	}

}
