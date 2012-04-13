package com.epam.android.social.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;

import com.epam.android.common.adapter.AbstractAdapter;
import com.epam.android.social.model.Follow;

public class FollowAdapter extends AbstractAdapter<Follow>{

	public FollowAdapter(Context c, int pItemResource, List<Follow> pList) {
		super(c, pItemResource, pList);
	}

	@Override
	public void init(View convertView, Follow item) {
		
	}

}
