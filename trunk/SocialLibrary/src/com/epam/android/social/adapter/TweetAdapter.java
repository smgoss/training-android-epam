package com.epam.android.social.adapter;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epam.android.common.adapter.AbstractAdapter;
import com.epam.android.social.R;
import com.epam.android.social.model.Tweet;

public class TweetAdapter extends AbstractAdapter<Tweet> {

	public TweetAdapter(Context c, int pItemResource, List<Tweet> pList) {
		super(c, pItemResource, pList);
	}

	@Override
	public void init(View view, Tweet item) {
		ImageView userAvatar = (ImageView) view.findViewById(R.id.userAvatar);
		mImageLoader.bind(this, userAvatar, item.getProfileUrl());

		TextView userName = (TextView) view.findViewById(R.id.userName);
		userName.setText(item.getUserName());

		TextView datePublic = (TextView) view.findViewById(R.id.tweetDate);
		datePublic.setText(new Date(item.getPublicdDate()).toLocaleString());

		
		TextView tweetText = (TextView) view.findViewById(R.id.tweetText);
		tweetText.setText(item.getText());

	}

}
