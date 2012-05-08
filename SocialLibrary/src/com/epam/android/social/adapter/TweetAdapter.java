package com.epam.android.social.adapter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epam.android.common.adapter.AbstractAdapter;
import com.epam.android.common.adapter.IAdapterCreator;
import com.epam.android.common.model.BaseModel;
import com.epam.android.social.R;
import com.epam.android.social.helper.DataConvertHelper;
import com.epam.android.social.model.Tweet;
import com.google.android.imageloader.ImageLoader;

public class TweetAdapter extends AbstractAdapter<Tweet> {

	private static final String TAG = TweetAdapter.class.getSimpleName();
	
	public static final IAdapterCreator<TweetAdapter> ADAPTER_CREATOR = new IAdapterCreator<TweetAdapter>() {
		
		@Override
		public TweetAdapter create(Context c, int pItemResource,
				List<? extends BaseModel> pList) {
			return new TweetAdapter(c, pItemResource, (List<Tweet>) pList);
		}
	};
	
	public TweetAdapter(Context c, int pItemResource, List<Tweet> pList) {
		super(c, pItemResource, pList);
	}

	@Override
	public void init(View view, Tweet item) {
		ImageView userAvatar = (ImageView) view.findViewById(R.id.userAvatar);
		mImageLoader.setAvatar(item.getProfileUrl(), userAvatar);
		TextView userName = (TextView) view.findViewById(R.id.userName);
		userName.setText(item.getUserName());

		Date date = new Date(item.getPublicdDate());
		Date dateNow = Calendar.getInstance().getTime();

		TextView datePublic = (TextView) view.findViewById(R.id.tweetDate);
		datePublic.setText(DataConvertHelper.getFormattedDate(dateNow, date));

		TextView tweetText = (TextView) view.findViewById(R.id.tweetText);
		tweetText.setText(item.getText());

	}

	

}
