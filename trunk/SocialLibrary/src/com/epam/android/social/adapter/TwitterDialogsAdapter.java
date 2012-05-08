package com.epam.android.social.adapter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epam.android.common.adapter.AbstractAdapter;
import com.epam.android.social.R;
import com.epam.android.social.helper.DataConvertHelper;
import com.epam.android.social.model.TwitterDialogs;

public class TwitterDialogsAdapter extends AbstractAdapter<TwitterDialogs>{

	public TwitterDialogsAdapter(Context c, int pItemResource,
			List<TwitterDialogs> pList) {
		super(c, pItemResource, pList);
	}

	@Override
	public void init(View view, TwitterDialogs item) {
		
		ImageView userAvatar = (ImageView) view.findViewById(R.id.userAvatar);
		mImageLoader.bind(this, userAvatar, item.getSenderProfileUrl());

		TextView userName = (TextView) view.findViewById(R.id.userName);
		userName.setText(item.getSenderScreenName());

		Date date = new Date(item.getPublicdDate());
		Date dateNow = Calendar.getInstance().getTime();

		TextView datePublic = (TextView) view.findViewById(R.id.tweetDate);
		datePublic.setText(DataConvertHelper.getFormattedDate(dateNow, date));

		TextView tweetText = (TextView) view.findViewById(R.id.tweetText);
		tweetText.setText(item.getText());
	}

}
