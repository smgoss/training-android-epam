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

public class StatusArrayModelListAdapter extends
		AbstractAdapter<StatusFacebook> {
	private static final String TAG = StatusArrayModelListAdapter.class
			.getSimpleName();

	public StatusArrayModelListAdapter(Context c, int pItemResource,
			List<StatusFacebook> pList) {
		super(c, pItemResource, pList);
	}

	@Override
	public void init(View view, StatusFacebook item) {

		TextView userName = (TextView) view.findViewById(R.id.userName);
		userName.setText(item.getFromName());

		Date date = DateUtils.toDateFromLongFormat(item.getUpdatedTime());
		Date dateNow = Calendar.getInstance().getTime();

		TextView postDate = (TextView) view.findViewById(R.id.postDate);
		postDate.setText(DataConvertHelper.getFormattedDate(dateNow, date));

		TextView postText = (TextView) view.findViewById(R.id.postText);
		postText.setText(item.getMessage());

		TextView tvPlaceName = (TextView) view.findViewById(R.id.tvPlaceName);
		try {
			tvPlaceName.setText(item.getPlaceName());
		} catch (Exception e) {
			tvPlaceName.setText("none");
		}
	}

}
