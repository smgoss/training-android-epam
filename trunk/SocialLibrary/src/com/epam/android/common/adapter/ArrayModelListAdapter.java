package com.epam.android.common.adapter;

import java.util.List;

import com.epam.android.common.model.User;
import com.epam.android.social.R;
import com.google.android.imageloader.ImageLoader;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ArrayModelListAdapter<T> extends AbstractAdapter<T> {

	private static final String TAG = ArrayModelListAdapter.class.getSimpleName();
	
	public ArrayModelListAdapter(Context context, int pItemResource, List<T> pList) {
		super(context, pItemResource, pList);
	}

	public void init(View view, T users) {
		User currentUser = (User) users; 
		TextView userName = (TextView) view.findViewById(R.id.userName);
		userName.setText(currentUser.getName());
		
		ImageView userAvatar = (ImageView) view.findViewById(R.id.userAvatar);
		mImageLoader.bind(userAvatar, currentUser.getImageUrl(), null);
		
				

	}



}
