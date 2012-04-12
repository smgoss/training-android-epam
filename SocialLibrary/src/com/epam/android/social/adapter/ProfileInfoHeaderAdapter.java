package com.epam.android.social.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epam.android.social.R;
import com.epam.android.social.model.ProfileInfo;
import com.google.android.imageloader.ImageLoader;

public class ProfileInfoHeaderAdapter {

	private ImageLoader mImageLoader;

	public ProfileInfoHeaderAdapter(Context c, View convertView,
			ProfileInfo item) {
		mImageLoader = (ImageLoader) c.getApplicationContext()
				.getSystemService(ImageLoader.IMAGE_LOADER_SERVICE);
		init(convertView, item);
	}

	public void init(View convertView, ProfileInfo item) {
		ImageView profileAvatar = (ImageView) convertView
				.findViewById(R.id.profileInfo_profileAvatar);
		mImageLoader.bind(profileAvatar, item.getProfileAvatarUrl(), null);

		TextView name = (TextView) convertView
				.findViewById(R.id.profileInfo_profileName);
		name.setText(item.getName());

		TextView screenName = (TextView) convertView
				.findViewById(R.id.profileInfo_profileScreenName);
		screenName.setText(item.getScreenName());

		TextView description = (TextView) convertView
				.findViewById(R.id.profileInfo_profileDescription);
		if (item.getDescription() != null && item.getDescription().length() != 0 ) {
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

	}

}
