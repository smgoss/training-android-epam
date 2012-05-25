package com.epam.android.social.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
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

public class ProfileFragment extends
		BaseArrayModelFragmentWithCustomLoad<ProfileInfo> {

	private static final String TAG = ProfileFragment.class.getSimpleName();

	private LinearLayout tweetsLinerLayout;

	private LinearLayout followingLinerLayout;

	private LinearLayout followersLinerLayout;

	private LinearLayout userItem;

	public static ProfileFragment newInstance(String query, String profileName) {
		Bundle bundle = new Bundle();
		ProfileFragment fragment = new ProfileFragment();
		bundle.putString(ApplicationConstants.ARG_QUERY, query);
		bundle.putString(ApplicationConstants.ARG_PROFILE_NAME, profileName);
		fragment.setArguments(bundle);
		return fragment;
	}

	private ProfileFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public String getUrl() {
		return getArguments().getString(ApplicationConstants.ARG_QUERY);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		tweetsLinerLayout = (LinearLayout) getView().findViewById(
				R.id.profileInfo_tweetLayout);
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
		TextView tweetTextView = (TextView) tweetsLinerLayout
				.findViewById(R.id.profileButton_name);
		tweetTextView.setText(getResources().getString(R.string.tweets));

		TextView tweetCountTextView = (TextView) tweetsLinerLayout
				.findViewById(R.id.profileButton_value);
		tweetCountTextView.setText(String.valueOf(item.getCountTweets()));

		TextView followingTextView = (TextView) followingLinerLayout
				.findViewById(R.id.profileButton_name);
		followingTextView.setText(getResources().getString(R.string.following));

		TextView followingCountTextView = (TextView) followingLinerLayout
				.findViewById(R.id.profileButton_value);
		followingCountTextView
				.setText(String.valueOf(item.getCountFollowing()));

		TextView followersTextView = (TextView) followersLinerLayout
				.findViewById(R.id.profileButton_name);
		followersTextView.setText(getResources().getString(R.string.followers));

		TextView followersCountTextView = (TextView) followersLinerLayout
				.findViewById(R.id.profileButton_value);
		followersCountTextView
				.setText(String.valueOf(item.getCountFollowers()));

		userItem = (LinearLayout) getView().findViewById(
				R.id.profileInfo_userItem);

		ImageLoader imageLoader = (ImageLoader) GetSystemService.get(
				getContext(), ImageLoader.IMAGE_LOADER_SERVICE);
		ImageView profileAvatar = (ImageView) userItem
				.findViewById(R.id.profileInfoUserItem_avatar);
		imageLoader.bind(profileAvatar, TwitterAPI.getInstance()
				.getUserAvatarBig(item.getScreenName()), null);

		TextView name = (TextView) userItem
				.findViewById(R.id.profileInfoUserItem_name);
		name.setText(item.getName());

		TextView screenName = (TextView) userItem
				.findViewById(R.id.profileInfoUserItem_screenName);
		screenName.setText("@" + item.getScreenName());

		TextView description = (TextView) userItem
				.findViewById(R.id.profileInfoUserItem_description);
		if (item.getDescription() != null
				&& item.getDescription().length() != 0) {
			description.setText(item.getDescription());
		} else {
			description.setVisibility(View.GONE);
		}

		TextView url = (TextView) convertView
				.findViewById(R.id.profileInfoUserItem_url);
		if (item.getUrl() != null) {
			url.setText(item.getUrl());
		} else {
			url.setVisibility(View.GONE);
		}

		ImageView changeProfileButton = (ImageView) userItem
				.findViewById(R.id.profileInfoUser_sendButton);
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

		LinearLayout main = (LinearLayout) getView().findViewById(
				R.id.profileInfo_main);
		main.setVisibility(View.VISIBLE);

	}

	@Override
	public int getProgressBarResource() {
		return R.id.progress_bar_on_profile;
	}

}
