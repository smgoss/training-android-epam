package com.epam.android.social.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.epam.android.common.utils.GetSystemService;
import com.epam.android.social.R;
import com.epam.android.social.api.TwitterAPI;
import com.epam.android.social.common.fragments.BaseArrayModelFragmentWithCustomLoad;
import com.epam.android.social.constants.ApplicationConstants;
import com.epam.android.social.model.ProfileInfo;
import com.google.android.imageloader.ImageLoader;

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

	private ProfileInfoHeaderFragment() {

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
		initView(getView(), result.get(0));
	}

	@Override
	public int getLayoutResource() {
		return R.layout.profile_info_header_fragment;
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

	private void initView(View convertView, ProfileInfo item) {
		ImageLoader imageLoader = (ImageLoader) GetSystemService.get(
				getContext(), ImageLoader.IMAGE_LOADER_SERVICE);
		ImageView profileAvatar = (ImageView) convertView
				.findViewById(R.id.profileInfo_profileAvatar);
		imageLoader.bind(profileAvatar, item.getProfileAvatarUrl(), null);

		TextView name = (TextView) convertView
				.findViewById(R.id.profileInfo_profileName);
		name.setText(item.getName());

		TextView screenName = (TextView) convertView
				.findViewById(R.id.profileInfo_profileScreenName);
		screenName.setText("@" + item.getScreenName());

		TextView description = (TextView) convertView
				.findViewById(R.id.profileInfo_profileDescription);
		if (item.getDescription() != null
				&& item.getDescription().length() != 0) {
			description.setText(item.getDescription());
		} else {
			description.setVisibility(View.GONE);
		}

		TextView url = (TextView) convertView
				.findViewById(R.id.profileInfo_profileUrl);
		if (item.getUrl() != null) {
			url.setText(item.getUrl());
		} else {
			url.setVisibility(View.GONE);
		}

		TextView tweetCount = (TextView) convertView
				.findViewById(R.id.profileInfo_tweetCount);
		tweetCount.setText(String.valueOf(item.getCountTweets()));

		TextView followingCount = (TextView) convertView
				.findViewById(R.id.profileInfo_followingCount);
		followingCount.setText(String.valueOf(item.getCountFollowing()));

		TextView followersCount = (TextView) convertView
				.findViewById(R.id.profileInfo_followersCount);
		followersCount.setText(String.valueOf(item.getCountFollowers()));

		TextView tweetsTextView = (TextView) convertView
				.findViewById(R.id.profileInfo_tweetTextView);
		tweetsTextView.setVisibility(View.VISIBLE);

		TextView followersTextView = (TextView) convertView
				.findViewById(R.id.profileInfo_followersTextView);
		followersTextView.setVisibility(View.VISIBLE);

		TextView followingTextView = (TextView) convertView
				.findViewById(R.id.profileInfo_followingTextView);
		followingTextView.setVisibility(View.VISIBLE);

		Button sendTweetButton = (Button) convertView
				.findViewById(R.id.profileInfo_sendTweetButton);
		sendTweetButton.setVisibility(View.VISIBLE);
		String accountName = getArguments().getString(
				ApplicationConstants.ARG_PROFILE_NAME);
		if (accountName.equals(item.getScreenName())) {
			sendTweetButton.setText(getContext().getResources().getString(
					R.string.change_profile));
		}

		Button tweetButton = (Button) convertView
				.findViewById(R.id.profileInfo_tweetButton);
		tweetButton.setVisibility(View.VISIBLE);

		Button followingButton = (Button) convertView
				.findViewById(R.id.profileInfo_followingButton);
		followingButton.setVisibility(View.VISIBLE);

		Button followersButton = (Button) convertView
				.findViewById(R.id.profileInfo_followersButton);
		followersButton.setVisibility(View.VISIBLE);
	}

}
