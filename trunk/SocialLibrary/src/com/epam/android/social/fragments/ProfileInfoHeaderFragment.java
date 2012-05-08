package com.epam.android.social.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.epam.android.social.R;
import com.epam.android.social.adapter.ProfileInfoHeaderAdapter;
import com.epam.android.social.api.TwitterAPI;
import com.epam.android.social.common.fragments.BaseArrayModelFragmentWithCustomLoad;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.model.ProfileInfo;

public class ProfileInfoHeaderFragment extends
		BaseArrayModelFragmentWithCustomLoad<ProfileInfo> {

	private static final String TAG = ProfileInfoHeaderFragment.class
			.getSimpleName();

	private LinearLayout tweetsLinerLayout;

	private LinearLayout followingLinerLayout;

	private LinearLayout followersLinerLayout;

	private Button changeProfileButton;

	public static ProfileInfoHeaderFragment newInstance(String query,
			String accountName) {
		Bundle bundle = new Bundle();
		ProfileInfoHeaderFragment fragment = new ProfileInfoHeaderFragment();
		bundle.putString(ApplicationConstants.ARG_QUERY, query);
		bundle.putString(ApplicationConstants.ARG_PROFILE_NAME, accountName);
		fragment.setArguments(bundle);
		return fragment;
	}

	private ProfileInfoHeaderFragment(){
		
	}
	
	@Override
	public String getUrl() {
		return getArguments().getString(ApplicationConstants.ARG_QUERY);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		tweetsLinerLayout = (LinearLayout) getView().findViewById(
				R.id.profileInfo_tweetsLayout);
		tweetsLinerLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onTweetButtonClick();
			}
		});

		followersLinerLayout = (LinearLayout) getView().findViewById(
				R.id.profileInfo_followersLayout);
		followersLinerLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onFollowersButtonClick();
			}
		});

		followingLinerLayout = (LinearLayout) getView().findViewById(
				R.id.profileInfo_followingLayout);
		followingLinerLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onFollowingButtonClick();
			}
		});

		changeProfileButton = (Button) getView().findViewById(
				R.id.profileInfo_sendTweetButton);
		changeProfileButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				FragmentTransaction transaction = getFragmentManager()
						.beginTransaction();
				transaction.setCustomAnimations(android.R.anim.slide_in_left,
						android.R.anim.slide_out_right);
				transaction.addToBackStack(getTag());
				transaction
						.add(R.id.twitter_timeline_fragment,
								ChangeProfileFragment
										.newInstance(TwitterAPI
												.getInstance()
												.getFullProfileInfo(
														getArguments()
																.getString(
																		ApplicationConstants.ARG_PROFILE_NAME))));
				transaction.commit();
			}
		});

		Button tweetButton = (Button) getView().findViewById(
				R.id.profileInfo_tweetButton);
		tweetButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				onTweetButtonClick();
			}
		});

		Button followingButton = (Button) getView().findViewById(
				R.id.profileInfo_followingButton);
		followingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				onFollowingButtonClick();

			}
		});

		Button followersButton = (Button) getView().findViewById(
				R.id.profileInfo_followersButton);
		followersButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				onFollowersButtonClick();
			}
		});
	}

	@Override
	protected void success(List<ProfileInfo> result) {
		initView(result);
	}

	@Override
	public int getLayoutResource() {
		return R.layout.profile_info_header_fragment;
	}

	private void initView(List<ProfileInfo> result) {
		new ProfileInfoHeaderAdapter(getContext(), getView(), result.get(0),
				getArguments().getString(ApplicationConstants.ARG_PROFILE_NAME));
	}

	private void onTweetButtonClick() {
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.setCustomAnimations(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
		transaction.addToBackStack(getTag());
		transaction
				.add(R.id.twitter_timeline_fragment,
						TweetTimeLineFragment
								.newInstance(
										TwitterAPI
												.getInstance()
												.getUserTimeLine(
														getArguments()
																.getString(
																		ApplicationConstants.ARG_PROFILE_NAME)),
										getArguments()
												.getString(
														ApplicationConstants.ARG_PROFILE_NAME)));
		transaction.commit();
	}

	private void onFollowingButtonClick() {
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.setCustomAnimations(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
		transaction.addToBackStack(getTag());
		transaction.add(R.id.twitter_timeline_fragment, FollowingFragment
				.newInstance(TwitterAPI.getInstance().getFollowing(
						getArguments().getString(
								ApplicationConstants.ARG_PROFILE_NAME))),
				FollowingFragment.TAG);
		transaction.commit();
	}

	private void onFollowersButtonClick() {
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.setCustomAnimations(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
		transaction.addToBackStack(getTag());
		transaction.add(R.id.twitter_timeline_fragment, FollowingFragment
				.newInstance(TwitterAPI.getInstance().getFollowers(
						getArguments().getString(
								ApplicationConstants.ARG_PROFILE_NAME))),
				FollowingFragment.TAG);
		transaction.commit();
	}

}
