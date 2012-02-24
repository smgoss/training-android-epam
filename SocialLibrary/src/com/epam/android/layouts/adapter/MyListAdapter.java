package com.epam.android.layouts.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epam.android.common.adapter.AbstractAdapter;
import com.epam.android.layouts.models.MyLayoutModel;
import com.epam.android.social.R;
import com.epam.android.social.model.Other;

public class MyListAdapter extends AbstractAdapter<MyLayoutModel> {

	@SuppressWarnings("unused")
	private static final String TAG = MyListAdapter.class
			.getSimpleName();

	public MyListAdapter(Context context, int pItemResource,
			List<MyLayoutModel> pList) {
		super(context, pItemResource, pList);
	}

	public void init(View view, MyLayoutModel other) {
		TextView title = (TextView) view.findViewById(R.id.myLayoutListTextViewBig);
		title.setText(other.getTitle());
		
		TextView text = (TextView) view.findViewById(R.id.myLayoutListTextViewStandart);
		text.setText(other.getText());
		
		TextView num = (TextView) view.findViewById(R.id.myLayoutListTextViewNumber);
		num.setText(other.getNumber().toString());
		
	
	}

}
