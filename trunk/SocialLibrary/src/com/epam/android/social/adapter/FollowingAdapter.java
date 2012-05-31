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
		mImageLoader.bind(userAvatar, TwitterAPI.getInstance()
				.getUserAvatarNormal(item.getScreenName()), null);

		userAvatar.setBackgroundResource(R.drawable.bg_shadow_photo);
		userAvatar.setPadding(0, 0, 0, convertDipToPix(3));

		ImageView followButton = (ImageView) convertView
				.findViewById(R.id.follow_followMeButton);
		if (item.isFollow()) {
			followButton.setBackgroundDrawable(context.getResources()
					.getDrawable(R.drawable.btn_bird_big_blue));
		} else {
			followButton.setBackgroundDrawable(context.getResources()
					.getDrawable(R.drawable.btn_bird_big_grey));
		}
		followButton.setTag(item.getIdUser());
	}

	private int convertDipToPix(int dip) {
		final float scale = getContext().getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}
}
