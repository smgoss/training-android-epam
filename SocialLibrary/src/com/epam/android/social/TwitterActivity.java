package com.epam.android.social;

import java.io.IOException;
import java.util.List;

import android.util.Log;
import android.widget.ListView;

import com.epam.android.common.BaseArrayModelByAnnotationActivity;
import com.epam.android.common.http.HttpClient;
import com.epam.android.social.adapter.TweetAdapter;
import com.epam.android.social.model.Tweet;

public class TwitterActivity extends BaseArrayModelByAnnotationActivity<Tweet>{

	private static final String URL = "http://search.twitter.com/search.json?q=samsung";
	
	private ListView mListView;
	
	@Override
	public String getUrl() {
		return URL;
	}

	@Override
	protected void success(List<Tweet> result) {
		mListView = (ListView) findViewById(R.id.array_model_list);
		mListView.setAdapter(new TweetAdapter(TwitterActivity.this, R.layout.tweet, result));
		
		HttpClient httpClient = (HttpClient) getApplicationContext().getSystemService(HttpClient.HTTP_CLIENT);
		try {
			Log.d("TAG",httpClient.loadAsString("https://api.twitter.com/1/statuses/home_timeline.json?include_entities=true"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public int getLayoutResource() {
		return R.layout.load_array_model;
	}

}
