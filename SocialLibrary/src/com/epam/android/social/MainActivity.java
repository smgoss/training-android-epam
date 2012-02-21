package com.epam.android.social;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.epam.android.common.http.Loader;
import com.epam.android.common.annotation.Tag;
import com.epam.android.social.model.Item;

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
		// Loader loader = new Loader((HttpClient)
		// getApplicationContext().getSystemService(HttpClient.HTTP_CLIENT));
	
		
		
		/*Loader loader = Loader.get(this);
		@Tag(key="item")
		JSONObject  jsonObject = loader.createJsonFromXml("http://partners.mtvnservices.com/dextr/partner/wireless/daily_show_most_popular_videos_changed/full.xml");
		Tag tag = Item.class.getAnnotation(Tag.class);
		String[] key = tag.key();
		
		
		for (int i = 0; i < item.getCategoryLength(0); i++) {
			Log.d(TAG, "" + item.getPubDate(i));
			Log.d(TAG, item.getDescription(i));
			Log.d(TAG, item.getGuid(i));
			Log.d(TAG, "" + item.getGuidIsPermaLink(i));
		}
		
		jsonObject.getJso(key)
		Loader loader = Loader.get(this);
		loader.testAnnotation();
		*/
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