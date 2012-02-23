package com.epam.android.social;

import java.util.List;

import android.util.Log;
import android.widget.Toast;

import com.epam.android.common.BaseArrayModelFromXmlAcivity;
import com.epam.android.social.model.Item;

public class ArrayModelFromXMLSampleActivity extends BaseArrayModelFromXmlAcivity<Item>{

	private static final String URL = "http://partners.mtvnservices.com/dextr/partner/wireless/daily_show_most_popular_videos_changed/full.xml";
	
	private static final String TAG = ArrayModelFromXMLSampleActivity.class.getSimpleName();
	
	@Override
	public String getUrl() {
		return URL;
	}

	@Override
	protected void success(List<Item> result) {
		
		Toast.makeText(this, "Load " + result.size() + " items", Toast.LENGTH_LONG).show();
		
		for(int i = 0; i < result.size(); i++){
			Log.d(TAG, result.get(i).getCopiright());
			Log.d(TAG, result.get(i).getDescription());
			Log.d(TAG, result.get(i).getGuid());
			Log.d(TAG, result.get(i).getKeywords());
			Log.d(TAG, result.get(i).getLink());
			Log.d(TAG, result.get(i).getMedia());
			Log.d(TAG, result.get(i).getMediaDuration());
			Log.d(TAG, result.get(i).getMediaTitle());
			Log.d(TAG, result.get(i).getThumbnail());
			Log.d(TAG, result.get(i).getTitle());
			Log.d(TAG, "" + result.get(i).getCategoryLength());
			Log.d(TAG, "" + result.get(i).getGuidIsPermaLink());
			Log.d(TAG, "" + result.get(i).getPubDate());
			for (int j = 0; j < result.get(i).getCategoryLength(); j++) {
				Log.d(TAG, "i=" + i + " j=" + j);
				Log.d(TAG, result.get(i).getCategory(j));
				Log.d(TAG, result.get(i).getCategorySchema(j));
			}
		}
		
	}

	@Override
	public int getLayoutResource() {
		return R.layout.load_array_model;
	}


}
