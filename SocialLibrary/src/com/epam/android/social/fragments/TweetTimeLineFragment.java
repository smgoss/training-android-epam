package com.epam.android.social.fragments;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

import com.epam.android.common.model.BaseModel;
import com.epam.android.social.R;
import com.epam.android.social.adapter.TweetAdapter;
import com.epam.android.social.common.fragments.CommonTwitterFragment;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.model.Tweet;

public class TweetTimeLineFragment extends CommonTwitterFragment<Tweet> {

	private static final String TAG = TweetTimeLineFragment.class
			.getSimpleName();

	private View actionView;

	private WindowManager mWindowManager;

	private WindowManager.LayoutParams mWindowParams;

	public static TweetTimeLineFragment newInstance(String query,
			String accountName) {
		Bundle bundle = new Bundle();
		TweetTimeLineFragment fragment = new TweetTimeLineFragment();
		bundle.putString(ApplicationConstants.ARG_QUERY, query);
		bundle.putString(ApplicationConstants.ARG_PROFILE_NAME, accountName);
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
				// addActionTweetView(view);
			}

		});
	}

	// private void addActionTweetView(View view) {
	// if (actionView == null) {
	// actionView = LayoutInflater.from(getActivity()).inflate(
	// R.layout.action_on_tweet, null);
	// }
	//
	// if (mWindowManager == null) {
	// mWindowManager = (WindowManager) getContext().getSystemService(
	// Context.WINDOW_SERVICE);
	// }
	//
	// mWindowParams = new WindowManager.LayoutParams();
	//
	// mWindowParams.alpha = 0.9f;
	// // mWindowParams.gravity = Gravity.TOP | Gravity.LEFT;
	// mWindowParams.x = view.getScrollX();
	//
	// mWindowParams.y = view.getScrollY();
	//
	// mWindowParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
	// | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
	// mWindowParams.height = view.getHeight();
	//
	// mWindowParams.width = LayoutParams.FILL_PARENT;
	//
	// mWindowManager.addView(actionView, mWindowParams);
	// }
	//
	// private void removeActionTweetView() {
	//
	// }

	@SuppressWarnings("unchecked")
	@Override
	public BaseAdapter createAdapter(List<? extends BaseModel> list) {
		return new TweetAdapter(getContext(), R.layout.tweet,
				(List<Tweet>) list);
	}

}
