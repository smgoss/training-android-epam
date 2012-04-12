package com.epam.android.demo.layouts.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epam.android.demo.common.adapter.AbstractAdapter;
import com.epam.android.demo.social.R;
import com.epam.android.demo.social.model.Other;

public class List1Adapter extends AbstractAdapter<Other> {

	@SuppressWarnings("unused")
	private static final String TAG = List1Adapter.class
			.getSimpleName();

	public List1Adapter(Context context, int pItemResource,
			List<Other> pList) {
		super(context, pItemResource, pList);
	}

	public void init(View view, Other other) {
		TextView userName = (TextView) view.findViewById(R.id.name);
		userName.setText(other.getName());
		
		ImageView userAvatar = (ImageView) view.findViewById(R.id.img);
		userAvatar.setImageDrawable(getContext().getResources().getDrawable(R.drawable.img));
	}

}
