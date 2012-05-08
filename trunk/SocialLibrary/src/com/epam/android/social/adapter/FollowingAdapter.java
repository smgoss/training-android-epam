package com.epam.android.social.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epam.android.common.adapter.AbstractAdapter;
import com.epam.android.social.R;
import com.epam.android.social.api.TwitterAPI;
import com.epam.android.social.model.Following;

public class FollowingAdapter extends AbstractAdapter<Following> {

	private Context context;

	public FollowingAdapter(Context c, int pItemResource, List<Following> pList) {
		super(c, pItemResource, pList);
		context = c;
	}

	@Override
	public void init(View convertView, final Following item) {
		TextView name = (TextView) convertView.findViewById(R.id.follow_name);
		name.setText(item.getName());

		TextView screenName = (TextView) convertView
				.findViewById(R.id.follow_screenName);
		screenName.setText("@" + item.getScreenName());

		ImageView userAvatar = (ImageView) convertView
				.findViewById(R.id.follow_avatar);
		mImageLoader.setAvatar(
				TwitterAPI.getInstance().getUserAvatar(item.getScreenName()),
				userAvatar);

		ImageView followButton = (ImageView) convertView
				.findViewById(R.id.follow_followMeButton);
		if (item.isFollow()) {
			followButton.setImageDrawable(context.getResources().getDrawable(
					R.drawable.twitter_follow));
		} else {
			followButton.setImageDrawable(context.getResources().getDrawable(
					R.drawable.twitter_follow_me));
		}
		followButton.setTag(item.getIdUser());
	}

}
