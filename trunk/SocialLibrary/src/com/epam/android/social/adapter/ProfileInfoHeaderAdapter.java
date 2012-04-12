package com.epam.android.social.adapter;

import java.util.List;

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
		description.setText(item.getDescription());

		TextView url = (TextView) convertView
				.findViewById(R.id.profileInfo_profileUrl);
		url.setText(item.getUrl());

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
