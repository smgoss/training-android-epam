package com.epam.android.social.adapter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epam.android.common.adapter.AbstractAdapter;
import com.epam.android.social.R;
import com.epam.android.social.model.SearchResult;

public class SearchAdapter extends AbstractAdapter<SearchResult>{

	public SearchAdapter(Context c, int pItemResource,
			List<SearchResult> pList) {
		super(c, pItemResource, pList);
	}

	@Override
	public void init(View view, SearchResult item) {
		ImageView userAvatar = (ImageView) view.findViewById(R.id.userAvatar);
		mImageLoader.bind(userAvatar, item.getProfileUrl(), null);
		TextView userName = (TextView) view.findViewById(R.id.userName);
		userName.setText(item.getUserName());

		Date date = new Date(item.getPublicdDate());
		Date dateNow = Calendar.getInstance().getTime();

		TextView datePublic = (TextView) view.findViewById(R.id.tweetDate);
		datePublic.setText(getFormattedDate(dateNow, date));

		TextView tweetText = (TextView) view.findViewById(R.id.tweetText);
		tweetText.setText(item.getText());

	}

	private String getFormattedDate(Date dateNow, Date date) {
		StringBuilder result = new StringBuilder();
		if (dateNow.getMonth() - date.getMonth() == 0) {

			if (dateNow.getDay() - date.getDay() == 0) {

				if (dateNow.getHours() - date.getHours() > 0) {
					result.append(String.valueOf(dateNow.getHours()
							- date.getHours())
							+ "h ");
				} else {
					if (date.getHours() - dateNow.getHours() != 0)
						result.append(String.valueOf(date.getHours()
								- dateNow.getHours())
								+ "h ");
				}

				if (dateNow.getMinutes() - date.getMinutes() > 0) {
					result.append(String.valueOf(dateNow.getMinutes()
							- date.getMinutes())
							+ "m ");

				} else {
					if (dateNow.getMinutes() - date.getMinutes() != 0) {
						result.append(String.valueOf(date.getMinutes()
								- dateNow.getMinutes())
								+ "m ");
					}

				}

			} else {
				if (dateNow.getDay() - date.getDay() > 0) {
					result.append(String.valueOf(dateNow.getDay()
							- date.getDay())
							+ "day ");
				} else {
					result.append(String.valueOf(date.getDay()
							- dateNow.getDay())
							+ "day ");
				}
			}

		}

		else {

			if (dateNow.getMonth() - date.getMonth() > 0) {
				result.append(String.valueOf(dateNow.getMonth()
						- date.getMonth())
						+ "month ");
			} else {
				result.append(String.valueOf(date.getMonth()
						- dateNow.getMonth())
						+ "month ");
			}
		}

		return result.append("0 m ago").toString();

	}

}
