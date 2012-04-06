package com.epam.android.layouts.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epam.android.common.adapter.AbstractAdapter;
import com.epam.android.social.R;
import com.epam.android.social.model.Other;

public class List2Adapter extends AbstractAdapter<Other> {

	@SuppressWarnings("unused")
	private static final String TAG = List2Adapter.class
			.getSimpleName();

	public List2Adapter(Context context, int pItemResource,
			List<Other> pList) {
		super(context, pItemResource, pList);
	}

	public void init(View view, Other other) {
		TextView userName = (TextView) view.findViewById(R.id.name);
		userName.setText(other.getName());
		
		TextView userNumber = (TextView) view.findViewById(R.id.number);
		userNumber.setText(other.getNumber().toString());
		
		ImageView userAvatar = (ImageView) view.findViewById(R.id.img);
		userAvatar.setImageDrawable(getContext().getResources().getDrawable(R.drawable.img));
	}

}
