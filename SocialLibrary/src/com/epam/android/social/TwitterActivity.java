package com.epam.android.social;

import java.io.IOException;
import java.util.List;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

import android.util.Log;
import android.widget.ListView;

import com.epam.android.common.BaseArrayModelByAnnotationActivity;
import com.epam.android.common.http.HttpClient;
import com.epam.android.social.adapter.TweetAdapter;
import com.epam.android.social.helper.OAuthHelper;
import com.epam.android.social.model.Tweet;

public class TwitterActivity extends BaseArrayModelByAnnotationActivity<Tweet> {

	private static final String TAG = TwitterActivity.class.getSimpleName();

	private static final String URL = "http://search.twitter.com/search.json?q=samsung";

	private ListView mListView;

	@Override
	public String getUrl() {
		return URL;
	}

	@Override
	protected void success(List<Tweet> result) {
		mListView = (ListView) findViewById(R.id.array_model_list);
		mListView.setAdapter(new TweetAdapter(TwitterActivity.this,
				R.layout.tweet, result));

		HttpClient httpClient = (HttpClient) getApplicationContext()
				.getSystemService(HttpClient.HTTP_CLIENT);
		try {
			String request = OAuthHelper.sign("https://api.twitter.com/1/statuses/home_timeline.json?include_entities=true"); 
			Log.d(TAG, request);
			try {
				Log.d(TAG, httpClient.loadAsString(request));
			} catch (IOException e) {
				Log.e(TAG, "IO error ", e);
			}
		} catch (OAuthMessageSignerException e) {
			Log.e(TAG, "OAuth Message Signer error ",e);
		} catch (OAuthNotAuthorizedException e) {
			Log.e(TAG, "OAuth Not Authorized error", e);
		} catch (OAuthExpectationFailedException e) {
			Log.e(TAG, "OAuth Expectation error ", e);
		} catch (OAuthCommunicationException e) {
			Log.e(TAG , "OAuth Communication error ",e );
		}

	}

	@Override
	public int getLayoutResource() {
		return R.layout.load_array_model;
	}

}
