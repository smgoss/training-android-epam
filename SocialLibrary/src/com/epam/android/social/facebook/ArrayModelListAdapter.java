package com.epam.android.social.facebook;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.epam.android.common.adapter.AbstractAdapter;
import com.epam.android.social.R;
import com.epam.android.social.helper.DataConvertHelper;
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

		// ImageView userAvatar = (ImageView)
		// view.findViewById(R.id.userAvatar);
		// mImageLoader.setAvatar(item.getProfileUrl(), userAvatar);
		TextView userName = (TextView) view.findViewById(R.id.userName);
		userName.setText(item.getFromName());

		// Date date = new Date(item.getUpdatedTime());
		// Date dateNow = Calendar.getInstance().getTime();

		SimpleDateFormat ISO8601DATEFORMAT = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ssZ", Locale.GERMANY);
		// System.out.println(ISO8601DATEFORMAT.parse(item.getUpdatedTime()));

		TextView datePublic = (TextView) view.findViewById(R.id.postDate);
		try {
			datePublic.setText(ISO8601DATEFORMAT.parse(item.getUpdatedTime()).toLocaleString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// datePublic.setText(DataConvertHelper.getFormattedDate(dateNow,
		// date));

		TextView tweetText = (TextView) view.findViewById(R.id.postText);
		tweetText.setText(item.getMessage());
	}

}
