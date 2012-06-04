package com.epam.android.social.adapter;

import java.util.Arrays;
import java.util.List;

import com.epam.android.social.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectPhotoAdapter extends BaseAdapter {

	private List<String> items;

	private Context mContext;

	public SelectPhotoAdapter(Context context) {
		items = Arrays.asList(context.getResources().getStringArray(
				R.array.select_photo_variants));
		mContext = context;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return Long.valueOf(arg0);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.select_photo_item, null, false);
		}

		TextView itemName = (TextView) convertView
				.findViewById(R.id.selectPhotoItem_name);
		itemName.setText(items.get(position));

		ImageView itemIcon = (ImageView) convertView
				.findViewById(R.id.selectPhotoItem_icon);
		if (position == 0) {
			itemIcon.setImageDrawable(mContext.getResources().getDrawable(
					R.drawable.ic_photo_from_camera));
		} else {
			itemIcon.setImageDrawable(mContext.getResources().getDrawable(
					R.drawable.ic_photo_from_gallery));
		}

		return convertView;
	}
}
