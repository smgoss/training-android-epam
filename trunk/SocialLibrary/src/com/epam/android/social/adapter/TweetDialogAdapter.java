package com.epam.android.social.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.epam.android.social.R;

public class TweetDialogAdapter extends BaseAdapter {

	private List<String> titles;
	private List<Drawable> icons;

	private Context mContext;

	public TweetDialogAdapter(Context context) {

	}

	public TweetDialogAdapter(Context context, List<String> urls,
			List<String> hashtags, List<String> profiles) {
		mContext = context;
		List<String> resTitles = Arrays.asList(context.getResources()
				.getStringArray(R.array.tweet_dialog));
//		List<Drawable> resIcons = new ArrayList<Drawable>();
//		resIcons.add(context.getResources().getDrawable(
//				R.drawable.tweet_dialog_0));
//		resIcons.add(context.getResources().getDrawable(
//				R.drawable.tweet_dialog_1));
//		resIcons.add(context.getResources().getDrawable(
//				R.drawable.tweet_dialog_2));
//		resIcons.add(context.getResources().getDrawable(
//				R.drawable.tweet_dialog_3));
//		resIcons.add(context.getResources().getDrawable(
//				R.drawable.tweet_dialog_4));
//		resIcons.add(context.getResources().getDrawable(
//				R.drawable.tweet_dialog_5));
//		resIcons.add(context.getResources().getDrawable(
//				R.drawable.tweet_dialog_6));
//		resIcons.add(context.getResources().getDrawable(
//				R.drawable.tweet_dialog_7));

		titles = new ArrayList<String>();
		titles.addAll(urls);
		titles.addAll(hashtags);
		titles.addAll(profiles);
		titles.addAll(resTitles);

		icons = new ArrayList<Drawable>(titles.size());

		for (String string : titles) {

			if (string.startsWith("#")) {
				icons.add(context.getResources().getDrawable(
						R.drawable.tweet_dialog_0));
			} else if (string.startsWith("@")) {
				icons.add(context.getResources().getDrawable(
						R.drawable.tweet_dialog_1));
			} else if (string.startsWith("http")) {
				icons.add(context.getResources().getDrawable(
						R.drawable.tweet_dialog_2));
			} else {
				icons.add(context.getResources().getDrawable(
						R.drawable.tweet_dialog_3));
			}
		}

		// icons.add(context.getResources().getDrawable(R.drawable.tweet_dialog_0));
		// icons.add(context.getResources().getDrawable(R.drawable.tweet_dialog_1));
		// icons.add(context.getResources().getDrawable(R.drawable.tweet_dialog_2));
		// icons.add(context.getResources().getDrawable(R.drawable.tweet_dialog_3));
		//
		// icons.add(context.getResources().getDrawable(R.drawable.tweet_dialog_5));
		// icons.add(context.getResources().getDrawable(R.drawable.tweet_dialog_6));
		// icons.add(context.getResources().getDrawable(R.drawable.tweet_dialog_7));

	}

	@Override
	public int getCount() {
		return titles.size();
	}

	@Override
	public Object getItem(int position) {
		return titles.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return Long.valueOf(arg0);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.tweet_dialog_item, null, false);
		}

		ImageView icon = (ImageView) convertView
				.findViewById(R.id.tweetDialogItem_icon);
		icon.setImageDrawable(icons.get(position));
		TextView itemName = (TextView) convertView
				.findViewById(R.id.selectTweetDialogItem_name);
		itemName.setText(titles.get(position));

		return convertView;
	}
}
