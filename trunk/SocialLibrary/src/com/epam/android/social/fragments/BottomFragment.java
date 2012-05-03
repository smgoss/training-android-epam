package com.epam.android.social.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.epam.android.social.R;
import com.epam.android.social.api.TwitterAPI;
import com.epam.android.social.constants.ApplicationConstants;

public class BottomFragment extends Fragment {

	private static final String TAG = BottomFragment.class.getSimpleName();

	public static BottomFragment newInstance(String accountName) {
		Bundle bundle = new Bundle();
		BottomFragment fragment = new BottomFragment();
		bundle.putString(ApplicationConstants.ARG_PROFILE_NAME, accountName);
		fragment.setArguments(bundle);
		return fragment;
	}

	private BottomFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.bottom, null, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getView().findViewById(R.id.tweetButton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						AddTweetFragment addTweetFragment = new AddTweetFragment();
						addTweetFragment.show(getFragmentManager(), TAG);
					}
				});
		getView().findViewById(R.id.searchButton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (getFragmentManager().findFragmentByTag(
								SearchLineFragment.TAG) == null) {
							FragmentTransaction fragmentTransaction = getFragmentManager()
									.beginTransaction();
							fragmentTransaction.addToBackStack(getTag());
							fragmentTransaction.setCustomAnimations(
									android.R.anim.fade_in,
									android.R.anim.fade_out);
							fragmentTransaction.add(
									R.id.twitter_timeline_fragment,
									new SearchLineFragment(),
									SearchLineFragment.TAG);
							fragmentTransaction.commit();
						}

					}
				});

		getView().findViewById(R.id.homeButton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(getView().getContext(),
								"onHomeButtonClick", Toast.LENGTH_SHORT).show();
					}
				});

		getView().findViewById(R.id.favoriteButton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						FragmentTransaction fragmentTransaction = getFragmentManager()
								.beginTransaction();
						fragmentTransaction.addToBackStack(getTag());
						fragmentTransaction
								.setCustomAnimations(android.R.anim.fade_in,
										android.R.anim.fade_out);
						fragmentTransaction
								.add(R.id.twitter_timeline_fragment,
										TweetTimeLineFragment
												.newInstance(
														TwitterAPI
																.getInstance()
																.getFavorite(),
														getArguments()
																.getString(
																		ApplicationConstants.ARG_PROFILE_NAME)),
										SearchTweetsFragment.TAG);
						fragmentTransaction.commit();
					}
				});

		getView().findViewById(R.id.messagesButton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(getView().getContext(),
								"onMessageButtonClick", Toast.LENGTH_SHORT)
								.show();
					}
				});
	}

}
