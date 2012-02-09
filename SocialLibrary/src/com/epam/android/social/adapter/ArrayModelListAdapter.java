package com.epam.android.social.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epam.android.common.adapter.AbstractAdapter;
import com.epam.android.social.R;
import com.epam.android.social.model.User;

public class ArrayModelListAdapter extends AbstractAdapter<User> {

	private static final String TAG = ArrayModelListAdapter.class.getSimpleName();
	
	public ArrayModelListAdapter(Context context, int pItemResource, List<User> pList) {
		super(context, pItemResource, pList);
	}

	public void init(View view, User user) {
		TextView userName = (TextView) view.findViewById(R.id.userName);
		userName.setText(user.getName());
		
		ImageView userAvatar = (ImageView) view.findViewById(R.id.userAvatar);
		mImageLoader.bind(this, userAvatar, user.getImageUrl());
	}

}
