package com.epam.android.social;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.epam.android.common.http.HttpClient;
import com.epam.android.common.http.Loader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
	
	public void onparseXMLtoJSONButtonClick(View view) throws ClientProtocolException, JSONException, parser.JSONException, IOException{
		Loader loader = new Loader((HttpClient) getApplicationContext().getSystemService(HttpClient.HTTP_CLIENT));
		Log.d(TAG, loader.createJsonFromXml("http://partners.mtvnservices.com/dextr/partner/wireless/comedy/tds_mobile_app_news/full.xml").toString());
		
	}
}