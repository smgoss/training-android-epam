package com.epam.android.social.facebook;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.epam.android.common.adapter.AbstractAdapter;
import com.epam.android.social.R;
import com.epam.android.social.model.FacebookUserinfo;

public class UserInfoAdapter extends AbstractAdapter<FacebookUserinfo> {

	private static final String TAG = UserInfoAdapter.class
			.getSimpleName();
	public UserInfoAdapter(Context c, int pItemResource,
			List<FacebookUserinfo> pList) {
		super(c, pItemResource, pList);
	}

	@Override
	public void init(View view, FacebookUserinfo item) {
		TextView userName = (TextView) view.findViewById(R.id.tvUserName);
		userName.setText(item.getName());
	}

}
