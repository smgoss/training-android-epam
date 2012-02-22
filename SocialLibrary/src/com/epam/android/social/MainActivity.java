package com.epam.android.social;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.epam.android.common.http.Loader;
import com.epam.android.common.annotation.Tag;
import com.epam.android.social.model.Item;
import com.epam.android.social.model.User;

public class MainActivity extends Activity {
	
	private static final String TAG = MainActivity.class.getSimpleName();

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		isOnline();

	}

	public void onModelButtonClick(View view) {
		Intent intent = new Intent(this, ModelSampleActivity.class);
		startActivity(intent);
	}

	public void onArrayModelButtonClick(View view) {
		Intent intent = new Intent(this, ArrayModelSampleActivity.class);
		startActivity(intent);
	}

	public void onParseXMLtoJSONButtonClick(View view)
			throws ClientProtocolException, JSONException, IOException {
		Loader loader = Loader.get(this);
		loader.createJsonFromXml("http://partners.mtvnservices.com/dextr/partner/wireless/daily_show_most_popular_videos_changed/full.xml");
//		List<Item> item = loader
//				.loadArrayModelFromXmlByAnnotation(
//						"http://partners.mtvnservices.com/dextr/partner/wireless/daily_show_most_popular_videos_changed/full.xml",
//						Item.MODEL_CREATOR);
		
//		for(int i = 0; i < item.size(); i++){
//			Log.d(TAG, item.get(i).getCopiright());
//			Log.d(TAG, item.get(i).getDescription());
//			Log.d(TAG, item.get(i).getGuid());
//			Log.d(TAG, item.get(i).getKeywords());
//			Log.d(TAG, item.get(i).getLink());
//			Log.d(TAG, item.get(i).getMedia());
//			Log.d(TAG, item.get(i).getMediaDuration());
//			Log.d(TAG, item.get(i).getMediaTitle());
//			Log.d(TAG, item.get(i).getThumbnail());
//			Log.d(TAG, item.get(i).getTitle());
//			Log.d(TAG, "" + item.get(i).getCategoryLength());
//			Log.d(TAG, "" + item.get(i).getGuidIsPermaLink());
//			Log.d(TAG, "" + item.get(i).getPubDate());
//			for (int j = 0; j < item.get(i).getCategoryLength(); j++) {
//				Log.d(TAG, "i=" + i + " j=" + j);
//				Log.d(TAG, item.get(i).getCategory(j));
//				Log.d(TAG, item.get(i).getCategorySchema(j));
//			}
//		}
//		
	}

	public void onRunAsynkTaskButtonClick(View view) {
		Intent intent = new Intent(this, TestAsyncTaskActivity.class);
		startActivity(intent);
	}

	public void onTestListViewButtonClick(View view) {
		startActivity(new Intent(this, TestListViewActivity.class));
	}
	
	public void onMultiSampleButtonClick(View view) {
		startActivity(new Intent(this, MultiSampleActivity.class));
	}

	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		Toast.makeText(this, R.string.not_internet, Toast.LENGTH_LONG).show();
		return false;
	}
}