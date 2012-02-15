package com.epam.android.social;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.epam.android.common.http.HttpClient;
import com.epam.android.common.http.Loader;
import com.epam.android.social.DelegateActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
	}

	public void onModelButtonClick(View view) {
		Intent intent = new Intent(this, ModelSampleActivity.class);
		startActivity(intent);
	}
	
	public void onArrayModelButtonClick(View view) {
		Intent intent = new Intent(this, ArrayModelSampleActivity.class);
		startActivity(intent);
	}
	
	public void onParseXMLtoJSONButtonClick(View view) throws ClientProtocolException, JSONException, parser.JSONException, IOException{
//		Loader loader = new Loader((HttpClient) getApplicationContext().getSystemService(HttpClient.HTTP_CLIENT));
		Loader loader = Loader.get(this);
		Log.d(TAG, loader.createJsonFromXml("http://partners.mtvnservices.com/dextr/partner/wireless/comedy/tds_mobile_app_news/full.xml").toString());
		
	}
	
	public void onRunAsynkTaskButtonClick(View view) {
		Intent intent = new Intent(this, TestAsyncTaskActivity.class);
		startActivity(intent);
	}
	
	public void onTestListViewButtonClick(View view) {
		startActivity(new Intent(this, TestListViewActivity.class));
	}
	
	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		Toast.makeText(this, R.string.not_internet,
				Toast.LENGTH_LONG).show();
		return false;
	}
}