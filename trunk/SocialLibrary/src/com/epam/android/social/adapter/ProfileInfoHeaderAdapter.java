package com.epam.android.social.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.epam.android.social.R;
import com.epam.android.social.model.ProfileInfo;
import com.google.android.imageloader.ImageLoader;

public class ProfileInfoHeaderAdapter {

	private ImageLoader mImageLoader;

	private String accountName;
	
	private Context context;

	public ProfileInfoHeaderAdapter(Context c, View convertView,
			ProfileInfo item, String accountName) {
		mImageLoader = (ImageLoader) c.getApplicationContext()
				.getSystemService(ImageLoader.IMAGE_LOADER_SERVICE);
		this.accountName = accountName;
		context = c;
		init(convertView, item);

	}

	private void init(View convertView, ProfileInfo item) {
		ImageView profileAvatar = (ImageView) convertView
				.findViewById(R.id.profileInfo_profileAvatar);
		mImageLoader.bind(profileAvatar, item.getProfileAvatarUrl(), null);

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
		if (accountName.equals(item.getScreenName())) {
			sendTweetButton.setText(context.getResources().getString(R.string.change_profile));
		}

		TextView profileTweetsTextView = (TextView) convertView
				.findViewById(R.id.profile_tweetsTextView);
		profileTweetsTextView.setVisibility(View.VISIBLE);

	}

}
