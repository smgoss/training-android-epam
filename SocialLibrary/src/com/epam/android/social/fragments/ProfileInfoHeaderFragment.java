package com.epam.android.social.fragments;

import java.util.List;

import android.R.anim;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.epam.android.social.R;
import com.epam.android.social.adapter.ProfileInfoHeaderAdapter;
import com.epam.android.social.api.TwitterAPI;
import com.epam.android.social.model.ProfileInfo;

public class ProfileInfoHeaderFragment extends
		BaseArrayModelFragmentWithCustomLoad<ProfileInfo> {

	private static final String TAG = ProfileInfoHeaderFragment.class
			.getSimpleName();

	private static final String ARG_QUERY = "query";

	private static final String ARG_ACCOUNT_NAME = "accountName";

	private LinearLayout tweetsButton;

	private LinearLayout followingButton;

	private LinearLayout followersButton;

	private Button changeProfileButton;
	
	private static final int SELECT_PHOTO = 100;

	public static ProfileInfoHeaderFragment newInstance(String query,
			String accountName) {
		Bundle bundle = new Bundle();
		ProfileInfoHeaderFragment fragment = new ProfileInfoHeaderFragment();
		bundle.putString(ARG_QUERY, query);
		bundle.putString(ARG_ACCOUNT_NAME, accountName);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public String getUrl() {
		return getArguments().getString(ARG_QUERY);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		tweetsButton = (LinearLayout) getView().findViewById(
				R.id.profileInfo_tweetsLayout);
		tweetsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getView().getContext(), "onTweetButtonClick",
						Toast.LENGTH_SHORT).show();
			}
		});

		followersButton = (LinearLayout) getView().findViewById(
				R.id.profileInfo_followersLayout);
		followersButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentTransaction transaction = getFragmentManager()
						.beginTransaction();
				transaction.setCustomAnimations(android.R.anim.slide_in_left,
						android.R.anim.slide_out_right);
				transaction.addToBackStack(getTag());
				transaction.add(R.id.twitter_timeline_fragment,
						FollowingFragment.newInstance(TwitterAPI.getInstance()
								.getFollowers(
										getArguments().getString(
												ARG_ACCOUNT_NAME))));
				transaction.commit();
			}
		});

		followingButton = (LinearLayout) getView().findViewById(
				R.id.profileInfo_followingLayout);
		followingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentTransaction transaction = getFragmentManager()
						.beginTransaction();
				transaction.setCustomAnimations(android.R.anim.slide_in_left,
						android.R.anim.slide_out_right);
				transaction.addToBackStack(getTag());
				transaction.add(R.id.twitter_timeline_fragment,
						FollowingFragment.newInstance(TwitterAPI.getInstance()
								.getFollowing(
										getArguments().getString(
												ARG_ACCOUNT_NAME))),FollowingFragment.TAG);
				transaction.commit();
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
				transaction.add(R.id.twitter_timeline_fragment,
						ChangeProfileFragment.newInstance(TwitterAPI
								.getInstance().getFullProfileInfo(
										getArguments().getString(
												ARG_ACCOUNT_NAME))));
				transaction.commit();
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
				getArguments().getString(ARG_ACCOUNT_NAME));
	}


}
