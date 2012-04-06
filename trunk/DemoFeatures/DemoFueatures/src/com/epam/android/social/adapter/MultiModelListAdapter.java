package com.epam.android.social.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epam.android.common.adapter.AbstractAdapter;
import com.epam.android.social.R;
import com.epam.android.social.model.Other;

public class MultiModelListAdapter extends AbstractAdapter<Other> {

	@SuppressWarnings("unused")
	private static final String TAG = MultiModelListAdapter.class
			.getSimpleName();

	public MultiModelListAdapter(Context context, int pItemResource,
			List<Other> pList) {
		super(context, pItemResource, pList);
	}

	public void init(View view, Other other) {
		TextView userName = (TextView) view.findViewById(R.id.userName);
		userName.setText(other.getName());
		
		TextView userNumber = (TextView) view.findViewById(R.id.userNumber);
		userNumber.setText(other.getNumber().toString());

		ImageView userAvatar = (ImageView) view.findViewById(R.id.userAvatar);
		mImageLoader.bind(this, userAvatar, other.getImageUrl());
	}

}
