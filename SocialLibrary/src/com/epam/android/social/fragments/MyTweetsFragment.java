package com.epam.android.social.fragments;

import java.util.List;

import com.epam.android.common.BaseArrayModelByAnnotationFragment;
import com.epam.android.layouts.adapter.CommonFragmentPagerAdapter;
import com.epam.android.social.R;
import com.epam.android.social.TwitterActivity;
import com.epam.android.social.adapter.TweetAdapter;
import com.epam.android.social.model.Tweet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MyTweetsFragment extends BaseArrayModelByAnnotationFragment<Tweet>{

	private static final String TAG = MyTweetsFragment.class.getSimpleName();

	private static final String URL = "http://search.twitter.com/search.json?q=samsung";


	private ListView mListView;

	@Override
	public String getUrl() {
		return URL;
	}

	@Override
	protected void success(List<Tweet> result) {
		mListView = (ListView) getActivity().findViewById(R.id.array_model_list);
		mListView.setAdapter(new TweetAdapter(getActivity(),
				R.layout.tweet, result));

	}

	@Override
	public int getLayoutResource() {
		return R.layout.load_array_model;
	}


	
}
