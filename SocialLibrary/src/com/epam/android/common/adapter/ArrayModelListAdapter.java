package com.epam.android.common.adapter;

import java.util.List;

import com.epam.android.social.R;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ArrayModelListAdapter<T> extends AbstractAdapter<T> {

	// protected ImageLoader mImageLoader = ImageLoader.get(this.getContext());

	public ArrayModelListAdapter(Context c, int pItemResource, List<T> pList) {
		super(c, pItemResource, pList);
	}

	public void init(View view, T users) {

		TextView userName = (TextView) view.findViewById(R.id.userName);
		//TODO set userName with valid data from the model
		
		ImageView userAvatar = (ImageView) view.findViewById(R.id.userAvatar);
		//TODO load avatars according to the model
		
//		try {
//		mImageLoader.load(friendItemListAvatar, null, url, this);
//		} catch (Exception e) {
//			Log.d("image error","error in adapter");
//			e.printStackTrace();
//		}
				

	}



}
