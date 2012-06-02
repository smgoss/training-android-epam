package com.epam.android.social.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.bottom, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "BottomFragment Create");

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
						getActivity().startSearch(null, false, null, false);

					}
				});

		getView().findViewById(R.id.homeButton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						SharedPreferences settings;
						settings = getActivity().getSharedPreferences(ApplicationConstants.PREFS_SETTINGS_NAME, 0);
						SharedPreferences.Editor editor = settings.edit();
						editor.putInt(ApplicationConstants.KEY_CURRENT_ACCOUNT, -1);
						editor.commit();
						
						FragmentTransaction transaction = getFragmentManager()
								.beginTransaction();
						transaction.add(R.id.main_mainLayout,
								new MainLoginFragment());
						transaction.commit();

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
														getActivity()
																.getIntent()
																.getStringExtra(
																		ApplicationConstants.ARG_PROFILE_NAME)),
										SearchTweetsFragment.TAG);
						fragmentTransaction.commit();
					}
				});

		getView().findViewById(R.id.messagesButton).setOnClickListener(
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
										TwitterDialogsFragment
												.newInstance(
														TwitterAPI
																.getInstance()
																.getDirectMessages(),
														getActivity()
																.getIntent()
																.getStringExtra(
																		ApplicationConstants.ARG_PROFILE_NAME)),
										SearchTweetsFragment.TAG);
						fragmentTransaction.commit();
					}
				});
	}
}
