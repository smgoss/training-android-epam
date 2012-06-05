package com.epam.android.social.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
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
		Resources res = context.getResources();
		List<String> resTitles = Arrays.asList(context.getResources()
				.getStringArray(R.array.tweet_dialog));
		icons = new ArrayList<Drawable>(resTitles.size() + urls.size()
				+ hashtags.size() + profiles.size());
		titles = new ArrayList<String>();

		for (String string : urls) {
			titles.add(string);
			icons.add(res.getDrawable(R.drawable.search_by)); // TODO: iconify
		}
		for (String string : resTitles) {
			if (res.getString(R.string.reply).equals(string)) {
				titles.add(string);
				icons.add(res.getDrawable(R.drawable.reply));
			} else if (res.getString(R.string.quote).equals(string)) {
				titles.add(string);
				icons.add(res.getDrawable(R.drawable.quote));
			} else if (res.getString(R.string.quote_with_comment)
					.equals(string)) {
				titles.add(string);
				icons.add(res.getDrawable(R.drawable.quote_with_comment));
			} else if (res.getString(R.string.view_replies).equals(string)) {
				titles.add(string);
				icons.add(res.getDrawable(R.drawable.view_replies));
			} else if (res.getString(R.string.search_by).equals(string)) {
				for (String tag : hashtags) {
					titles.add(string + " " + tag);
					icons.add(res.getDrawable(R.drawable.search_by));
				}
			} else if (res.getString(R.string.copy).equals(string)) {
				titles.add(string);
				icons.add(res.getDrawable(R.drawable.copy));
			} else if (res.getString(R.string.who_quoted_this).equals(string)) {
				titles.add(string);
				icons.add(res.getDrawable(R.drawable.who_quoted_this));
			} else if (res.getString(R.string.share).equals(string)) {
				titles.add(string);
				icons.add(res.getDrawable(R.drawable.share));
			} else if (res.getString(R.string.profile).equals(string)) {
				for (String profile : profiles) {
					titles.add(string + " " + profile);
					icons.add(res.getDrawable(R.drawable.profile));
				}
			}

		}
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
