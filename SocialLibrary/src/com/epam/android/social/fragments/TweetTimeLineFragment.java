package com.epam.android.social.fragments;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.epam.android.common.model.BaseModel;
import com.epam.android.social.R;
import com.epam.android.social.adapter.TweetAdapter;
import com.epam.android.social.common.fragments.CommonTwitterFragment;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.model.Tweet;

public class TweetTimeLineFragment extends CommonTwitterFragment<Tweet> {

	private static final String TAG = TweetTimeLineFragment.class
			.getSimpleName();

	public static TweetTimeLineFragment newInstance(String query,
			String accountName, boolean getMessagesFromReceiver) {
		Bundle bundle = new Bundle();
		TweetTimeLineFragment fragment = new TweetTimeLineFragment();
		bundle.putString(ApplicationConstants.ARG_QUERY, query);
		bundle.putString(ApplicationConstants.ARG_PROFILE_NAME, accountName);
		bundle.putBoolean(ApplicationConstants.GET_MESSAGES_FROM_RECEIVER,
				getMessagesFromReceiver);
		fragment.setArguments(bundle);
		return fragment;
	}

	private TweetTimeLineFragment() {

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Bundle bundle = new Bundle();
				TweetDialogFragment tweetDialogFragment = new TweetDialogFragment();
				bundle.putString(ApplicationConstants.TWEET_TEXT,
						((TextView) view.findViewById(R.id.tweetText))
								.getText().toString());
				tweetDialogFragment.setArguments(bundle);
				tweetDialogFragment.show(getFragmentManager(), TAG);
			}

		});

	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseAdapter createAdapter(List<? extends BaseModel> list) {
		return new TweetAdapter(getContext(), R.layout.tweet,
				(List<Tweet>) list);
	}

	@Override
	public int getProgressBarResource() {
		return R.id.progress_bar_on_listView;
	}

}
