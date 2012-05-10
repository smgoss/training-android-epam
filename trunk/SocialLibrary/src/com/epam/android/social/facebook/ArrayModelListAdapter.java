package com.epam.android.social.facebook;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.epam.android.common.adapter.AbstractAdapter;
import com.epam.android.social.R;
import com.epam.android.social.model.StatusFacebook;

public class ArrayModelListAdapter extends AbstractAdapter<StatusFacebook> {
	private static final String TAG = ArrayModelListAdapter.class
			.getSimpleName();

	public ArrayModelListAdapter(Context c, int pItemResource,
			List<StatusFacebook> pList) {
		super(c, pItemResource, pList);
	}

	@Override
	public void init(View view, StatusFacebook item) {
		TextView userName = (TextView) view.findViewById(R.id.userName);
		userName.setText(item.getName());
	}

}
